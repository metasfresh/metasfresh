-- Function: public.AD_Menu_Make_AD_Element_ID_Not_Mandatory()

-- DROP FUNCTION IF EXISTS public.AD_Menu_Make_AD_Element_ID_Not_Mandatory();

CREATE OR REPLACE FUNCTION public.AD_Menu_Make_AD_Element_ID_Not_Mandatory()
  RETURNS void AS
$BODY$

BEGIN


UPDATE AD_Column c SET IsMandatory='N'
from (
	select col.AD_Column_ID
	from ad_column col 
	join ad_table t on col.ad_table_ID = t.ad_table_ID
	where t.tablename = 'AD_Menu'
	and col.columnname = 'AD_Element_ID') x
Where c.ad_column_ID = x.ad_column_ID;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.AD_Menu_Make_AD_Element_ID_Not_Mandatory()
  OWNER TO metasfresh;



select AD_Menu_Make_AD_Element_ID_Not_Mandatory()
