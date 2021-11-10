CREATE TABLE backup.c_flatrate_term_gh11968 AS
SELECT *
FROM c_flatrate_term
;

WITH records AS (
    SELECT c_flatrate_term_id,
           de_metas_contracts.fetchinitialc_flatrate_term_id(c_flatrate_term_id) AS C_Flatrate_Term_Master_ID
    FROM c_flatrate_term)
UPDATE c_flatrate_term
SET C_Flatrate_Term_Master_ID=records.C_Flatrate_Term_Master_ID
FROM records
WHERE c_flatrate_term.c_flatrate_term_id = records.c_flatrate_term_id
;



