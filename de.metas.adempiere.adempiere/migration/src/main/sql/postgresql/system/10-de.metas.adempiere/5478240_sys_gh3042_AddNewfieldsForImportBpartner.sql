
-- 2017-11-24T12:06:14.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557895,1143,0,17,195,533,'N','PaymentRule',TO_TIMESTAMP('2017-11-24 12:06:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Wie die Rechnung bezahlt wird','D',1,'Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Zahlungsweise',0,0,TO_TIMESTAMP('2017-11-24 12:06:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-24T12:06:14.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557895 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-24T12:07:49.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN PaymentRule CHAR(1)')
;

-- 2017-11-24T12:08:11.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557896,560,0,19,533,'N','C_InvoiceSchedule_ID',TO_TIMESTAMP('2017-11-24 12:08:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Plan für die Rechnungsstellung','D',10,'"Rechnungsintervall" definiert die Häufigkeit mit der Sammelrechnungen erstellt werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Terminplan Rechnung',0,0,TO_TIMESTAMP('2017-11-24 12:08:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-24T12:08:11.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557896 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-24T12:08:15.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN C_InvoiceSchedule_ID NUMERIC(10)')
;

-- 2017-11-24T12:08:15.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_BPartner ADD CONSTRAINT CInvoiceSchedule_IBPartner FOREIGN KEY (C_InvoiceSchedule_ID) REFERENCES public.C_InvoiceSchedule DEFERRABLE INITIALLY DEFERRED
;

-- 2017-11-24T12:08:37.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557897,2664,0,10,533,'N','IBAN',TO_TIMESTAMP('2017-11-24 12:08:37','YYYY-MM-DD HH24:MI:SS'),100,'N','International Bank Account Number','D',255,'If your bank provides an International Bank Account Number, enter it here
Details ISO 13616 and http://www.ecbs.org. The account number has the maximum length of 22 characters (without spaces). The IBAN is often printed with a apace after 4 characters. Do not enter the spaces in Adempiere.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','IBAN',0,0,TO_TIMESTAMP('2017-11-24 12:08:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-24T12:08:37.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557897 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-24T12:08:40.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN IBAN VARCHAR(255)')
;

-- 2017-11-24T12:09:06.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557898,973,0,10,533,'N','SwiftCode',TO_TIMESTAMP('2017-11-24 12:09:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Swift Code or BIC','D',255,'The Swift Code (Society of Worldwide Interbank Financial Telecommunications) or BIC (Bank Identifier Code) is an identifier of a Bank. The first 4 characters are the bank code, followed by the 2 character country code, the two character location code and optional 3 character branch code. For details see http://www.swift.com/biconline/index.cfm','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Swift code',0,0,TO_TIMESTAMP('2017-11-24 12:09:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-24T12:09:06.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557898 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-24T12:09:10.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN SwiftCode VARCHAR(255)')
;

-- 2017-11-24T12:09:53.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557899,837,0,19,533,'N','C_BP_BankAccount_ID',TO_TIMESTAMP('2017-11-24 12:09:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Bankverbindung des Geschäftspartners','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Bankverbindung',0,0,TO_TIMESTAMP('2017-11-24 12:09:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-24T12:09:53.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557899 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-24T12:09:56.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN C_BP_BankAccount_ID NUMERIC(10)')
;

-- 2017-11-24T12:09:56.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_BPartner ADD CONSTRAINT CBPBankAccount_IBPartner FOREIGN KEY (C_BP_BankAccount_ID) REFERENCES public.C_BP_BankAccount DEFERRABLE INITIALLY DEFERRED
;

-- 2017-11-24T12:16:11.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557895,560577,0,441,0,TO_TIMESTAMP('2017-11-24 12:16:11','YYYY-MM-DD HH24:MI:SS'),100,'Wie die Rechnung bezahlt wird',0,'D','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.',0,'Y','Y','Y','Y','N','N','N','N','N','Zahlungsweise',580,570,0,1,1,TO_TIMESTAMP('2017-11-24 12:16:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T12:16:11.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560577 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-24T12:16:23.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557898,560578,0,441,0,TO_TIMESTAMP('2017-11-24 12:16:23','YYYY-MM-DD HH24:MI:SS'),100,'Swift Code or BIC',0,'D','The Swift Code (Society of Worldwide Interbank Financial Telecommunications) or BIC (Bank Identifier Code) is an identifier of a Bank. The first 4 characters are the bank code, followed by the 2 character country code, the two character location code and optional 3 character branch code. For details see http://www.swift.com/biconline/index.cfm',0,'Y','Y','Y','Y','N','N','N','N','N','Swift code',590,580,0,1,1,TO_TIMESTAMP('2017-11-24 12:16:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T12:16:23.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560578 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-24T12:16:31.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557897,560579,0,441,0,TO_TIMESTAMP('2017-11-24 12:16:31','YYYY-MM-DD HH24:MI:SS'),100,'International Bank Account Number',0,'D','If your bank provides an International Bank Account Number, enter it here
Details ISO 13616 and http://www.ecbs.org. The account number has the maximum length of 22 characters (without spaces). The IBAN is often printed with a apace after 4 characters. Do not enter the spaces in Adempiere.',0,'Y','Y','Y','Y','N','N','N','N','N','IBAN',600,590,0,1,1,TO_TIMESTAMP('2017-11-24 12:16:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T12:16:31.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560579 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-24T12:16:40.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557896,560580,0,441,0,TO_TIMESTAMP('2017-11-24 12:16:40','YYYY-MM-DD HH24:MI:SS'),100,'Plan für die Rechnungsstellung',0,'D','"Rechnungsintervall" definiert die Häufigkeit mit der Sammelrechnungen erstellt werden.',0,'Y','Y','Y','Y','N','N','N','N','N','Terminplan Rechnung',610,600,0,1,1,TO_TIMESTAMP('2017-11-24 12:16:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T12:16:40.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560580 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-24T12:17:02.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=560,Updated=TO_TIMESTAMP('2017-11-24 12:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560580
;

-- 2017-11-24T12:17:02.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=570,Updated=TO_TIMESTAMP('2017-11-24 12:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560577
;

-- 2017-11-24T12:17:02.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=580,Updated=TO_TIMESTAMP('2017-11-24 12:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560578
;

-- 2017-11-24T12:17:02.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=590,Updated=TO_TIMESTAMP('2017-11-24 12:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560579
;

-- 2017-11-24T12:17:02.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=600,Updated=TO_TIMESTAMP('2017-11-24 12:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5941
;

-- 2017-11-24T12:17:02.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=610,Updated=TO_TIMESTAMP('2017-11-24 12:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5924
;

-- 2017-11-24T12:17:17.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557899,560581,0,441,0,TO_TIMESTAMP('2017-11-24 12:17:16','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners',0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Bankverbindung',620,610,0,1,1,TO_TIMESTAMP('2017-11-24 12:17:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T12:17:17.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560581 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-24T12:17:28.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=600,Updated=TO_TIMESTAMP('2017-11-24 12:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560581
;

-- 2017-11-24T12:17:28.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=610,Updated=TO_TIMESTAMP('2017-11-24 12:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5941
;

-- 2017-11-24T12:17:28.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=620,Updated=TO_TIMESTAMP('2017-11-24 12:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5924
;

-- 2017-11-24T12:17:35.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-11-24 12:17:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560579
;

-- 2017-11-24T12:17:47.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_FieldGroup_ID=109,Updated=TO_TIMESTAMP('2017-11-24 12:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560579
;

-- 2017-11-24T12:17:51.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_FieldGroup_ID=109,Updated=TO_TIMESTAMP('2017-11-24 12:17:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560578
;

-- 2017-11-24T12:17:57.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_FieldGroup_ID=109,Updated=TO_TIMESTAMP('2017-11-24 12:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560581
;

-- 2017-11-24T12:40:06.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2017-11-24 12:40:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=542083
;



-- 2017-11-24T14:55:06.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557900,305,0,15,533,'N','FirstSale',TO_TIMESTAMP('2017-11-24 14:55:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum des Ersten Verkaufs','D',7,'"Erster Verkauf" zeigt das Datum des ersten Verkaufs an diesen Geschäftspartner an.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Erster Verkauf',0,0,TO_TIMESTAMP('2017-11-24 14:55:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-24T14:55:06.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557900 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-24T14:55:09.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN FirstSale TIMESTAMP WITHOUT TIME ZONE')
;

-- 2017-11-24T14:55:29.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557900,560597,0,441,0,TO_TIMESTAMP('2017-11-24 14:55:29','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Ersten Verkaufs',0,'D','"Erster Verkauf" zeigt das Datum des ersten Verkaufs an diesen Geschäftspartner an.',0,'Y','Y','Y','Y','N','N','N','N','N','Erster Verkauf',630,620,0,1,1,TO_TIMESTAMP('2017-11-24 14:55:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T14:55:29.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560597 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-24T14:55:43.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=560,Updated=TO_TIMESTAMP('2017-11-24 14:55:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560597
;

-- 2017-11-24T14:55:44.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=570,Updated=TO_TIMESTAMP('2017-11-24 14:55:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560580
;

-- 2017-11-24T14:55:44.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=580,Updated=TO_TIMESTAMP('2017-11-24 14:55:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560577
;

-- 2017-11-24T14:55:44.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=590,Updated=TO_TIMESTAMP('2017-11-24 14:55:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560578
;

-- 2017-11-24T14:55:44.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=600,Updated=TO_TIMESTAMP('2017-11-24 14:55:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560579
;

-- 2017-11-24T14:55:44.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=610,Updated=TO_TIMESTAMP('2017-11-24 14:55:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560581
;

-- 2017-11-24T14:55:44.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=620,Updated=TO_TIMESTAMP('2017-11-24 14:55:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5941
;

-- 2017-11-24T14:55:44.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=630,Updated=TO_TIMESTAMP('2017-11-24 14:55:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5924
;

-- 2017-11-24T14:56:15.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=20,Updated=TO_TIMESTAMP('2017-11-24 14:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557896
;


ALTER TABLE I_BPartner DROP CONSTRAINT cinvoiceschedule_ibpartner;


-- 2017-11-24T14:57:52.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bpartner','C_InvoiceSchedule_ID','VARCHAR(20)',null,null)
;

-- 2017-11-24T14:59:40.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19, FieldLength=10,Updated=TO_TIMESTAMP('2017-11-24 14:59:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557896
;

ALTER TABLE I_BPartner DROP COLUMN C_InvoiceSchedule_ID;




-- 2017-11-24T15:00:32.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN C_InvoiceSchedule_ID NUMERIC(10)')
;

-- 2017-11-24T15:00:32.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_BPartner ADD CONSTRAINT CInvoiceSchedule_IBPartner FOREIGN KEY (C_InvoiceSchedule_ID) REFERENCES public.C_InvoiceSchedule DEFERRABLE INITIALLY DEFERRED
;

-- 2017-11-24T15:03:59.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543484,0,'InvoiceSchedule',TO_TIMESTAMP('2017-11-24 15:03:59','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','Status Terminplan','Status Terminplan',TO_TIMESTAMP('2017-11-24 15:03:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T15:03:59.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543484 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-11-24T15:04:25.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543484, AD_Reference_ID=10, ColumnName='InvoiceSchedule', Description='', FieldLength=20, Help=NULL, Name='Status Terminplan',Updated=TO_TIMESTAMP('2017-11-24 15:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557896
;

-- 2017-11-24T15:04:25.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Status Terminplan', Description='', Help=NULL WHERE AD_Column_ID=557896 AND IsCentrallyMaintained='Y'
;


ALTER TABLE I_BPartner DROP COLUMN C_InvoiceSchedule_ID;



-- 2017-11-24T15:04:57.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557901,560,0,19,533,'N','C_InvoiceSchedule_ID',TO_TIMESTAMP('2017-11-24 15:04:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Plan für die Rechnungsstellung','D',10,'"Rechnungsintervall" definiert die Häufigkeit mit der Sammelrechnungen erstellt werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Terminplan Rechnung',0,0,TO_TIMESTAMP('2017-11-24 15:04:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-24T15:04:57.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557901 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-24T15:05:00.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bpartner','C_InvoiceSchedule_ID','NUMERIC(10)',null,null)
;

-- 2017-11-24T15:05:07.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN InvoiceSchedule VARCHAR(20)')
;








-- 2017-11-24T16:15:33.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=560,Updated=TO_TIMESTAMP('2017-11-24 16:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560578
;

-- 2017-11-24T16:15:33.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=570,Updated=TO_TIMESTAMP('2017-11-24 16:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560579
;

-- 2017-11-24T16:15:33.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=580,Updated=TO_TIMESTAMP('2017-11-24 16:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560581
;

-- 2017-11-24T16:15:33.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=590,Updated=TO_TIMESTAMP('2017-11-24 16:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560597
;

-- 2017-11-24T16:15:33.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=600,Updated=TO_TIMESTAMP('2017-11-24 16:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560580
;

-- 2017-11-24T16:15:33.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=610,Updated=TO_TIMESTAMP('2017-11-24 16:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560577
;

-- 2017-11-24T16:15:50.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557901,560598,0,441,0,TO_TIMESTAMP('2017-11-24 16:15:49','YYYY-MM-DD HH24:MI:SS'),100,'Plan für die Rechnungsstellung',0,'D','"Rechnungsintervall" definiert die Häufigkeit mit der Sammelrechnungen erstellt werden.',0,'Y','Y','Y','Y','N','N','N','N','Y','Terminplan Rechnung',640,630,0,1,1,TO_TIMESTAMP('2017-11-24 16:15:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T16:15:50.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560598 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-24T16:15:56.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=610,Updated=TO_TIMESTAMP('2017-11-24 16:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560598
;

-- 2017-11-24T16:15:56.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=620,Updated=TO_TIMESTAMP('2017-11-24 16:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560577
;

-- 2017-11-24T16:15:56.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=630,Updated=TO_TIMESTAMP('2017-11-24 16:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5941
;

-- 2017-11-24T16:15:56.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=640,Updated=TO_TIMESTAMP('2017-11-24 16:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5924
;



