-- 2022-01-06T09:43:57.184254500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y', WEBUI_ViewQuickAction_Default='Y',Updated=TO_TIMESTAMP('2022-01-06 11:43:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541029
;

-- 2022-01-06T09:49:38.493623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Abrechnung starten',Updated=TO_TIMESTAMP('2022-01-06 11:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584953
;

-- 2022-01-06T09:49:38.505093800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Auswahl in Warteschlange für Rechnungsstellung und PDF-Verkettung stellen', IsActive='Y', Name='Abrechnung starten',Updated=TO_TIMESTAMP('2022-01-06 11:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541886
;

-- 2022-01-06T09:49:44.776559500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Abrechnung starten',Updated=TO_TIMESTAMP('2022-01-06 11:49:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584953
;

-- 2022-01-06T09:49:47.102286200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Abrechnung starten',Updated=TO_TIMESTAMP('2022-01-06 11:49:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584953
;

-- 2022-01-06T09:50:25.357785200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=15,Updated=TO_TIMESTAMP('2022-01-06 11:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542166
;

-- 2022-01-06T09:52:51.484638600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540568,'EXISTS (SELECT 1 FROM p.M_Product_Category p WHERE p.M_Product_Category_Parent_ID = M_Product_Category.M_Product_Category_ID)',TO_TIMESTAMP('2022-01-06 11:52:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Product_Category that has children','S',TO_TIMESTAMP('2022-01-06 11:52:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-06T09:53:02.095010500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540568,Updated=TO_TIMESTAMP('2022-01-06 11:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542166
;

-- 2022-01-06T10:01:26.346376500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='PDF file for invoice was created.',Updated=TO_TIMESTAMP('2022-01-06 12:01:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545089
;

-- 2022-01-06T10:01:39.334233900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Fakturierung PDF wurde erstellt',Updated=TO_TIMESTAMP('2022-01-06 12:01:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545089
;

-- 2022-01-06T10:01:51.555052200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Fakturierung PDF wurde erstellt',Updated=TO_TIMESTAMP('2022-01-06 12:01:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545089
;

-- 2022-01-06T10:02:01.213428500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='PDF file for invoices was created.',Updated=TO_TIMESTAMP('2022-01-06 12:02:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545089
;

-- 2022-01-06T10:04:11.839747600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT 1 FROM M_Product_Category p WHERE p.M_Product_Category_Parent_ID = M_Product_Category.M_Product_Category_ID)',Updated=TO_TIMESTAMP('2022-01-06 12:04:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540568
;

-- 2022-01-06T11:43:11.795263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579080,2041,0,18,110,540435,'Bill_User_ID',TO_TIMESTAMP('2022-01-06 13:43:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Ansprechpartner des Geschäftspartners für die Rechnungsstellung','de.metas.printing',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungskontakt',0,0,TO_TIMESTAMP('2022-01-06 13:43:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-06T11:43:11.797847900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579080 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-06T11:43:11.826697200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(2041) 
;

-- 2022-01-06T11:43:14.262236300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Printing_Queue','ALTER TABLE public.C_Printing_Queue ADD COLUMN Bill_User_ID NUMERIC(10)')
;

-- 2022-01-06T11:43:14.533399300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Printing_Queue ADD CONSTRAINT BillUser_CPrintingQueue FOREIGN KEY (Bill_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;


-- 2022-01-06T14:49:07.990546800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Fakturierung PDF wurde erstellt',Updated=TO_TIMESTAMP('2022-01-06 16:49:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545089
;

