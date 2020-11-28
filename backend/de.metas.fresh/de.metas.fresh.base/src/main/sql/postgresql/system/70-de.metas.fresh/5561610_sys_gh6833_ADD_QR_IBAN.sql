-- 2020-06-17T07:14:21.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577768,0,'QR_IBAN',TO_TIMESTAMP('2020-06-17 10:14:21','YYYY-MM-DD HH24:MI:SS'),100,'International Bank Account Number','D','For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).','Y','QR IBAN','QR IBAN',TO_TIMESTAMP('2020-06-17 10:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-17T07:14:21.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577768 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-06-17T07:14:35.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=577768, ColumnName='QR_IBAN', Description='International Bank Account Number', Help='For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).', Name='QR IBAN',Updated=TO_TIMESTAMP('2020-06-17 10:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=542083
;

-- 2020-06-17T07:14:35.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='QR IBAN', Description='International Bank Account Number', Help='For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).' WHERE AD_Column_ID=542083
;

-- 2020-06-17T07:14:35.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577768) 
;

-- 2020-06-17T07:14:37.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BP_BankAccount','ALTER TABLE public.C_BP_BankAccount ADD COLUMN QR_IBAN VARCHAR(34)')
;

-- 2020-06-17T07:16:25.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=2664, ColumnName='IBAN', Description='International Bank Account Number', Help='If your bank provides an International Bank Account Number, enter it here
Details ISO 13616 and http://www.ecbs.org. The account number has the maximum length of 22 characters (without spaces). The IBAN is often printed with a apace after 4 characters. Do not enter the spaces in Adempiere.', Name='IBAN',Updated=TO_TIMESTAMP('2020-06-17 10:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=542083
;

-- 2020-06-17T07:16:25.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='IBAN', Description='International Bank Account Number', Help='If your bank provides an International Bank Account Number, enter it here
Details ISO 13616 and http://www.ecbs.org. The account number has the maximum length of 22 characters (without spaces). The IBAN is often printed with a apace after 4 characters. Do not enter the spaces in Adempiere.' WHERE AD_Column_ID=542083
;

-- 2020-06-17T07:16:25.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(2664) 
;

-- 2020-06-17T07:16:42.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570890,577768,0,10,298,'QR_IBAN',TO_TIMESTAMP('2020-06-17 10:16:42','YYYY-MM-DD HH24:MI:SS'),100,'N','International Bank Account Number','D',0,34,'For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'QR IBAN',0,0,TO_TIMESTAMP('2020-06-17 10:16:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-06-17T07:16:42.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570890 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-06-17T07:16:42.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577768) 
;

-- 2020-06-17T07:17:50.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,Description,EntityType,AD_Org_ID) VALUES (226,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2020-06-17 10:17:50','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2020-06-17 10:17:50','YYYY-MM-DD HH24:MI:SS'),100,'For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).',614928,'Y',320,290,1,1,570890,'QR IBAN','International Bank Account Number','D',0)
;

-- 2020-06-17T07:17:50.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614928 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-17T07:17:50.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577768) 
;

-- 2020-06-17T07:17:50.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614928
;

-- 2020-06-17T07:17:50.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(614928)
;

-- 2020-06-17T07:18:17.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,614928,0,226,570134,1000036,'F',TO_TIMESTAMP('2020-06-17 10:18:17','YYYY-MM-DD HH24:MI:SS'),100,'International Bank Account Number','For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).','Y','N','N','Y','N','Y','N','QR IBAN',41,0,40,TO_TIMESTAMP('2020-06-17 10:18:17','YYYY-MM-DD HH24:MI:SS'),100)
;

