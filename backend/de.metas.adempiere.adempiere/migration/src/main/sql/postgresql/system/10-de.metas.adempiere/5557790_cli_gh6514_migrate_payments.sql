DROP TABLE IF EXISTS tmp_bankstatementline_payments;
CREATE TABLE tmp_bankstatementline_payments AS
SELECT bsl.c_payment_id,
       bsl.c_bankstatement_id,
       bsl.c_bankstatementline_id,
       NULL::numeric AS c_bankstatementline_Ref_id
FROM C_BankStatement bs
         INNER JOIN C_BankStatementLine bsl ON bs.c_bankstatement_id = bsl.c_bankstatement_id
WHERE bs.DocStatus = 'CO'
  AND bsl.c_payment_id IS NOT NULL
  --
UNION ALL
--
SELECT ref.c_payment_id,
       ref.c_bankstatement_id,
       ref.c_bankstatementline_id,
       ref.c_bankstatementline_ref_id
FROM C_BankStatement bs
         INNER JOIN C_BankStatementLine bsl ON bs.c_bankstatement_id = bsl.c_bankstatement_id
         INNER JOIN c_bankstatementline_ref ref ON bsl.c_bankstatementline_id = ref.c_bankstatementline_id
WHERE bs.DocStatus = 'CO'
;

CREATE UNIQUE INDEX ON tmp_bankstatementline_payments (c_payment_id);

DROP TABLE IF EXISTS tmp_payments_to_migrate;
CREATE TABLE tmp_payments_to_migrate AS
SELECT *
FROM (
         SELECT p.docstatus,
                p.isreconciled,
                (CASE
                     WHEN p.docstatus = 'RE'                 THEN 'Y'
                     WHEN bsl.c_bankstatement_id IS NOT NULL THEN 'Y'
                                                             ELSE 'N'
                 END) AS isreconciled_expected,
                bsl.*
         FROM c_payment p
                  LEFT OUTER JOIN tmp_bankstatementline_payments bsl ON bsl.c_payment_id = p.c_payment_id
         WHERE p.docstatus IN ('CO', 'CL', 'RE', 'VO')
     ) t
WHERE TRUE
-- t.isreconciled <> t.isreconciled_expected
;
CREATE UNIQUE INDEX ON tmp_payments_to_migrate (c_payment_id);

-- drop table if exists backup.c_payment_gh6514;
CREATE TABLE backup.c_payment_gh6514 AS
SELECT *
FROM c_payment;

--
UPDATE c_payment p
SET isreconciled=t.isreconciled_expected,
    c_bankstatement_id=t.c_bankstatement_id,
    c_bankstatementline_id=t.c_bankstatementline_id,
    c_bankstatementline_ref_id=t.c_bankstatementline_ref_id
FROM tmp_payments_to_migrate t
WHERE p.c_payment_id = t.c_payment_id;

-- select * from c_payment where isreconciled='Y'

