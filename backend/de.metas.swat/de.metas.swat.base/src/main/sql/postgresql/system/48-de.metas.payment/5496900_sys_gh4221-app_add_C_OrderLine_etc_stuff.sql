-- 2018-06-29T17:01:04.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544154,0,'IsManualPaymentTerm',TO_TIMESTAMP('2018-06-29 17:01:04','YYYY-MM-DD HH24:MI:SS'),100,'Die Zahlungsbedingung wurde vom Nutzer ausgewählt und soll nicht durch das System überschrieben werden','D','Y','Manuelle Zahlungsbedingung','Manuelle Zahlungsbedingung',TO_TIMESTAMP('2018-06-29 17:01:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-29T17:01:04.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544154 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-06-29T17:01:08.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-29 17:01:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544154 AND AD_Language='de_CH'
;

-- 2018-06-29T17:01:08.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544154,'de_CH') 
;

-- 2018-06-29T17:01:54.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-29 17:01:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Manual payment term',PrintName='Manual payment term',Description='The current payment term was set by a user and shall not be overwritten by the system.' WHERE AD_Element_ID=544154 AND AD_Language='en_US'
;

-- 2018-06-29T17:01:54.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544154,'en_US') 
;

-- 2018-06-29T17:02:27.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,CreatedBy,AD_Client_ID,IsActive,Created,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (20,'N',1,0,'N','N','N','N',0,100,0,'Y',TO_TIMESTAMP('2018-06-29 17:02:27','YYYY-MM-DD HH24:MI:SS'),'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-06-29 17:02:27','YYYY-MM-DD HH24:MI:SS'),100,'N','N',260,'N','N','IsManualPaymentTerm',560599,'N','N','N','N','N','N','N','N','Die Zahlungsbedingung wurde vom Nutzer ausgewählt und soll nicht durch das System überschrieben werden',0,0,'Manuelle Zahlungsbedingung',544154,'D','N','N')
;

-- 2018-06-29T17:02:27.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560599 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-06-29T17:02:30.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN IsManualPaymentTerm CHAR(1) DEFAULT ''N'' CHECK (IsManualPaymentTerm IN (''Y'',''N''))')
;

-- 2018-06-29T17:02:55.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-06-29 17:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560599
;

SELECT "de.metas.async".executeSqlAsync(
	'UPDATE C_OrderLine SET IsManualPaymentTerm=''N'' WHERE C_OrderLine_ID IN (select C_OrderLine_ID from C_OrderLine where IsManualPaymentTerm IS NULL ORDER BY C_OrderLine_ID DESC LIMIT 100000)',
	'ALTER TABLE public.C_OrderLine ALTER COLUMN IsManualPaymentTerm SET NOT NULL'
);

-- 2018-06-29T17:11:30.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,CreatedBy,AD_Client_ID,IsActive,Created,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (12,14,0,'N','N','N','N',0,100,0,'Y',TO_TIMESTAMP('2018-06-29 17:11:30','YYYY-MM-DD HH24:MI:SS'),'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-06-29 17:11:30','YYYY-MM-DD HH24:MI:SS'),100,'N','N',260,'N','N','PaymentDiscount',560600,'N','N','N','N','N','N','N','N',0,0,'Skonto %',544151,'D','N','N')
;

-- 2018-06-29T17:11:30.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560600 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-06-29T17:11:37.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN PaymentDiscount NUMERIC')
;

-- 2018-06-29T17:17:47.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560600,565043,0,187,TO_TIMESTAMP('2018-06-29 17:17:46','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','Skonto %',TO_TIMESTAMP('2018-06-29 17:17:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-29T17:17:47.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-29T17:20:24.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=224, SeqNoGrid=214,Updated=TO_TIMESTAMP('2018-06-29 17:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565043
;

-- 2018-06-29T18:56:53.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-29 18:56:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565040
;

-- 2018-06-29T18:58:13.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-29 18:58:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565042
;

-- 2018-06-29T18:58:33.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-29 18:58:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565039
;

-- 2018-06-29T19:12:44.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2018-06-29 19:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565042
;

