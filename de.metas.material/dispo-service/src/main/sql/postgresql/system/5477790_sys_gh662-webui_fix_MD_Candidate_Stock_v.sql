
CREATE OR REPLACE VIEW public.MD_Candidate_Stock_v AS 
SELECT DISTINCT ON (M_Product_ID, StorageAttributesKey, M_Warehouse_ID)
	M_Product_ID,
	StorageAttributesKey,
	M_Warehouse_ID,
	DateProjected,
	Qty
FROM MD_Candidate
WHERE /* these two conditions are in the whereclausee of the index md_candidate_uc_stock */
	IsActive='Y' AND MD_Candidate_Type='STOCK'
ORDER BY
	M_Product_ID,
	StorageAttributesKey,
    M_Warehouse_ID,
    DateProjected DESC
;

ALTER TABLE public.md_candidate
   ALTER COLUMN storageattributeskey SET DEFAULT '';
COMMENT ON COLUMN public.md_candidate.storageattributeskey
  IS 'Don''t use null because it''s part of a UC index.';

UPDATE public.md_candidate SET storageattributeskey='' WHERE storageattributeskey='<ALL_STORAGE_ATTRIBUTES_KEYS>';
