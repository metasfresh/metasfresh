CREATE OR REPLACE FUNCTION after_migration_M_MaterialCockpit_rebuild()
    RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN
    PERFORM M_MaterialCockpit_rebuild();
END;
$$;
