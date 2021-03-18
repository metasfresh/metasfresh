--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO
$$
    BEGIN
        BEGIN
            /* DDL */ PERFORM public.db_alter_table('ExternalSystem_Config_Alberta', 'alter table ExternalSystem_Config_Alberta drop column name;');
        EXCEPTION
            WHEN others THEN RAISE NOTICE 'column Value Name did not exists anymore in ExternalSystem_Config_Alberta.';
        END;
    END;
$$
;

