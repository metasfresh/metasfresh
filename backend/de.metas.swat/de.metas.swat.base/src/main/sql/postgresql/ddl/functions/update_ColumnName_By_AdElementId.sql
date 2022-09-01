

DROP FUNCTION IF EXISTS update_ColumnName_By_AdElementId(
    p_AD_Element_ID numeric,
    p_ColumnName character varying

)
;

CREATE OR REPLACE FUNCTION update_ColumnName_By_AdElementId(
    p_AD_Element_ID numeric,
    p_ColumnName character varying = NULL

)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;


BEGIN
    UPDATE AD_Column SET ColumnName = p_ColumnName WHERE AD_Element_ID=p_AD_Element_ID;

    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Column rows using AD_Element_ID=%', update_count, p_AD_Element_ID;


    UPDATE ad_process_para  SET ColumnName= p_ColumnName WHERE AD_Element_ID=p_AD_Element_ID;

    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Process_Para rows using AD_Element_ID=%', update_count, p_AD_Element_ID;

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100
;

COMMENT ON FUNCTION update_ColumnName_By_AdElementId(numeric, character varying) IS
    'Update AD_Column.ColumnName and AD_Process_Para.ColumnName from AD_Element_ID.'
;
