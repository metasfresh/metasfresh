-- sql for new column
alter table c_invoice_candidate
    add column ispartialpayment varchar CONSTRAINT c_invoice_candidate_ispartialpayment_check
        CHECK (ispartialpayment = ANY (ARRAY ['Y'::bpchar, 'N'::bpchar]));

-- 2022-07-29T12:12:08.746Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581192,0,TO_TIMESTAMP('2022-07-29 14:12:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Akontozahlung','Akontozahlung',TO_TIMESTAMP('2022-07-29 14:12:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-29T12:12:08.755Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581192 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-29T12:14:29.455Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Payment on account', PrintName='Payment on account',Updated=TO_TIMESTAMP('2022-07-29 14:14:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581192 AND AD_Language='en_US'
;

-- 2022-07-29T12:14:29.495Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581192,'en_US')
;

-- 2022-07-29T12:14:39.513Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-29 14:14:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581192 AND AD_Language='de_DE'
;

-- 2022-07-29T12:14:39.515Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581192,'de_DE')
;

-- 2022-07-29T12:14:39.522Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581192,'de_DE')
;

-- 2022-07-29T12:17:18.840Z
UPDATE AD_Element SET ColumnName='ispartiaalpayment',Updated=TO_TIMESTAMP('2022-07-29 14:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581192
;

-- 2022-07-29T12:17:18.845Z
UPDATE AD_Column SET ColumnName='ispartiaalpayment', Name='Akontozahlung', Description=NULL, Help=NULL WHERE AD_Element_ID=581192
;

-- 2022-07-29T12:17:18.846Z
UPDATE AD_Process_Para SET ColumnName='ispartiaalpayment', Name='Akontozahlung', Description=NULL, Help=NULL, AD_Element_ID=581192 WHERE UPPER(ColumnName)='ISPARTIAALPAYMENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-29T12:17:18.848Z
UPDATE AD_Process_Para SET ColumnName='ispartiaalpayment', Name='Akontozahlung', Description=NULL, Help=NULL WHERE AD_Element_ID=581192 AND IsCentrallyMaintained='Y'
;

-- Column: C_Invoice_Candidate.ispartiaalpayment
-- 2022-07-29T12:21:04.135Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583863,581192,0,20,540270,'ispartiaalpayment',TO_TIMESTAMP('2022-07-29 14:21:04','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.invoicecandidate',0,1,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Y','N',0,'Akontozahlung',0,0,TO_TIMESTAMP('2022-07-29 14:21:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-29T12:21:04.138Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583863 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-29T12:21:04.150Z
/* DDL */  select update_Column_Translation_From_AD_Element(581192)
;

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Akontozahlung
-- Column: C_Invoice_Candidate.ispartiaalpayment
-- 2022-07-29T12:23:53.730Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583863,702507,0,540279,0,TO_TIMESTAMP('2022-07-29 14:23:53','YYYY-MM-DD HH24:MI:SS'),100,'''N''',0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Akontozahlung',0,570,0,1,1,TO_TIMESTAMP('2022-07-29 14:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-29T12:23:53.734Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702507 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-29T12:23:53.743Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581192)
;

-- 2022-07-29T12:23:53.760Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702507
;

-- 2022-07-29T12:23:53.763Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(702507)
;

-- UI Element: Rechnungsdisposition -> Rechnungskandidaten.Akontozahlung
-- Column: C_Invoice_Candidate.ispartiaalpayment
-- 2022-07-29T12:26:50.312Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702507,0,540279,540146,610781,'F',TO_TIMESTAMP('2022-07-29 14:26:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Akontozahlung',7,0,0,TO_TIMESTAMP('2022-07-29 14:26:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-29T13:36:45.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610781
;

-- 2022-07-29T13:36:45.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541058
;

-- 2022-07-29T13:36:45.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548108
;

-- 2022-07-29T13:36:45.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548109
;

-- 2022-07-29T13:36:45.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609542
;

-- 2022-07-29T13:36:45.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548110
;

-- 2022-07-29T13:36:45.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- 2022-07-29T13:36:45.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541084
;

-- 2022-07-29T13:36:45.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541085
;

/*
 * #%L
 * work-metas
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2022-07-29T13:36:45.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548120
;

-- 2022-07-29T13:36:45.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-07-29 15:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541953
;

-- 2022-07-29T15:21:18.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsPartialPayment',Updated=TO_TIMESTAMP('2022-07-29 17:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581192
;

-- 2022-07-29T15:21:18.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPartialPayment', Name='Akontozahlung', Description=NULL, Help=NULL WHERE AD_Element_ID=581192
;

-- 2022-07-29T15:21:18.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPartialPayment', Name='Akontozahlung', Description=NULL, Help=NULL, AD_Element_ID=581192 WHERE UPPER(ColumnName)='ISPARTIALPAYMENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-29T15:21:18.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPartialPayment', Name='Akontozahlung', Description=NULL, Help=NULL WHERE AD_Element_ID=581192 AND IsCentrallyMaintained='Y'
;

