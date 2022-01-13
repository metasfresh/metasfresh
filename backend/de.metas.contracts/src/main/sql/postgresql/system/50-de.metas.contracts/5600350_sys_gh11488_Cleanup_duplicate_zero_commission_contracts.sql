
-- 1. update all c_commission_share to use the first created 0% commission contract
UPDATE c_commission_share
SET c_flatrate_term_id = subquery.c_flatrate_term_id
FROM (select bill_bpartner_id, min(c_flatrate_term_id) as c_flatrate_term_id
      from c_flatrate_term
      where c_flatrate_conditions_id = 540047
      group by bill_bpartner_id
      having count(bill_bpartner_id) > 1) AS subquery
where c_commission_share.c_bpartner_salesrep_id = subquery.bill_bpartner_id;

-- 2. delete duplicate contracts
delete
from c_flatrate_term
where c_flatrate_conditions_id = 540047
  and c_flatrate_term_id not in
      (select min(c_flatrate_term_id) as c_flatrate_term_id
       from c_flatrate_term
       where c_flatrate_conditions_id = 540047
       group by bill_bpartner_id);
