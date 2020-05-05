DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.getEmptiesBalance( date, date );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.getEmptiesBalance(
  IN p_datefrom date,
  IN p_dateto   date)

  RETURNS TABLE
  (
    productValue character varying(40),
    productName  character varying(225),
    Received     numeric,
    Shipped      numeric
  )
AS
$BODY$


select
  value,
  name,
  sum(Received) as Received,
  sum(Shipped)  as Shipped
from
  (
    select
      m2.value,
      m2.name,
      case when io.MovementType IN ('V+', 'C+') /* 'V+'=VendorReceipts */
        then sum(iol.movementqty)
      else 0 end as Received,
      case when io.MovementType IN ('V-', 'C-') /* 'V-'=VendorReturns */
        then sum(iol.movementqty)
      else 0 end as Shipped
    from M_inout io
      join M_InOutLine iol on iol.m_inout_id = io.m_inout_id
      join m_product m2 on iol.m_product_id = m2.m_product_id
    where iol.ispackagingmaterial = 'Y' and io.MovementType IN ('C-', 'C+', 'V-', 'V+')
          and io.movementdate >= p_datefrom and io.movementdate <= p_dateto
    group by m2.value, m2.name, io.MovementType
  ) as d
group by value, name

$BODY$
LANGUAGE sql
STABLE;
