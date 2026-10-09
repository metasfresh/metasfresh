
select backup_table( 'c_elementvalue_trl'); --backup.c_elementvalue_trl_bkp_20250723_165205_592



UPDATE c_elementvalue_trl set description = ev.description
from c_elementvalue ev
where ev.c_elementvalue_id = c_elementvalue_trl.c_elementvalue_id
and ev.value in (select accountValue from migration_data.default_account_mapping);