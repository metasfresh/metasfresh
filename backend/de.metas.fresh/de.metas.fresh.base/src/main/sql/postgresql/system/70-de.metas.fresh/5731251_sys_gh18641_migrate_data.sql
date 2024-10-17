
create table backup.m_product_120824 as select * from m_product;

update m_product
set weight     = case when netweight > 0 then netweight else weight end,
    netweight  = weight
where 1 = 1
;