update M_DiscountSchema set BreakValueType=(case when IsQuantityBased='Y' then 'Q' else 'A' end)
where DiscountType='B';

-- update M_DiscountSchema set BreakValueType=null where DiscountType<>'B';

