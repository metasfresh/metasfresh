-- 2019-10-11T20:52:37.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (10,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-10-11 22:52:37','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-10-11 22:52:37','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540463,'N',569144,'N','N','N','N','N','N','N','N',0,577135,'de.metas.esb.edi','N','N','GTIN','N','GTIN',0)
;

-- 2019-10-11T20:52:37.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569144 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-11T20:52:37.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577135) 
;

-- 2019-10-11T20:53:00.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (10,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-10-11 22:53:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-10-11 22:53:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540463,'N',569145,'N','N','N','N','N','N','N','N',0,577133,'de.metas.esb.edi','N','N','EAN_TU','N','TU-EAN',0)
;

-- 2019-10-11T20:53:00.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569145 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-11T20:53:00.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577133) 
;

-- 2019-10-11T20:53:12.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (10,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-10-11 22:53:11','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-10-11 22:53:11','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540463,'N',569146,'N','N','N','N','N','N','N','N',0,577137,'de.metas.esb.edi','N','N','UPC_TU','N','TU-UPC',0)
;

-- 2019-10-11T20:53:12.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569146 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-11T20:53:12.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577137) 
;

-- 2019-10-11T20:53:24.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (10,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-10-11 22:53:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-10-11 22:53:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540463,'N',569147,'N','N','N','N','N','N','N','N',0,577134,'de.metas.esb.edi','N','N','EAN_CU','N','CU-EAN',0)
;

-- 2019-10-11T20:53:24.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569147 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-11T20:53:24.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577134) 
;

-- 2019-10-11T20:53:42.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (10,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-10-11 22:53:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-10-11 22:53:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540463,'N',569148,'N','N','N','N','N','N','N','N',0,577136,'de.metas.esb.edi','N','N','UPC_CU','N','CU-UPC',0)
;

-- 2019-10-11T20:53:42.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569148 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-11T20:53:42.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577136) 
;

-- 2019-10-11T20:54:26.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=545129
;

-- 2019-10-11T20:54:42.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=548349
;

-- 2019-10-11T20:54:42.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=548349
;

-- 2019-10-11T20:54:59.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548419,0,TO_TIMESTAMP('2019-10-11 22:54:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.esb.edi',540221,550239,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','Y','Mandant',230,'R',TO_TIMESTAMP('2019-10-11 22:54:59','YYYY-MM-DD HH24:MI:SS'),100,'AD_Client_ID')
;

-- 2019-10-11T20:54:59.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548420,0,TO_TIMESTAMP('2019-10-11 22:54:59','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540221,550240,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Organisation',240,'R',TO_TIMESTAMP('2019-10-11 22:54:59','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org_ID')
;

-- 2019-10-11T20:54:59.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548421,0,TO_TIMESTAMP('2019-10-11 22:54:59','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540221,550241,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',250,'E',TO_TIMESTAMP('2019-10-11 22:54:59','YYYY-MM-DD HH24:MI:SS'),100,'Created')
;

-- 2019-10-11T20:55:00.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548422,0,TO_TIMESTAMP('2019-10-11 22:54:59','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540221,550242,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',260,'R',TO_TIMESTAMP('2019-10-11 22:54:59','YYYY-MM-DD HH24:MI:SS'),100,'CreatedBy')
;

-- 2019-10-11T20:55:00.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569147,0,TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540221,550243,'N','N','CU-EAN',270,'E',TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'EAN_CU')
;

-- 2019-10-11T20:55:00.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569145,0,TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540221,550244,'N','N','TU-EAN',280,'E',TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'EAN_TU')
;

-- 2019-10-11T20:55:00.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569144,0,TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540221,550245,'N','N','GTIN',290,'E',TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'GTIN')
;

-- 2019-10-11T20:55:00.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548423,0,TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540221,550246,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',300,'E',TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'IsActive')
;

-- 2019-10-11T20:55:00.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569148,0,TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540221,550247,'N','N','CU-UPC',310,'E',TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'UPC_CU')
;

-- 2019-10-11T20:55:00.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569146,0,TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540221,550248,'N','N','TU-UPC',320,'E',TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'UPC_TU')
;

-- 2019-10-11T20:55:00.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548424,0,TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540221,550249,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',330,'E',TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'Updated')
;

-- 2019-10-11T20:55:00.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,548425,0,TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540221,550250,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',340,'R',TO_TIMESTAMP('2019-10-11 22:55:00','YYYY-MM-DD HH24:MI:SS'),100,'UpdatedBy')
;

-- 2019-10-11T20:55:51.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-11 22:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550248
;

-- 2019-10-11T20:55:56.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-11 22:55:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550247
;

