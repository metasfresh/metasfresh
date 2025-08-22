DROP FUNCTION IF EXISTS public.update_Mobile_Application_TRLs(p_Mobile_Application_ID numeric,
                                                              p_AD_Language           character varying)
;

CREATE OR REPLACE FUNCTION public.update_Mobile_Application_TRLs(p_Mobile_Application_ID numeric = NULL,
                                                                 p_AD_Language           character varying = NULL)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN

    UPDATE Mobile_Application ma
    SET name        = ma_trl_effective.name,
        description = ma_trl_effective.description,
        Updated     = ma_trl_effective.updated

    FROM Mobile_Application_Trl_Effective_v ma_trl_effective
    WHERE (p_Mobile_Application_ID IS NULL OR ma_trl_effective.mobile_application_id = p_Mobile_Application_ID)
      AND (p_AD_Language IS NULL OR ma_trl_effective.AD_Language = p_AD_Language)
      AND ma.mobile_application_id = ma_trl_effective.mobile_application_id
      AND isbasead_language(ma_trl_effective.ad_language) = 'Y'
      AND ma.updated <> ma_trl_effective.updated;
    --
    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'UPDATE % Mobile_Application ROWS USING Mobile_Application_ID=%, AD_Language=%', update_count, p_Mobile_Application_ID, p_AD_Language;

END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     SECURITY DEFINER
                     COST 100
;

ALTER FUNCTION public.update_Mobile_Application_TRLs(numeric, character varying)
    OWNER TO metasfresh
;

COMMENT ON FUNCTION public.update_Mobile_Application_TRLs(numeric, character varying) IS
    'WHEN the Mobile_Application_TRL has one of its values changed from the base language, the change shall also propagate to the parent Mobile_Application.'
;
