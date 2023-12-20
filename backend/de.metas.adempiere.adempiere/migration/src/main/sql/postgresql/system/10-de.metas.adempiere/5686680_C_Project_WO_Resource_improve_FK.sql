drop index if exists c_project_wo_step_uq;
create unique index c_project_wo_step_uq  on c_project_wo_step(c_project_id, c_project_wo_step_id);

alter table c_project_wo_resource
    drop constraint if exists cprojectwostep_cprojectworesource;

alter table c_project_wo_resource
    add constraint cprojectwostep_cprojectworesource
        foreign key (c_project_id, c_project_wo_step_id) references public.c_project_wo_step (c_project_id, c_project_wo_step_id)
            deferrable initially deferred;

