DROP FUNCTION IF EXISTS getcreditlimitforpartner(p_c_bpartner_id numeric,
                                                 p_date          date)
;

CREATE OR REPLACE FUNCTION getcreditlimitforpartner(p_c_bpartner_id numeric,
                                                    p_date          date) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(SUM(lim.amount), 0)

FROM C_BPartner partner
         JOIN C_BPartner_CreditLimit lim ON partner.c_bpartner_id = lim.c_bpartner_id


WHERE partner.c_bpartner_id = p_c_bpartner_id
  AND ((lim.processed = 'Y' AND lim.isactive = 'Y') OR (lim.processed = 'N' AND lim.isactive = 'N'))
  AND lim.datefrom <= p_date
  AND NOT EXISTS(SELECT 1
                 FROM c_bpartner_creditlimit lim2
                 WHERE lim.c_bpartner_id = lim2.c_bpartner_id
                   AND lim2.datefrom >= lim.datefrom
                   AND lim2.datefrom <= p_Date
                   AND ((lim2.processed = 'Y' AND lim2.isactive = 'Y') OR (lim2.processed = 'N' AND lim2.isactive = 'N'))
                   AND lim2.c_bpartner_creditlimit_id != lim.c_bpartner_creditlimit_id)

GROUP BY partner.c_bpartner_id

$$
;

COMMENT ON FUNCTION getcreditlimitforpartner(numeric, date) IS 'The credit limit always comes in base currency. TEST: SELECT getcreditlimitforpartner(2156017, now()::date);'
;

ALTER FUNCTION getcreditlimitforpartner(numeric, date) OWNER TO metasfresh
;
