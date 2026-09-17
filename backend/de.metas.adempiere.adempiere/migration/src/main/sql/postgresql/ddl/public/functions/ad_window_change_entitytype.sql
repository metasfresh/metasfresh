CREATE OR REPLACE FUNCTION ad_window_change_entitytype(p_AD_Window_ID numeric, p_EntityType varchar)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count_window int;
    v_count_tab    int;
    v_count_field  int;
BEGIN
    -- AD_Window
    UPDATE AD_Window SET EntityType = p_EntityType WHERE AD_Window_ID = p_AD_Window_ID AND EntityType IS DISTINCT FROM p_EntityType;
    GET DIAGNOSTICS v_count_window = ROW_COUNT;

    -- AD_Tab
    UPDATE AD_Tab SET EntityType = p_EntityType WHERE AD_Window_ID = p_AD_Window_ID AND EntityType IS DISTINCT FROM p_EntityType;
    GET DIAGNOSTICS v_count_tab = ROW_COUNT;

    -- AD_Field (via AD_Tab)
    UPDATE AD_Field SET EntityType = p_EntityType
    WHERE AD_Tab_ID IN (SELECT AD_Tab_ID FROM AD_Tab WHERE AD_Window_ID = p_AD_Window_ID)
      AND EntityType IS DISTINCT FROM p_EntityType;
    GET DIAGNOSTICS v_count_field = ROW_COUNT;

    RAISE NOTICE 'ad_window_change_entitytype(AD_Window_ID=%, EntityType=%): updated % AD_Window, % AD_Tab, % AD_Field rows',
        p_AD_Window_ID, p_EntityType, v_count_window, v_count_tab, v_count_field;
END;
$$;
