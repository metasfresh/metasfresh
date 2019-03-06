create table backup.M_CostElement_BKP20190306 as select * from M_CostElement;

update M_CostElement set IsActive='N' where CostingMethod not in ('S', 'A');

/*
select * from M_CostElement;
*/
