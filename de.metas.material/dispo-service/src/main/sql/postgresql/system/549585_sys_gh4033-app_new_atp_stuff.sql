

DROP VIEW IF EXISTS md_candidate_stock_v;

-- 2018-06-13T19:57:25.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_purchasecandidate','M_AttributeInstance_ID','NUMERIC(10)',null,null)
;

-- 2018-06-14T06:44:04.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='If a purchase candidate is split, the siblings share the same DemandReference',Updated=TO_TIMESTAMP('2018-06-14 06:44:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560251
;


--
-- sort event log records by ID
-- 2018-06-14T12:24:52.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=10.000000000000,Updated=TO_TIMESTAMP('2018-06-14 12:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561297
;

-- 2018-06-14T12:25:40.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2018-06-14 12:25:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561306
;

-- 2018-06-14T12:26:10.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.material.dispo',Updated=TO_TIMESTAMP('2018-06-14 12:26:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563010
;

-- 2018-06-14T12:26:30.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557786,564579,0,540802,TO_TIMESTAMP('2018-06-14 12:26:30','YYYY-MM-DD HH24:MI:SS'),100,1024,'de.metas.material.dispo','Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)','Y','Y','N','N','N','N','N','StorageAttributesKey (technical)',TO_TIMESTAMP('2018-06-14 12:26:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-14T12:26:30.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564579 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-14T12:26:30.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558004,564580,0,540802,TO_TIMESTAMP('2018-06-14 12:26:30','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'de.metas.material.dispo','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2018-06-14 12:26:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-14T12:26:30.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564580 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-14T12:27:24.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543479, AD_Reference_Value_ID=540100, ColumnName='C_OrderSO_ID', Description='Auftrag', Help=NULL, Name='Auftrag',Updated=TO_TIMESTAMP('2018-06-14 12:27:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557892
;

-- 2018-06-14T12:27:24.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auftrag', Description='Auftrag', Help=NULL WHERE AD_Column_ID=557892
;

SELECT db_alter_table('MD_Candidate','ALTER TABLE MD_Candidate RENAME COLUMN C_Order_ID TO C_OrderSO_ID;');

-- 2018-06-14T12:28:59.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=541356, AD_Reference_Value_ID=138, ColumnName='C_BPartner_Customer_ID', Description=NULL, Help=NULL, Name='Kunde',Updated=TO_TIMESTAMP('2018-06-14 12:28:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558004
;

-- 2018-06-14T12:28:59.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kunde', Description=NULL, Help=NULL WHERE AD_Column_ID=558004
;

SELECT db_alter_table('MD_Candidate','ALTER TABLE MD_Candidate RENAME COLUMN C_BPartner_ID TO C_BPartner_Customer_ID');
-- 2018-06-14T12:33:28.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=180,Updated=TO_TIMESTAMP('2018-06-14 12:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564580
;

-- 2018-06-14T12:33:39.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=190,Updated=TO_TIMESTAMP('2018-06-14 12:33:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560574
;

-- 2018-06-14T12:33:43.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=200,Updated=TO_TIMESTAMP('2018-06-14 12:33:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560576
;

-- 2018-06-14T12:33:55.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=210,Updated=TO_TIMESTAMP('2018-06-14 12:33:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560575
;

-- 2018-06-14T12:33:57.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2018-06-14 12:33:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560574
;
-- 2018-06-14T15:27:17.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560376,566,0,11,540859,'N','SeqNo',TO_TIMESTAMP('2018-06-14 15:27:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.material.dispo',14,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Reihenfolge',0,0,TO_TIMESTAMP('2018-06-14 15:27:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-06-14T15:27:17.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560376 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-06-14T16:24:52.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=541356, AD_Reference_Value_ID=138, ColumnName='C_BPartner_Customer_ID', Description=NULL, Help=NULL, Name='Kunde',Updated=TO_TIMESTAMP('2018-06-14 16:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558005
;

-- 2018-06-14T16:24:52.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kunde', Description=NULL, Help=NULL WHERE AD_Column_ID=558005
;

-- 2018-06-14T16:36:53.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='This is not real table and view; in metasfresh it''s used to access the rows returned by the DB function de_metas_material.retrieve_atp_at_date(timestamp with time zone)', Help='Please keep in sync with the DB function de_metas_material.retrieve_atp_at_date(timestamp with time zone)', Name='MD_Candidate_ATP_QueryResult', TableName='MD_Candidate_ATP_QueryResult',Updated=TO_TIMESTAMP('2018-06-14 16:36:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540859
;

-- 2018-06-14T16:39:04.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='There is no real table or view in the DB; This AD_Table used in metasfresh to access the rows returned by the DB function de_metas_material.retrieve_atp_at_date(timestamp with time zone).',Updated=TO_TIMESTAMP('2018-06-14 16:39:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540859
;

-- 2018-06-14T16:39:06.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Help='Please keep in sync with the DB function de_metas_material.retrieve_atp_at_date(timestamp with time zone).',Updated=TO_TIMESTAMP('2018-06-14 16:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540859
;

-- 2018-06-14T16:39:12.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Help='Please keep in sync with the DB function de_metas_material.retrieve_atp_at_date(timestamp with time zone)!',Updated=TO_TIMESTAMP('2018-06-14 16:39:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540859
;


CREATE SCHEMA IF NOT EXISTS de_metas_material;

DROP FUNCTION IF EXISTS de_metas_material.retrieve_atp_at_date(timestamp with time zone);
CREATE FUNCTION de_metas_material.retrieve_atp_at_date(IN p_date timestamp with time zone)
RETURNS TABLE(
	M_Product_ID numeric, 
	M_Warehouse_ID numeric, 
	C_BPartner_Customer_ID numeric, 
	StorageAttributesKey character varying, 
	DateProjected timestamp with time zone,
	SeqNo numeric,
	Qty numeric) AS
$BODY$
	SELECT DISTINCT ON (M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey)
		M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, DateProjected, SeqNo, Qty
	FROM MD_Candidate 
	WHERE IsActive='Y' AND MD_Candidate_Type='STOCK' and DateProjected <= p_date
	ORDER BY M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, Dateprojected DESC, SeqNo DESC
$BODY$
  LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_material.retrieve_atp_at_date(timestamp with time zone)
  IS 'Note that the Qtys can be from MD_Candidates whose DateProjected is before the given p_date, if they were not yet superseeded by more recent values.
  Please keep this function in sync with the AD_Table named MD_Candidate_ATP_QueryResult and also with the function retrieve_atp_at_date_debug';
  
DROP INDEX IF EXISTS md_candidate_stock_v_perf;
CREATE INDEX md_candidate_stock_v_perf
  ON public.md_candidate
  USING btree
  (M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, Dateprojected DESC, SeqNo DESC, Qty)
  WHERE isactive = 'Y' AND md_candidate_type = 'STOCK';
COMMENT ON INDEX public.md_candidate_stock_v_perf
  IS 'This index has the purpose of supporting the function de_metas_material.retrieve_atp_at_date 
in finding the latest DateProjected for a given product-id, warehouse-id, partner-id and StorageAttributesKey-(like-)expression.

Note: the Qty column is in so that hopefully all this can be done by the DBMS using index-only-scans.';  

DROP FUNCTION IF EXISTS de_metas_material.retrieve_atp_at_date_debug(timestamp with time zone);
CREATE FUNCTION de_metas_material.retrieve_atp_at_date_debug(IN p_date timestamp with time zone)
RETURNS TABLE(
	M_Product_ID numeric, 
	M_Warehouse_ID numeric, 
	C_BPartner_Customer_ID numeric, 
	StorageAttributesKey character varying, 
	SeqNo numeric,
	Qty numeric, 
	MD_Candidate_ID numeric,
	Dateprojected timestamp with time zone) AS
$BODY$
	SELECT DISTINCT ON (M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey)
		M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, SeqNo, Qty, MD_Candidate_ID, Dateprojected
	FROM MD_Candidate 
	WHERE IsActive='Y' AND MD_Candidate_Type='STOCK' and DateProjected <= p_date
	ORDER BY M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, Dateprojected DESC, SeqNo DESC
$BODY$
  LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_material.retrieve_atp_at_date_debug(timestamp with time zone)
  IS 'Like de_metas_material.retrieve_atp_at_date, but returns additional columns that are not part of the index.';

