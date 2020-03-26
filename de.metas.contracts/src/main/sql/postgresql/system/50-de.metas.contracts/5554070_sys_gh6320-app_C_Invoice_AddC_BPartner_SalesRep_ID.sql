-- 2020-03-06T13:19:49.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570096,577106,0,20,318,'IsSalesPartnerRequired',TO_TIMESTAMP('2020-03-06 14:19:49','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Auftrag nur mit Vertriebspartner',0,0,TO_TIMESTAMP('2020-03-06 14:19:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-03-06T13:19:50.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570096 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-03-06T13:19:50.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577106) 
;

-- 2020-03-06T13:19:54.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN IsSalesPartnerRequired CHAR(1) DEFAULT ''N'' CHECK (IsSalesPartnerRequired IN (''Y'',''N'')) NOT NULL')
;
-- 2020-03-06T15:37:49.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Beim (Rechnungs-)Partner ist hinterlegt, dass ein Vertriebspartner angegeben werden muss.', Value='de.metas.contracts.commission.MissingSalesPartner',Updated=TO_TIMESTAMP('2020-03-06 16:37:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544940
;

-- 2020-03-06T15:37:56.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Beim (Rechnungs-)Partner ist hinterlegt, dass ein Vertriebspartner angegeben werden muss.',Updated=TO_TIMESTAMP('2020-03-06 16:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544940
;

-- 2020-03-06T15:38:04.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Beim (Rechnungs-)Partner ist hinterlegt, dass ein Vertriebspartner angegeben werden muss.',Updated=TO_TIMESTAMP('2020-03-06 16:38:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544940
;

-- 2020-03-06T15:38:12.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The document''s (bill-)partner is flagged to require a sales partner to be specified',Updated=TO_TIMESTAMP('2020-03-06 16:38:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544940
;

