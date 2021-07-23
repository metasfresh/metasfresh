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
    raise notice '% AD_Element_Link records added for missing AD_Windows', sqlInsertCount;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

