--
-- add C_BPartner_ID to both MD_Candidate_Stock_v and MD_Candidate.
--

-- 2017-11-30T16:35:08.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,558004,187,0,30,540808,'N','C_BPartner_ID',TO_TIMESTAMP('2017-11-30 16:35:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','de.metas.material.dispo',10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geschäftspartner',0,0,TO_TIMESTAMP('2017-11-30 16:35:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-30T16:35:08.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558004 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-30T16:35:21.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate','ALTER TABLE public.MD_Candidate ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2017-11-30T16:36:00.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,558005,187,0,30,540859,'N','C_BPartner_ID',TO_TIMESTAMP('2017-11-30 16:36:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','de.metas.material.dispo',10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Geschäftspartner',0,0,TO_TIMESTAMP('2017-11-30 16:36:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-30T16:36:00.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558005 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-30T16:47:15.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-11-30 16:47:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558004
;

--
-- add C_BPartner_ID to MD_Candidate_Stock_v
--
DROP VIEW IF EXISTS public.MD_Candidate_Stock_v;
CREATE OR REPLACE VIEW public.MD_Candidate_Stock_v AS 
SELECT
	main.M_Product_ID,
	main.M_Warehouse_ID,
	main.C_BPartner_ID,
	main.DateProjected,
	stockPerDate.StorageAttributesKey,
	stockPerDate.Qty
FROM 
	( /* "main": get the different product, warehouse and dateProjected values that we have. the rest shall be joined */
		SELECT DISTINCT M_Product_ID, M_Warehouse_ID, C_BPartner_ID, DateProjected 
		FROM MD_Candidate 
		WHERE IsActive='Y' AND MD_Candidate_Type='STOCK' 
	) main
	JOIN LATERAL 
	( /* "stockPerDate": for each combination from "main", join the latest stock records which are older or as old as main */
		SELECT DISTINCT ON (M_Product_ID, M_Warehouse_ID, C_BPartner_ID, StorageAttributesKey)
			M_Product_ID, M_Warehouse_ID, C_BPartner_ID, StorageAttributesKey, 
			DateProjected, Qty
		FROM ( /* we might have multiple stock records with the same product, warehouse etc, so aggregate them */
			SELECT 
				M_Product_ID, M_Warehouse_ID, C_BPartner_ID, StorageAttributesKey, DateProjected,
				SUM(Qty) as Qty
			FROM MD_Candidate innerCand
			WHERE /* these two conditions are in the whereclausee of the index md_candidate_stock_perf */
				innerCand.IsActive='Y' AND innerCand.MD_Candidate_Type='STOCK'
				AND innerCand.M_Warehouse_ID = main.M_Warehouse_ID
				AND innerCand.M_Product_ID = main.M_Product_ID
				AND innerCand.DateProjected <= main.DateProjected
			GROUP BY
				M_Product_ID, C_BPartner_ID, M_Warehouse_ID, StorageAttributesKey, Dateprojected
			) groupedData
		ORDER BY     
			M_Product_ID, M_Warehouse_ID, C_BPartner_ID, StorageAttributesKey, Dateprojected DESC
	) stockPerDate ON true
ORDER BY     
	M_Product_ID, M_Warehouse_ID, C_BPartner_ID, StorageAttributesKey, Dateprojected DESC
;
COMMENT ON VIEW public.MD_Candidate_Stock_v 
IS 'For each distinct (DateProjected, M_Product_ID, C_BPartner_ID, M_Warehouse_ID) that occurs in any active STOCK MD_Candidate,
this view selects the different Qtys and StorageAttributesKeys for that date, product, partner and warehouse.
Note that those Qtys and StorageAttributesKeys can be from MD_Candidates whose DateProjected is before the respective date, 
if they were not yet superseeded by more recent values';


DROP INDEX IF EXISTS public.md_candidate_stock_perf;
DROP INDEX IF EXISTS public.md_candidate_stock_v_perf;

CREATE INDEX md_candidate_stock_v_perf
  ON public.md_candidate
  USING btree
  (M_Product_ID, M_Warehouse_ID, C_BPartner_ID, DateProjected DESC, StorageAttributesKey, Qty)
  WHERE IsActive = 'Y' AND MD_Candidate_Type = 'STOCK';
COMMENT ON INDEX public.md_candidate_stock_v_perf
  IS 'This index has the purpose of supporting the view MD_Candidate_Stock_v 
in finding the latest DateProjected for a given product-id, warehouse-id, partner-id and StorageAttributesKey-(like-)expression.

Note: the Qty column is in so that hopefully MD_Candidate_Stock_v can be done by the DBMS using index-only-scans.';


DROP INDEX IF EXISTS public.md_candidate_stock_latest_date_perf;
CREATE INDEX md_candidate_stock_latest_date_perf
  ON public.md_candidate
  USING btree
  (DateProjected DESC NULLS LAST)
  WHERE IsActive = 'Y' AND MD_Candidate_Type = 'STOCK';
COMMENT ON INDEX public.md_candidate_stock_latest_date_perf 
  IS 'this index supports finding the latest M_Candidate with a data less or equal than a given date';
