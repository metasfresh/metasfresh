-- Checks whether the attributes of one ASI are a subset of another ASI's attributes.
-- Used for M_Product_ASI_Data lookups where the master data ASI (e.g., {Country=DE})
-- must match against a line ASI that may have additional attributes (e.g., {Country=DE, Lot=123}).
--
-- Uses GenerateASIAllAttributesKey (ALL attributes, not just storage-relevant)
-- and UnnestAttributesKey for part-by-part comparison.

CREATE OR REPLACE FUNCTION IsASIAttributesKeySubset(
    p_subset_asi_id  numeric, -- the potentially smaller ASI (e.g., M_Product_ASI_Data's ASI)
    p_superset_asi_id numeric -- the potentially larger ASI (e.g., the shipment/invoice line's ASI)
)
    RETURNS boolean
    LANGUAGE 'sql'
    COST 200
    STABLE
AS
$BODY$
SELECT CASE
           -- NULL or 0 ASI on the subset side = wildcard, matches everything
           WHEN p_subset_asi_id IS NULL OR p_subset_asi_id <= 0 THEN true
           -- If the superset side has no ASI, only a wildcard subset can match (handled above)
           WHEN p_superset_asi_id IS NULL OR p_superset_asi_id <= 0 THEN false
           ELSE
               -- Check that every part of the subset key exists in the superset key
               NOT EXISTS (
                   SELECT 1
                   FROM UnnestAttributesKey(GenerateASIAllAttributesKey(p_subset_asi_id)) subset_part
                   WHERE NOT EXISTS (
                       SELECT 1
                       FROM UnnestAttributesKey(GenerateASIAllAttributesKey(p_superset_asi_id)) superset_part
                       WHERE subset_part.part_text = superset_part.part_text
                   )
               )
           END
    ;
$BODY$
;

COMMENT ON FUNCTION IsASIAttributesKeySubset(numeric, numeric) IS
    'Returns true if the attributes of p_subset_asi_id are a subset of (or equal to) p_superset_asi_id.
    Uses ALL attributes (not just storage-relevant) via GenerateASIAllAttributesKey.

    Special cases:
    * p_subset_asi_id IS NULL or 0: always returns true (wildcard — matches any ASI)
    * p_superset_asi_id IS NULL or 0: returns false (unless subset is also wildcard)

    Used by M_Product_ASI_Data GTIN lookups in EDI DESADV/INVOIC exports.
    Java equivalent: AttributesKey.contains(other) using createAttributesKeyFromASIAllAttributes()'
;
