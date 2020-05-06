-- 2018-01-12T17:00:59.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,558578,204,0,19,540270,'N','C_PaymentTerm_ID',TO_TIMESTAMP('2018-01-12 17:00:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Bedingungen für die Bezahlung dieses Vorgangs','de.metas.invoicecandidate',10,'Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Zahlungsbedingung',0,0,TO_TIMESTAMP('2018-01-12 17:00:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-01-12T17:00:59.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558578 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-12T17:01:01.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_PaymentTerm_ID NUMERIC(10)')
;

-- 2018-01-12T17:01:02.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT CPaymentTerm_CInvoiceCandidate FOREIGN KEY (C_PaymentTerm_ID) REFERENCES public.C_PaymentTerm DEFERRABLE INITIALLY DEFERRED
;

-- 2018-01-12T17:02:55.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,558578,561480,0,540279,0,TO_TIMESTAMP('2018-01-12 17:02:55','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',0,'U','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','Y','Y','N','N','N','N','N','Zahlungsbedingung',720,380,0,1,1,TO_TIMESTAMP('2018-01-12 17:02:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-12T17:02:55.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561480 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-01-12T17:03:12.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2018-01-12 17:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561480
;

-- 2018-01-12T17:03:29.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,561480,0,540279,550145,540056,'F',TO_TIMESTAMP('2018-01-12 17:03:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Zahlungsbedingung',1020,0,0,TO_TIMESTAMP('2018-01-12 17:03:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-15T19:10:38.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=855,Updated=TO_TIMESTAMP('2018-01-15 19:10:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550145
;

