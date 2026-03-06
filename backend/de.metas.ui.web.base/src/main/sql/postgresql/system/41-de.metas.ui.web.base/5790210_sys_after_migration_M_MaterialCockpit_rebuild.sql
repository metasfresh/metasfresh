-- Slim down after_migration_M_MaterialCockpit_rebuild() to a thin wrapper
-- that delegates to the standalone M_MaterialCockpit_rebuild().

CREATE OR REPLACE FUNCTION after_migration_M_MaterialCockpit_rebuild()
    RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN
    PERFORM M_MaterialCockpit_rebuild();
END;
$$;
