-- set section code from existing allocations

update c_foreignexchangecontract
set m_sectioncode_id = usedInDocs.M_sectioncode_id
from (select max(cOrder.m_sectioncode_id) as M_sectioncode_id, allocs.c_foreignexchangecontract_id
      from c_order cOrder
               inner join C_ForeignExchangeContract_Alloc allocs on allocs.c_order_id = cOrder.c_order_id
      group by allocs.c_foreignexchangecontract_id) usedInDocs
where usedInDocs.c_foreignexchangecontract_id = c_foreignexchangecontract.c_foreignexchangecontract_id
;


-- set contract section code to all allocations involved

update c_foreignexchangecontract_alloc
set m_sectioncode_id = contract.M_sectioncode_id
from c_foreignexchangecontract contract
where contract.c_foreignexchangecontract_id = c_foreignexchangecontract_alloc.c_foreignexchangecontract_id
;
