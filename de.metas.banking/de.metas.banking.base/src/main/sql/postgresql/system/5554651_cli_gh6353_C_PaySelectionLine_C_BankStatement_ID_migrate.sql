UPDATE C_PaySelectionLine psl
SET c_bankstatement_id=bsl.c_bankstatement_id
FROM c_bankstatementline bsl
WHERE psl.c_bankstatementline_id IS NOT NULL
  AND psl.c_bankstatement_id IS NULL
  AND bsl.c_bankstatementline_id = psl.c_bankstatementline_id;



