-- Function: public.update_ad_element_on_ad_element_trl_update(numeric, character varying)

DROP FUNCTION public.update_ad_element_on_ad_element_trl_update(p_AD_Element_ID numeric,
                                                                p_AD_Language character varying)
;

CREATE OR REPLACE FUNCTION public.update_ad_element_on_ad_element_trl_update(p_AD_Element_ID numeric = NULL,
                                                                             p_AD_Language character varying = NULL)
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
        Updated                 = NOW(),
        UpdatedBy               = 99
    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND e.AD_Element_ID = e_trl.AD_Element_ID
      AND isbasead_language(e_trl.ad_language) = 'Y'
      AND EXISTS(SELECT 1
                 FROM ad_element_trl el_trl
                 WHERE el_trl.ad_element_id = e_trl.ad_element_id
                   AND el_trl.ad_language = e_trl.ad_language
                   AND ((COALESCE(e.name, '') NOT LIKE COALESCE(e_trl.name, ''))
                     OR (COALESCE(e.printname, '') NOT LIKE COALESCE(e_trl.printname, ''))
                     OR (COALESCE(e.description, '') NOT LIKE COALESCE(e_trl.description, ''))
                     OR (COALESCE(e.help, '') NOT LIKE COALESCE(e_trl.help, ''))
                     OR (COALESCE(e.commitwarning, '') NOT LIKE COALESCE(e_trl.commitwarning, ''))
                     OR (COALESCE(e.webui_namebrowse, '') NOT LIKE COALESCE(e_trl.webui_namebrowse, ''))
                     OR (COALESCE(e.webui_namenew, '') NOT LIKE COALESCE(e_trl.webui_namenew, ''))
                     OR (COALESCE(e.webui_namenewbreadcrumb, '') NOT LIKE COALESCE(e_trl.webui_namenewbreadcrumb, ''))
                     OR (COALESCE(e.po_name, '') NOT LIKE COALESCE(e_trl.po_name, ''))
                     OR (COALESCE(e.po_printname, '') NOT LIKE COALESCE(e_trl.po_printname, ''))
                     OR (COALESCE(e.po_description, '') NOT LIKE COALESCE(e_trl.po_description, ''))
                     OR (COALESCE(e.po_help, '') NOT LIKE COALESCE(e_trl.po_help, ''))));

    --
    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Element rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;


END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     SECURITY DEFINER
                     COST 100
;

ALTER FUNCTION public.update_ad_element_on_ad_element_trl_update(numeric, character varying)
    OWNER TO metasfresh
;

COMMENT ON FUNCTION public.update_ad_element_on_ad_element_trl_update(numeric, character varying) IS 'When the AD_Element_trl has one of its values changed for the base language, the change shall also propagate to the parent AD_Element.
This is used for automatic updates on AD_Element which has column that are not manually updatable.
'
;
