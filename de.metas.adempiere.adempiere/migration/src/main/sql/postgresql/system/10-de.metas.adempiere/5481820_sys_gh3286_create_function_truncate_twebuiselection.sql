DROP FUNCTION IF EXISTS trunc_twebuiselection();
CREATE FUNCTION trunc_twebuiselection()  returns integer
LANGUAGE plpgsql
AS $$
DECLARE
BEGIN
        truncate t_webui_viewselection;
        truncate t_webui_viewselectionline;
	RETURN 0;
END;
$$;