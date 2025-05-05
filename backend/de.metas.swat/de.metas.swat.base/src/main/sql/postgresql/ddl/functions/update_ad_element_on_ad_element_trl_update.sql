-- Function: public.update_ad_element_on_ad_element_trl_update(numeric, character varying)

DROP FUNCTION public.update_ad_element_on_ad_element_trl_update(p_AD_Element_ID numeric,
                                                                p_AD_Language   character varying)
;

CREATE OR REPLACE FUNCTION public.update_ad_element_on_ad_element_trl_update(p_AD_Element_ID numeric = NULL,
                                                                             p_AD_Language   character varying = NULL)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN

    UPDATE AD_Element e
    SET name                    = e_trl.name,
        printname               = e_trl.printname,
        description             = e_trl.description,
        help                    = e_trl.help,
        commitwarning           = e_trl.commitwarning,
        webui_namebrowse        = e_trl.webui_namebrowse,
        webui_namenew           = e_trl.webui_namenew,
        webui_namenewbreadcrumb = e_trl.webui_namenewbreadcrumb,
        po_name                 = e_trl.po_name,
        po_printname            = e_trl.po_printname,
        po_description          = e_trl.po_description,
        po_help                 = e_trl.po_help,
        Updated                 = e_trl.updated

    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND e.AD_Element_ID = e_trl.AD_Element_ID
      AND isbasead_language(e_trl.ad_language) = 'Y'
      AND e.updated <> e_trl.updated;
    --
    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'UPDATE % AD_Element ROWS USING AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;


END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     SECURITY DEFINER
                     COST 100
;

ALTER FUNCTION public.update_ad_element_on_ad_element_trl_update(numeric, character varying)
    OWNER TO metasfresh
;

COMMENT ON FUNCTION public.update_ad_element_on_ad_element_trl_update(numeric, character varying) IS 'WHEN the AD_Element_trl has one OF its VALUES changed FOR the base LANGUAGE, the change shall ALSO propagate TO the parent AD_Element.
This IS used FOR automatic updates ON AD_Element which has COLUMN that are NOT manually updatable.
'
;
