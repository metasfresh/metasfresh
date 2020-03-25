UPDATE c_payselection ps
SET isactive=(
    CASE
        WHEN (SELECT count(1) FROM c_payselectionline psl WHERE psl.c_payselection_id = ps.c_payselection_id AND psl.isactive = 'Y' AND c_bankstatement_id IS NULL) = 0
            THEN 'Y'
            ELSE 'N'
    END)
WHERE TRUE
;

