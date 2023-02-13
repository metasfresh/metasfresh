DROP FUNCTION IF EXISTS getDeliveryCreditUsedPercentForSectionGroupPartner(p_section_group_partner_id numeric,
                                                                           p_date                     date)
;

CREATE FUNCTION getDeliveryCreditUsedPercentForSectionGroupPartner(p_section_group_partner_id numeric,
                                                                   p_date                     date) RETURNS character varying
    STABLE
    LANGUAGE sql
AS
$$

SELECT CASE
           WHEN COALESCE(SUM(lim.amount), 0) = 0 THEN '0%'
                                                 ELSE ROUND(COALESCE(SUM(stats.delivery_creditused), 0) * 100 / COALESCE(SUM(lim.amount), 0), 3) || '%'
       END

FROM C_BPartner sectionGroupPartner
         JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id
         JOIN c_bpartner_stats stats ON sectionPartner.c_bpartner_id = stats.c_bpartner_id
         JOIN C_BPartner_CreditLimit lim ON sectionPartner.c_bpartner_id = lim.c_bpartner_id


WHERE sectionGroupPartner.c_bpartner_id = p_section_group_partner_id

  AND ((lim.processed = 'Y' AND lim.isactive = 'Y') OR (lim.processed = 'N' AND lim.isactive = 'N'))
  AND lim.datefrom <= p_date
  AND NOT EXISTS(SELECT 1
                 FROM c_bpartner_creditlimit lim2
                 WHERE lim.c_bpartner_id = lim2.c_bpartner_id
                   AND lim2.datefrom >= lim.datefrom
                   AND lim2.datefrom <= p_Date
                   AND ((lim2.processed = 'Y' AND lim2.isactive = 'Y') OR (lim2.processed = 'N' AND lim2.isactive = 'N'))
                   AND lim2.c_bpartner_creditlimit_id != lim.c_bpartner_creditlimit_id)

GROUP BY sectionPartner.section_group_partner_id

$$
;

COMMENT ON FUNCTION getDeliveryCreditUsedPercentForSectionGroupPartner ( numeric, date) IS 'TEST: SELECT getDeliveryCreditUsedPercentForSectionGroupPartner(2156017, now()::date);'
;