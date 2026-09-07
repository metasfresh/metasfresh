-- me03#29063: Reusable ASI subset check function for M_Product_ASI_Data lookups.
-- Returns true when the first ASI's attributes are a subset of the second ASI's attributes.

CREATE OR REPLACE FUNCTION IsASIAttributesKeySubset(
    p_subset_asi_id  numeric,
    p_superset_asi_id numeric
)
    RETURNS boolean
    LANGUAGE 'sql'
    COST 200
    STABLE
AS
$BODY$
SELECT CASE
           WHEN p_subset_asi_id IS NULL OR p_subset_asi_id <= 0 THEN true
           WHEN p_superset_asi_id IS NULL OR p_superset_asi_id <= 0 THEN false
           ELSE
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
