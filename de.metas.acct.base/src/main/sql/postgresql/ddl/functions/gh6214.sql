-- CREATE OR REPLACE FUNCTION gh6214(p_c_bpartner_id numeric,
--                                   dateFrom date,
--                                   dateTo date,
--                                   isSoTrx text = 'Y')
--     RETURNS numeric
-- AS
-- $$
--
-- SELECT NULL;
--
-- $$
--     LANGUAGE SQL STABLE;


DROP TABLE IF EXISTS temp_gh6241;
CREATE TEMPORARY TABLE temp_gh6241
(
    beginningBalance numeric,
    endingBalance    numeric,
    dateacct         date,
    description      text,
    c_doctype_id     numeric,
    documentno       text,
    amount           numeric
)
;

WITH invoicesAndPaymentsInPeriod AS
         (
             SELECT --
                    0                                      beginningBalance,
                    0                                      endingBalance,
                    i.dateacct                             dateacct,
                    coalesce(i.poreference, i.description) description,
                    i.c_doctype_id                         c_doctype_id,
                    i.documentno                           documentno,
                    i.grandtotal                           amount
             FROM c_invoice i
             WHERE TRUE
               AND i.c_bpartner_id IN (2156574,
                                       2156623,
                                       2156884,
                                       2156704,
                                       2156886,
                                       2157166,
                                       2156953,
                                       2156483,
                                       2157077)
               AND i.dateacct >= '1111-07-07'::date -- datefrom
               AND i.dateacct <= '3333-07-07'::date-- dateto
               AND i.issotrx = 'Y'
             UNION ALL
             SELECT --
                    0              beginningBalance,
                    0              endingBalance,
                    p.dateacct     dateacct,
                    p.description  description,
                    p.c_doctype_id c_doctype_id,
                    p.documentno   documentno,
                    p.payamt       amount
             FROM c_payment p
             WHERE TRUE
               AND p.c_bpartner_id IN (2156574,
                                       2156623,
                                       2156884,
                                       2156704,
                                       2156886,
                                       2157166,
                                       2156953,
                                       2156483,
                                       2157077)
               AND p.dateacct >= '1111-07-07'::date -- datefrom
               AND p.dateacct <= '3333-07-07'::date-- dateto
               AND p.isreceipt = 'Y'
         )
INSERT
INTO temp_gh6241(beginningBalance,
                 endingBalance,
                 dateacct,
                 description,
                 c_doctype_id,
                 documentno,
                 amount)
SELECT--
      beginningBalance,
      endingBalance,
      dateacct,
      description,
      c_doctype_id,
      documentno,
      amount
FROM invoicesAndPaymentsInPeriod
;


SELECT *
FROM temp_gh6241;
