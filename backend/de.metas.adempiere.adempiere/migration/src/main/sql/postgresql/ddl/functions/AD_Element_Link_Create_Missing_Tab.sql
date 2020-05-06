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
    raise notice '% AD_Element_Link records added for missing AD_Tabs', sqlInsertCount;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

