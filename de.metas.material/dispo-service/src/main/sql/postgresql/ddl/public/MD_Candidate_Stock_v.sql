
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
			WHERE /* these two conditions are in the whereclausee of the index md_candidate_uc_stock */
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
IS 'For each distinct (DateProjected, M_Product_ID, M_Warehouse_ID) that occurs in any active STOCK MD_Candidate,
this view selects the different Qtys and StorageAttributesKeys for that date, product and warehouse.
Note that those Qtys and StorageAttributesKeys can be from MD_Candidate whose DateProjected is before the respective date, 
if they were not yet superseeded by more recent values';
