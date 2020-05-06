update c_ordertax  
set ispackagingtax = 'Y'
where c_tax_id in (select c_tax_id from c_tax where name ilike '%Gebinde%');