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
