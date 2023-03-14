
select db_alter_table('m_inventory',
                      'alter table public.m_inventory alter column movementdate type timestamp with time zone using movementdate::timestamp with time zone;');
