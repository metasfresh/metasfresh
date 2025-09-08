update C_POS_Order set Processed=(case when Status in ('CO', 'VO') then 'Y' else 'N' end);
update C_POS_Order set IsActive='N' where Status='VO' and IsActive='Y';
