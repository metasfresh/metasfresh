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
    raise notice '% AD_Element_Link records added for missing AD_Fields', sqlInsertCount;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

