-- Function: public.update_ad_element_on_ad_element_trl_update(numeric, character varying)

DROP FUNCTION public.update_Mobile_Application_on_Mobile_Application_Trl_Update(p_Mobile_Application_ID numeric,
                                                                p_AD_Language   character varying)
;

CREATE OR REPLACE FUNCTION public.update_Mobile_Application_on_Mobile_Application_Trl_Update(p_Mobile_Application_ID numeric = NULL,
                                                                             p_AD_Language   character varying = NULL)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN

    UPDATE Mobile_Application ma
    SET name                    = ma_trl.name,
        description             = ma_trl.description,
        Updated                 = ma_trl.updated

    FROM Mobile_Application_Trl_Effective_v ma_trl
    WHERE (p_Mobile_Application_ID IS NULL OR ma_trl.mobile_application_id = p_Mobile_Application_ID)
      AND (p_AD_Language IS NULL OR ma_trl.AD_Language = p_AD_Language)
      AND ma.mobile_application_id = ma_trl.mobile_application_id
      AND isbasead_language(ma_trl.ad_language) = 'Y'
      AND ma.updated <> ma_trl.updated;
    --
    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'UPDATE % Mobile_Application ROWS USING Mobile_Application_ID=%, AD_Language=%', update_count, p_Mobile_Application_ID, p_AD_Language;


END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     SECURITY DEFINER
                     COST 100
;

ALTER FUNCTION public.update_Mobile_Application_on_Mobile_Application_Trl_Update(numeric, character varying)
    OWNER TO metasfresh
;

COMMENT ON FUNCTION public.update_Mobile_Application_on_Mobile_Application_Trl_Update(numeric, character varying) IS 'WHEN the AD_Element_trl has one OF its VALUES changed FOR the base LANGUAGE, the change shall ALSO propagate TO the parent AD_Element.
This IS used FOR automatic updates ON AD_Element which has COLUMN that are NOT manually updatable.
'
;
