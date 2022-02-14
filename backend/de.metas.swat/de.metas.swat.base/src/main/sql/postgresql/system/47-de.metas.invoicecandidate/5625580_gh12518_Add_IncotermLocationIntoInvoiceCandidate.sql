-- 2022-02-11T17:05:57.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_Incoterms_ID/-1@>0 & @C_Incoterms_ID@!540001',Updated=TO_TIMESTAMP('2022-02-11 19:05:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=658892
;

-- 2022-02-11T17:08:03.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,658892,0,544489,546664,600732,'F',TO_TIMESTAMP('2022-02-11 19:08:02','YYYY-MM-DD HH24:MI:SS'),100,'Anzugebender Ort für Handelsklausel','Y','N','N','Y','N','N','N',0,'Incoterm Location',16,0,0,TO_TIMESTAMP('2022-02-11 19:08:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-11T17:08:34.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Incoterms Sadt',Updated=TO_TIMESTAMP('2022-02-11 19:08:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=658892
;

-- 2022-02-11T17:20:21.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@C_Incoterms_ID@!null & @C_Incoterms_ID@!540001',Updated=TO_TIMESTAMP('2022-02-11 19:20:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=501614
;

-- 2022-02-11T17:21:05.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2022-02-11 19:21:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=501614
;

-- 2022-02-11T17:22:59.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@C_Incoterms_ID@!null & @C_Incoterms_ID@!540001',Updated=TO_TIMESTAMP('2022-02-11 19:22:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=501616
;

-- 2022-02-14T07:19:35.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT IncotermLocation AS DefaultValue FROM C_Order WHERE C_Order_id=@C_Order_ID@',Updated=TO_TIMESTAMP('2022-02-14 09:19:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=501616
;

-- 2022-02-14T07:28:33.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT IncotermLocation AS DefaultValue FROM C_Order WHERE C_Order_id=(select C_Order_id from C_invoice where c_invoice_id=@C_Invoice_ID@)',Updated=TO_TIMESTAMP('2022-02-14 09:28:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=501616
;

-- 2022-02-14T07:32:02.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=14,Updated=TO_TIMESTAMP('2022-02-14 09:32:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=590761
;

-- 2022-02-14T07:38:24.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@SQL=SELECT IncotermLocation AS DefaultValue FROM C_Order WHERE C_Order_id=(select C_Order_id from C_invoice where c_invoice_id=@C_Invoice_ID@)',Updated=TO_TIMESTAMP('2022-02-14 09:38:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=658892
;

-- 2022-02-14T07:54:54.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Source_Column_ID,Source_Table_ID,SQL_GetTargetRecordIdBySourceRecordId,Updated,UpdatedBy) VALUES (0,501616,0,540059,318,TO_TIMESTAMP('2022-02-14 09:54:52','YYYY-MM-DD HH24:MI:SS'),100,'S','Y',501614,259,'select c_order_id from c_invoice where c_invoice_id=@C_Invoice_ID / -1@',TO_TIMESTAMP('2022-02-14 09:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-14T08:32:16.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_SQLColumn_SourceTableColumn WHERE AD_SQLColumn_SourceTableColumn_ID=540059
;


---------------------------

-- 2022-02-14T08:34:03.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579246,501608,0,14,540270,'IncotermLocation',TO_TIMESTAMP('2022-02-14 10:34:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Anzugebender Ort für Handelsklausel','de.metas.invoicecandidate',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterm Location',0,0,TO_TIMESTAMP('2022-02-14 10:34:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-02-14T08:34:03.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579246 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-02-14T08:34:03.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(501608) 
;

-- 2022-02-14T08:34:13.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN IncotermLocation VARCHAR(2000)')
;

