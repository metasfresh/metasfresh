
-- 2020-09-13T18:36:35.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2020-09-13 20:36:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550302
;

-- 2020-09-13T19:11:40.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571492,576948,0,17,541023,540463,'InvoicableQtyBasedOn',TO_TIMESTAMP('2020-09-13 21:11:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Abr. Menge basiert auf',0,0,TO_TIMESTAMP('2020-09-13 21:11:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-13T19:11:40.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571492 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-13T19:11:40.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576948) 
;

-- 2020-09-13T19:12:04.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571492,0,TO_TIMESTAMP('2020-09-13 21:12:03','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.esb.edi',540221,550307,'N','N','Abr. Menge basiert auf',350,'E',TO_TIMESTAMP('2020-09-13 21:12:03','YYYY-MM-DD HH24:MI:SS'),100,'InvoicableQtyBasedOn')
;

--------------

-- 2020-09-13T19:35:26.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571493,576948,0,17,541023,540644,'InvoicableQtyBasedOn','(select CASE                    WHEN array_length(array_agg(DISTINCT l.invoicableqtybasedon), 1) = 1                        THEN (array_agg(DISTINCT l.invoicableqtybasedon))[1]                     ELSE NULL                END     from edi_desadvline l     where l.edi_desadv_id=edi_desadv.edi_desadv_id)',TO_TIMESTAMP('2020-09-13 21:35:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Abr. Menge basiert auf',0,0,TO_TIMESTAMP('2020-09-13 21:35:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-13T19:35:26.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571493 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-13T19:35:26.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576948) 
;

-- 2020-09-13T19:36:08.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571493,0,TO_TIMESTAMP('2020-09-13 21:36:07','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.esb.edi',540405,550308,'N','N','Abr. Menge basiert auf',340,'E',TO_TIMESTAMP('2020-09-13 21:36:07','YYYY-MM-DD HH24:MI:SS'),100,'InvoicableQtyBasedOn')
;

-- 2020-09-13T19:36:31.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2020-09-13 21:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550308
;

-- 2020-09-13T19:43:26.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571494,576948,0,17,541023,540462,'InvoicableQtyBasedOn',TO_TIMESTAMP('2020-09-13 21:43:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.esb.edi',0,11,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Abr. Menge basiert auf',0,0,TO_TIMESTAMP('2020-09-13 21:43:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-13T19:43:26.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571494 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-13T19:43:26.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576948) 
;

-- 2020-09-13T19:48:13.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=11,Updated=TO_TIMESTAMP('2020-09-13 21:48:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571493
;

-- 2020-09-13T19:50:22.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548412,0,TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.esb.edi',540220,550309,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','Y','Mandant',430,'R',TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'AD_Client_ID')
;

-- 2020-09-13T19:50:22.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548413,0,TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540220,550310,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Organisation',440,'R',TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org_ID')
;

-- 2020-09-13T19:50:22.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548414,0,TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540220,550311,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',450,'E',TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Created')
;

-- 2020-09-13T19:50:22.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548415,0,TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540220,550312,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',460,'R',TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'CreatedBy')
;

-- 2020-09-13T19:50:22.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571494,0,TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.esb.edi',540220,550313,'N','N','Abr. Menge basiert auf',470,'E',TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'InvoicableQtyBasedOn')
;

-- 2020-09-13T19:50:23.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548416,0,TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540220,550314,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',480,'E',TO_TIMESTAMP('2020-09-13 21:50:22','YYYY-MM-DD HH24:MI:SS'),100,'IsActive')
;

-- 2020-09-13T19:50:23.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548417,0,TO_TIMESTAMP('2020-09-13 21:50:23','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540220,550315,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',490,'E',TO_TIMESTAMP('2020-09-13 21:50:23','YYYY-MM-DD HH24:MI:SS'),100,'Updated')
;

-- 2020-09-13T19:50:23.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548418,0,TO_TIMESTAMP('2020-09-13 21:50:23','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540220,550316,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',500,'R',TO_TIMESTAMP('2020-09-13 21:50:23','YYYY-MM-DD HH24:MI:SS'),100,'UpdatedBy')
;

-- 2020-09-13T19:50:48.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2020-09-13 21:50:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550313
;

