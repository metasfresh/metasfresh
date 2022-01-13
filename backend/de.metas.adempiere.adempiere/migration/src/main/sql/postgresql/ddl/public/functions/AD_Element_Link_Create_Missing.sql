drop function if exists AD_Element_Link_Create_Missing();

create or replace function AD_Element_Link_Create_Missing
(
)
returns void
as
$BODY$ 
declare
    sqlInsertCount integer;
begin
    perform AD_Element_Link_Create_Missing_Field();
    perform AD_Element_Link_Create_Missing_Tab();
    perform AD_Element_Link_Create_Missing_Window();
end;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;



/*
select AD_Element_Link_Create_Missing();
*/
