UPDATE esr_importline il
SET c_bankstatement_id=bsl.c_bankstatement_id
FROM c_bankstatementline bsl
WHERE il.c_bankstatement_id IS NULL
  AND il.c_bankstatementline_id IS NOT NULL
  AND bsl.c_bankstatementline_id = il.c_bankstatementline_id
;
