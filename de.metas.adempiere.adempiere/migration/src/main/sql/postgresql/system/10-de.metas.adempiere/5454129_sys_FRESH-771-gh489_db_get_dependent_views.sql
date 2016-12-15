
DROP FUNCTION IF EXISTS public.db_dependent_views(text);
CREATE OR REPLACE FUNCTION public.db_dependent_views(IN p_tablename text)
  RETURNS TABLE(view_schema text, view_name text, depth integer, path text[]) AS
$BODY$
BEGIN
	RETURN QUERY
		WITH RECURSIVE views_list AS (
			SELECT DISTINCT
				u.view_schema::text
				, u.view_name::text
				, 0 as depth
				, ARRAY[u.view_schema || '.' || u.view_name] as path
			FROM information_schema.view_table_usage u
			WHERE u.table_schema='public' and lower(u.table_name)=lower(p_tablename)
			--
			UNION ALL
			SELECT DISTINCT
				c.view_schema::text
				, c.view_name::text
				, p.depth + 1 as depth
				, p.path || (c.view_schema || '.' || c.view_name) as path
				
			FROM information_schema.view_table_usage c
			INNER JOIN views_list p ON p.view_schema=c.table_schema AND p.view_name=c.table_name
		)
		SELECT DISTINCT * FROM views_list v
		;
END; 
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION public.db_dependent_views(text) IS 'Selects the views that directly or indirectly depend on the given view or table. Assumes that the given view or table is in the *public* schema.';

COMMENT ON FUNCTION public.db_dependents(IN regclass)
  IS 'This function is deprecated. Please use db_dependent_views(text) instead, because it is easier to understand and deals with schemas better';
