alter table public.leichmehl_plufile_config
    drop constraint leichmehlplufileconfiggroup_leichmehlplufileconfig;

alter table public.leichmehl_plufile_config
    add constraint leichmehlplufileconfiggroup_leichmehlplufileconfig
        foreign key (leichmehl_plufile_configgroup_id) references public.leichmehl_plufile_configgroup
            on delete cascade
            deferrable initially deferred;

