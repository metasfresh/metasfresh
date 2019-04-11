drop function if exists AD_Element_Link_Create_Missing_Window(numeric);

create or replace function AD_Element_Link_Create_Missing_Window
(
    p_AD_Window_ID numeric = null
)
returns void
as
$BODY$ 
declare
    sqlInsertCount integer;
begin
    INSERT INTO AD_Element_Link
    (
        ad_window_id, 
        ad_tab_id, 
        ad_field_id,
        ad_element_id, 
        --
        ad_element_link_id, 
        --
        ad_client_id, ad_org_id, created, createdby, isactive, updated, updatedby
    )
    select
        w.AD_Window_ID,
        null as AD_Tab_ID,
        null as AD_Field_ID,
        w.AD_Element_ID as AD_Element_ID,
        --
        nextval('ad_element_link_seq') as AD_Element_Link_ID,
        --
        w.AD_Client_ID, w.AD_Org_ID, w.Created, w.CreatedBy, 'Y', w.Updated, w.UpdatedBy
    from AD_Window w
    where true
    and (w.AD_Window_ID=p_AD_Window_ID or p_AD_Window_ID is null or p_AD_Window_ID <= 0)
    and not exists (select 1 from AD_Element_Link l where l.AD_Window_ID=w.AD_Window_ID and l.AD_Tab_ID is null and l.AD_Field_ID is null)
    ;
    --
    GET DIAGNOSTICS sqlInsertCount = ROW_COUNT;
    raise notice '% records added for missing AD_Windows', sqlInsertCount;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;



































drop function if exists AD_Element_Link_Create_Missing_Tab(numeric);

create or replace function AD_Element_Link_Create_Missing_Tab
(
    p_AD_Tab_ID numeric = null
)
returns void
as
$BODY$ 
declare
    sqlInsertCount integer;
begin
    INSERT INTO AD_Element_Link
    (
        ad_window_id, 
        ad_tab_id, 
        ad_field_id,
        ad_element_id, 
        --
        ad_element_link_id, 
        --
        ad_client_id, ad_org_id, created, createdby, isactive, updated, updatedby
    )
    select
        tt.AD_Window_ID,
        tt.AD_Tab_ID as AD_Tab_ID,
        null as AD_Field_ID,
        tt.AD_Element_ID as AD_Element_ID,
        --
        nextval('ad_element_link_seq') as AD_Element_Link_ID,
        --
        tt.AD_Client_ID, tt.AD_Org_ID, tt.Created, tt.CreatedBy, 'Y', tt.Updated, tt.UpdatedBy
    from AD_Tab tt
    where true
    and (tt.AD_Tab_ID=p_AD_Tab_ID or p_AD_Tab_ID is null or p_AD_Tab_ID <= 0)
    and not exists (select 1 from AD_Element_Link l where l.AD_Window_ID=tt.AD_Window_ID and l.AD_Tab_ID=tt.AD_Tab_ID and l.AD_Field_ID is null)
    ;
    --
    GET DIAGNOSTICS sqlInsertCount = ROW_COUNT;
    raise notice '% records added for missing AD_Tabs', sqlInsertCount;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;




































drop function if exists AD_Element_Link_Create_Missing_Field(numeric);

create or replace function AD_Element_Link_Create_Missing_Field
(
    p_AD_Field_ID numeric = null
)
returns void
as
$BODY$ 
declare
    sqlInsertCount integer;
begin
    INSERT INTO AD_Element_Link
    (
        ad_window_id, 
        ad_tab_id, 
        ad_field_id,
        ad_element_id, 
        --
        ad_element_link_id, 
        --
        ad_client_id, ad_org_id, created, createdby, isactive, updated, updatedby
    )
    select
        tt.AD_Window_ID,
        null as AD_Tab_ID, -- tt.AD_Tab_ID,
        f.AD_Field_ID,
        coalesce(f.AD_Name_ID, c.AD_Element_ID) as AD_Element_ID,
        --
        nextval('ad_element_link_seq') as AD_Element_Link_ID,
        --
        f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, 'Y', f.Updated, f.UpdatedBy
    from AD_Field f
    left outer join AD_Column c on (c.AD_Column_ID=f.AD_Column_ID)
    inner join AD_Tab tt on (tt.AD_Tab_ID=f.AD_Tab_ID)
    where true
    and (f.AD_Field_ID=p_AD_Field_ID or p_AD_Field_ID <= 0 or p_AD_Field_ID is null)
    and not exists (select 1 from AD_Element_Link l where l.AD_Field_ID=f.AD_Field_ID)
    ;
    --
    GET DIAGNOSTICS sqlInsertCount = ROW_COUNT;
    raise notice '% records added for missing AD_Fields', sqlInsertCount;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;


































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






