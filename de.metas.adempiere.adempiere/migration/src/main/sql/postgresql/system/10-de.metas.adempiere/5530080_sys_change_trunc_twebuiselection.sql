
DROP FUNCTION IF EXISTS trunc_twebuiselection();
CREATE FUNCTION trunc_twebuiselection()  returns integer
LANGUAGE plpgsql
AS $$
DECLARE
BEGIN
        delete from t_webui_viewselection;
        delete from t_webui_viewselectionline;
        RETURN 0;
END;
$$;

COMMENT ON FUNCTION public.trunc_twebuiselection()
IS 'Deletes all records from t_webui_viewselection and t_webui_viewselectionline. DOES NOT TRUNCATE because
truncating needs additional locks and therefore can be blocked by a database backup (pg_dump).
And while this function is active (blocked or no), it blocks other things that result in an unuable WebUI.
';

--select trunc_twebuiselection();
