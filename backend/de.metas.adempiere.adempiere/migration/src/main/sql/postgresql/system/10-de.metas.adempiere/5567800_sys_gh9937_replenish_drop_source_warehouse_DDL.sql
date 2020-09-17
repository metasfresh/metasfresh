
select db_alter_table('i_replenish', 'alter table i_replenish drop column M_WarehouseSource_ID;');
select db_alter_table('i_replenish', 'alter table i_replenish drop column warehousesourcevalue;');

select db_alter_table('m_replenish', 'alter table m_replenish drop column M_WarehouseSource_ID;');


-- 2020-09-16T18:20:02.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_replenish','ReplenishType',null,'NOT NULL',null)
;

