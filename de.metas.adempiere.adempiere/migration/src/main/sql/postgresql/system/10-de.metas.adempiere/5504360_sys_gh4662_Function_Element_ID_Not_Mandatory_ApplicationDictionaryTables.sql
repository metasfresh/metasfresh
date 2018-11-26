-- Function: public.Element_ID_Not_Mandatory_ApplicationDictionaryTables()

-- DROP FUNCTION IF EXISTS public.Element_ID_Not_Mandatory_ApplicationDictionaryTables();

CREATE OR REPLACE FUNCTION public.Element_ID_Not_Mandatory_ApplicationDictionaryTables()
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


INSERT INTO t_alter_column values('ad_tab','AD_Element_ID','NUMERIC(10)',null,null)
;


INSERT INTO t_alter_column values('ad_tab','AD_Element_ID',null,'NULL',null)
;




UPDATE AD_Column c SET IsMandatory='N'
from (
	select col.AD_Column_ID
	from ad_column col 
	join ad_table t on col.ad_table_ID = t.ad_table_ID
	where t.tablename = 'AD_Window'
	and col.columnname = 'AD_Element_ID') x
Where c.ad_column_ID = x.ad_column_ID;



INSERT INTO t_alter_column values('ad_window','AD_Element_ID','NUMERIC(10)',null,null)
;


INSERT INTO t_alter_column values('ad_window','AD_Element_ID',null,'NULL',null)
;



UPDATE AD_Column c SET IsMandatory='N'
from (
	select col.AD_Column_ID
	from ad_column col 
	join ad_table t on col.ad_table_ID = t.ad_table_ID
	where t.tablename = 'AD_Tab'
	and col.columnname = 'AD_Element_ID') x
Where c.ad_column_ID = x.ad_column_ID;

INSERT INTO t_alter_column values('ad_menu','AD_Element_ID','NUMERIC(10)',null,null)
;


INSERT INTO t_alter_column values('ad_menu','AD_Element_ID',null,'NULL',null)
;



END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.Element_ID_Not_Mandatory_ApplicationDictionaryTables()
  OWNER TO metasfresh;