-- 2019-10-11T20:56:05.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-11 22:56:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550245
;

-- 2019-10-11T20:56:06.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-11 22:56:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550244
;

-- 2019-10-11T20:56:13.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-11 22:56:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550243
;


DROP VIEW IF EXISTS public.edi_cctop_invoic_500_v;
CREATE OR REPLACE VIEW public.edi_cctop_invoic_500_v AS
SELECT 
    sum(il.qtyentered) AS qtyinvoiced,
    min(il.c_invoiceline_id) AS edi_cctop_invoic_500_v_id,
    sum(il.linenetamt) AS linenetamt,
    min(il.line) AS line,
    il.c_invoice_id,
    il.c_invoice_id AS edi_cctop_invoic_v_id,
    il.priceactual,
    il.pricelist,
    pp.UPC AS UPC_CU,
    pp.EAN_CU,
    p.value,
    pp.productno AS vendorproductno,
    substr(p.name, 1, 35) AS name,
    substr(p.name, 36, 70) AS name2,
    t.rate,
        CASE
            WHEN u.x12de355 = 'TU' THEN 'PCE'
            ELSE u.x12de355
        END AS eancom_uom,
        CASE
            WHEN u_price.x12de355 = 'TU' THEN 'PCE'
            ELSE u_price.x12de355
        END AS eancom_price_uom,
        CASE
            WHEN t.rate = 0 THEN 'Y'
            ELSE ''
        END AS taxfree,
    c.iso_code,
    il.ad_client_id,
    il.ad_org_id,
    min(il.created) AS created,
    min(il.createdby)::numeric(10,0) AS createdby,
    max(il.updated) AS updated,
    max(il.updatedby)::numeric(10,0) AS updatedby,
    il.isactive,
        CASE pc.value
            WHEN 'Leergut' THEN 'P'
            ELSE ''
        END AS leergut,
    COALESCE(NULLIF(pp.productdescription, ''), NULLIF(pp.description, ''), NULLIF(p.description, ''), p.name)::character varying AS productdescription,
    COALESCE(ol.line, il.line) AS orderline,
	COALESCE(NULLIF(o.poreference, ''), i.poreference)::character varying(40) AS orderporeference,
    il.c_orderline_id,
    sum(il.taxamtinfo) AS taxamtinfo,
    pip.GTIN,
    pip.EAN_TU,
    pip.UPC AS UPC_TU
FROM c_invoiceline il
    LEFT JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id AND ol.isactive = 'Y'
        LEFT JOIN M_HU_PI_Item_Product pip ON ol.M_HU_PI_Item_Product_ID=pip.M_HU_PI_Item_Product_ID
    LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
    LEFT JOIN m_product p ON p.m_product_id = il.m_product_id
    LEFT JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
    LEFT JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
    LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
    LEFT JOIN c_bpartner_product pp ON pp.c_bpartner_id = i.c_bpartner_id AND pp.m_product_id = il.m_product_id AND pp.isactive = 'Y'
    LEFT JOIN c_tax t ON t.c_tax_id = il.c_tax_id
    LEFT JOIN c_uom u ON u.c_uom_id = il.c_uom_id
    LEFT JOIN c_uom u_price ON u_price.c_uom_id = il.price_uom_id
WHERE true 
    AND il.m_product_id IS NOT NULL 
    AND il.isactive = 'Y'
    AND il.qtyentered <> 0
GROUP BY 
  	il.c_invoice_id, 
	il.priceactual, 
	il.pricelist, pp.UPC, pp.EAN_CU, p.value, pp.productno, 
	(substr(p.name, 1, 35)), 
	(substr(p.name, 36, 70)), 
	t.rate, 
	(CASE
            WHEN u.x12de355 = 'TU' THEN 'PCE'
            ELSE u.x12de355
        END), 
	(CASE
            WHEN u_price.x12de355 = 'TU' THEN 'PCE'
            ELSE u_price.x12de355
        END), 
	(CASE
            WHEN t.rate = 0 THEN 'Y'
            ELSE ''
        END), 
	c.iso_code, 
	il.ad_client_id, 
	il.ad_org_id, 
	il.isactive, 
	(CASE pc.value
            WHEN 'Leergut' THEN 'P'
            ELSE ''
        END), 
	(COALESCE(NULLIF(pp.productdescription, ''), NULLIF(pp.description, ''), NULLIF(p.description, ''), p.name)), 
	(COALESCE(NULLIF(o.poreference, ''), i.poreference)), 
	(COALESCE(ol.line, il.line)), 
	il.c_orderline_id,
    pip.UPC, pip.GTIN, pip.EAN_TU
ORDER BY COALESCE(ol.line, il.line);

COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
';

