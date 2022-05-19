-- 2021-08-31T18:39:21.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575896,577414,0,19,540311,'C_Customer_Trade_Margin_ID',TO_TIMESTAMP('2021-08-31 21:39:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@Type_Conditions/''''@=''MarginCommission''',0,'C_Customer_Trade_Margin',0,0,TO_TIMESTAMP('2021-08-31 21:39:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-31T18:39:21.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575896 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-31T18:39:21.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577414) 
;

-- 2021-08-31T18:39:28.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Conditions','ALTER TABLE public.C_Flatrate_Conditions ADD COLUMN C_Customer_Trade_Margin_ID NUMERIC(10)')
;

-- 2021-08-31T18:39:29.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Flatrate_Conditions ADD CONSTRAINT CCustomerTradeMargin_CFlatrateConditions FOREIGN KEY (C_Customer_Trade_Margin_ID) REFERENCES public.C_Customer_Trade_Margin DEFERRABLE INITIALLY DEFERRED
;

-- 2021-08-31T18:40:11.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission''',Updated=TO_TIMESTAMP('2021-08-31 21:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559719
;

-- 2021-08-31T18:40:16.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','OnFlatrateTermExtend','VARCHAR(2)',null,'Ca')
;

-- 2021-08-31T18:40:16.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Flatrate_Conditions SET OnFlatrateTermExtend='Ca' WHERE OnFlatrateTermExtend IS NULL
;

-- 2021-08-31T18:42:00.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''''@=''Commission'' & @Type_Conditions/''''@=''MarginCommission''',Updated=TO_TIMESTAMP('2021-08-31 21:42:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569246
;

-- 2021-08-31T18:42:09.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''''@=''Commission''',Updated=TO_TIMESTAMP('2021-08-31 21:42:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569246
;


-- 2021-08-31T18:42:12.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','C_HierarchyCommissionSettings_ID','NUMERIC(10)',null,null)
;

-- 2021-08-31T18:42:46.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission''',Updated=TO_TIMESTAMP('2021-08-31 21:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545773
;

-- 2021-08-31T18:42:48.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','InvoiceRule','CHAR(1)',null,'I')
;

-- 2021-08-31T18:42:48.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Flatrate_Conditions SET InvoiceRule='I' WHERE InvoiceRule IS NULL
;

