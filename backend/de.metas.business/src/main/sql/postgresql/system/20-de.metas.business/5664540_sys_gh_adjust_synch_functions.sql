
CREATE OR REPLACE FUNCTION isSynchroniseTrl(p_ad_language character varying) RETURNS character
    STABLE
    LANGUAGE sql
AS
$$
SELECT l.issynchtranslation
FROM AD_Language l
WHERE l.AD_Language = p_AD_Language
  AND l.IsActive = 'Y'
UNION ALL
SELECT 'N'
LIMIT 1;
$$
;

ALTER FUNCTION isSynchroniseTrl(varchar) OWNER TO metasfresh
;



DROP FUNCTION IF EXISTS update_ad_element_on_ad_element_trl_update(p_ad_element_id numeric,
                                                                   p_ad_language   character varying)
;

CREATE FUNCTION update_ad_element_on_ad_element_trl_update(p_ad_element_id numeric DEFAULT NULL::numeric,
                                                           p_ad_language   character varying DEFAULT NULL::character varying) RETURNS void
    SECURITY DEFINER
    LANGUAGE plpgsql
AS
$$
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
        po_help                 = e_trl.po_help

    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND e.AD_Element_ID = e_trl.AD_Element_ID
      AND isbasead_language(e_trl.ad_language) = 'Y'
      AND isSynchroniseTrl(e_trl.ad_language) = 'Y';

    --
    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Element rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;


END;
$$
;

COMMENT ON FUNCTION update_ad_element_on_ad_element_trl_update(numeric, varchar) IS 'When the AD_Element_trl has one of its values changed for the base language, the change shall also propagate to the parent AD_Element.
This is used for automatic updates on AD_Element which has column that are not manually updatable.
'
;

ALTER FUNCTION update_ad_element_on_ad_element_trl_update(numeric, varchar) OWNER TO metasfresh
;




DROP FUNCTION  IF EXISTS  update_column_translation_from_ad_element(p_ad_element_id numeric, p_ad_language character varying);

CREATE OR REPLACE FUNCTION update_column_translation_from_ad_element(p_ad_element_id numeric DEFAULT NULL::numeric, p_ad_language character varying DEFAULT NULL::character varying) RETURNS void
    SECURITY DEFINER
    LANGUAGE plpgsql
AS
$$
DECLARE
    update_count integer;
BEGIN
    UPDATE AD_Column_Trl c_trl
    SET Name         = e_trl.Name,
        Description  = e_trl.Description,
        IsTranslated = e_trl.IsTranslated
    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND c_trl.ad_language = e_trl.ad_language
      AND EXISTS(SELECT 1 FROM ad_column c WHERE c.ad_element_id = e_trl.ad_element_id AND c.ad_column_id = c_trl.ad_column_id)
      AND issynchronisetrl(e_trl.ad_language) = 'Y';
    --
    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Column_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE AD_Column c
        SET Name        = e_trl.Name,
            Description = e_trl.Description
        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND c.ad_element_id = e_trl.ad_element_id
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND issynchronisetrl(e_trl.ad_language) = 'Y';
        --
        GET DIAGNOSTICS update_count = ROW_COUNT;
        RAISE NOTICE 'Update % AD_Column rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;
    END IF;
END ;
$$
;

COMMENT ON FUNCTION update_column_translation_from_ad_element(numeric, varchar) IS 'Update AD_Column_Trl/AD_Column from AD_Element_ID.'
;

ALTER FUNCTION update_column_translation_from_ad_element(numeric, varchar) OWNER TO metasfresh
;

--
DROP FUNCTION IF EXISTS update_fieldtranslation_from_ad_name_element(p_ad_element_id numeric,
                                                                     p_ad_language   character varying)
;

CREATE OR REPLACE FUNCTION update_fieldtranslation_from_ad_name_element(p_ad_element_id numeric DEFAULT NULL::numeric,
                                                                        p_ad_language   character varying DEFAULT NULL::character varying) RETURNS void
    SECURITY DEFINER
    LANGUAGE plpgsql
AS
$$
DECLARE
    update_count_via_AD_Column integer;
    update_count_via_AD_Name   integer;
BEGIN
    --
    -- AD_Field_Trl via AD_Column
    UPDATE AD_Field_Trl f_trl
    SET IsTranslated = e_trl.IsTranslated,
        Name         = e_trl.Name,
        Description  = e_trl.Description,
        Help         = e_trl.Help
    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND f_trl.ad_language = e_trl.ad_language

      AND EXISTS(SELECT 1
                 FROM ad_column c
                          JOIN AD_Field f ON c.ad_column_id = f.ad_column_id
                 WHERE c.ad_element_id = e_trl.ad_element_id
                   AND f_trl.ad_field_id = f.ad_field_id
                   AND f.ad_name_id IS NULL)
      AND issynchronisetrl(e_trl.ad_language) = 'Y';
    --
    GET DIAGNOSTICS update_count_via_AD_Column = ROW_COUNT;


    --
    -- AD_Field_Trl via AD_Element -> AD_Name_ID
    UPDATE AD_Field_Trl f_trl
    SET IsTranslated = e_trl.IsTranslated,
        Name         = e_trl.Name,
        Description  = e_trl.Description,
        Help         = e_trl.Help

    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND f_trl.ad_language = e_trl.ad_language
      AND EXISTS(SELECT 1
                 FROM AD_Field f
                 WHERE f.ad_name_id = e_trl.ad_element_id
                   AND f.ad_field_id = f_trl.ad_field_id)
      AND issynchronisetrl(e_trl.ad_language) = 'Y';

    --
    GET DIAGNOSTICS update_count_via_AD_Name = ROW_COUNT;

    RAISE NOTICE 'Updated AD_Field_Trl for AD_Element_ID=%, AD_Language=%: % rows via AD_Column, % rows via AD_Name',
        p_AD_Element_ID, p_AD_Language, update_count_via_AD_Column, update_count_via_AD_Name;

    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE AD_Field f
        SET Name        = e_trl.Name,
            Description = e_trl.Description
        FROM AD_Element_Trl_Effective_v e_trl
        WHERE f.AD_Name_ID IS NULL
          AND (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND EXISTS(SELECT 1
                     FROM AD_Column c
                     WHERE c.ad_element_id = e_trl.ad_element_id
                       AND c.ad_column_id = f.ad_column_id)
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND issynchronisetrl(e_trl.ad_language) = 'Y';

        GET DIAGNOSTICS update_count_via_AD_Column = ROW_COUNT;

        UPDATE AD_Field f
        SET Name        = e_trl.Name,
            Description = e_trl.Description
        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND f.ad_Name_id = e_trl.ad_element_id
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND issynchronisetrl(e_trl.ad_language) = 'Y';
        --
        GET DIAGNOSTICS update_count_via_AD_Name = ROW_COUNT;


        RAISE NOTICE 'Updated AD_Field for AD_Element_ID=%, AD_Language=%: % rows via AD_Column, % rows via AD_Name',
            p_AD_Element_ID, p_AD_Language, update_count_via_AD_Column, update_count_via_AD_Name;
    END IF;

END;
$$
;

COMMENT ON FUNCTION update_fieldtranslation_from_ad_name_element(numeric, varchar) IS 'When the AD_Field.AD_Name_ID is changed, update all the AD_Field_Trl entries of the AD_Field, based on the AD_Element behind the AD_Name'
;

ALTER FUNCTION update_fieldtranslation_from_ad_name_element(numeric, varchar) OWNER TO metasfresh
;

--

DROP FUNCTION IF EXISTS update_process_para_translation_from_ad_element(p_ad_element_id numeric,
                                                                        p_ad_language   character varying)
;

CREATE OR REPLACE FUNCTION update_process_para_translation_from_ad_element(p_ad_element_id numeric DEFAULT NULL::numeric,
                                                                           p_ad_language   character varying DEFAULT NULL::character varying) RETURNS void
    SECURITY DEFINER
    LANGUAGE plpgsql
AS
$$
DECLARE
    update_count integer;
BEGIN

    UPDATE ad_process_para_trl p_trl
    SET IsTranslated = e_trl.IsTranslated,
        Name         = e_trl.Name,
        Description  = e_trl.Description,
        Help         = e_trl.help
    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND p_trl.ad_language = e_trl.ad_language
      AND EXISTS(SELECT 1 FROM ad_process_para p WHERE p.ad_element_id = e_trl.ad_element_id AND p.ad_process_para_id = p_trl.ad_process_para_id)
      AND issynchronisetrl(e_trl.ad_language) = 'Y';

    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Process_Para_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;


    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE ad_process_para p
        SET Name        = e_trl.Name,
            Description = e_trl.Description,
            Help        = e_trl.help

        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND p.ad_element_id = e_trl.ad_element_id
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND issynchronisetrl(e_trl.ad_language) = 'Y';
        --
        GET DIAGNOSTICS update_count = ROW_COUNT;
        RAISE NOTICE 'Update % AD_Process_Para rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;
    END IF;

END;
$$
;

ALTER FUNCTION update_process_para_translation_from_ad_element(numeric, varchar) OWNER TO metasfresh
;

---

DROP FUNCTION IF EXISTS update_printformatitem_translation_from_ad_element(p_ad_element_id numeric, p_ad_language character varying);

CREATE OR REPLACE FUNCTION update_printformatitem_translation_from_ad_element(p_ad_element_id numeric DEFAULT NULL::numeric, p_ad_language character varying DEFAULT NULL::character varying) RETURNS void
    SECURITY DEFINER
    LANGUAGE plpgsql
AS
$$
DECLARE
    update_count integer;
BEGIN

    UPDATE ad_printformatitem_trl p_trl
    SET IsTranslated = e_trl.IsTranslated,
        printname    = e_trl.printname

    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND p_trl.ad_language = e_trl.ad_language
      AND EXISTS(SELECT 1
                 FROM ad_column c
                          JOIN ad_printformatitem p ON c.ad_column_id = p.ad_column_id
                 WHERE c.ad_element_id = e_trl.ad_element_id
                   AND p_trl.ad_printformatitem_id = p.ad_printformatitem_id)
      AND issynchronisetrl(e_trl.ad_language) = 'Y';


    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_PrintFormatItem_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;


    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE ad_printformatitem p
        SET Name      = e_trl.Name,
            printname = e_trl.printname

        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND EXISTS(SELECT 1
                     FROM AD_Column c
                     WHERE c.ad_element_id = e_trl.ad_element_id
                       AND c.ad_column_id = p.ad_column_id)
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND issynchronisetrl(e_trl.ad_language) = 'Y';

        --
        GET DIAGNOSTICS update_count = ROW_COUNT;
        RAISE NOTICE 'Update % AD_PrintFormatItem rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;
    END IF;


END;
$$
;

ALTER FUNCTION update_printformatitem_translation_from_ad_element(numeric, varchar) OWNER TO metasfresh
;

---

DROP FUNCTION IF EXISTS update_tab_translation_from_ad_element(p_ad_element_id numeric, p_ad_language character varying);

CREATE OR REPLACE FUNCTION update_tab_translation_from_ad_element(p_ad_element_id numeric DEFAULT NULL::numeric, p_ad_language character varying DEFAULT NULL::character varying) RETURNS void
    SECURITY DEFINER
    LANGUAGE plpgsql
AS
$$
DECLARE
    update_count integer;
BEGIN

    UPDATE AD_Tab_Trl t_trl
    SET IsTranslated  = e_trl.IsTranslated,
        Name          = e_trl.Name,
        Description   = e_trl.Description,
        Help          = e_trl.Help,
        CommitWarning = e_trl.CommitWarning
    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND t_trl.ad_language = e_trl.ad_language
      AND EXISTS(SELECT 1 FROM AD_Tab t WHERE t.ad_element_id = e_trl.ad_element_id AND t.ad_tab_id = t_trl.ad_tab_id)
      AND issynchronisetrl(e_trl.ad_language) = 'Y';

    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Tab_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE AD_Tab t
        SET Name          = e_trl.Name,
            Description   = e_trl.Description,
            Help          = e_trl.Help,
            CommitWarning = e_trl.CommitWarning

        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND t.ad_element_id = e_trl.ad_element_id
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND issynchronisetrl(e_trl.ad_language) = 'Y';
        --
        GET DIAGNOSTICS update_count = ROW_COUNT;
        RAISE NOTICE 'Update % AD_Tab rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;
    END IF;

END;
$$
;

COMMENT ON FUNCTION update_tab_translation_from_ad_element(numeric, varchar) IS 'When the AD_Tab.AD_Element_ID is changed, update all the AD_Tab_Trl entries of the AD_Tab, based on the AD_Element.'
;

ALTER FUNCTION update_tab_translation_from_ad_element(numeric, varchar) OWNER TO metasfresh
;

---
DROP FUNCTION IF EXISTS update_window_translation_from_ad_element(p_ad_element_id numeric, p_ad_language character varying);

CREATE OR REPLACE FUNCTION update_window_translation_from_ad_element(p_ad_element_id numeric DEFAULT NULL::numeric, p_ad_language character varying DEFAULT NULL::character varying) RETURNS void
    SECURITY DEFINER
    LANGUAGE plpgsql
AS
$$
DECLARE
    update_count integer;
BEGIN

    UPDATE AD_Window_TRL w_trl
    SET IsTranslated = e_trl.IsTranslated,
        Name         = e_trl.Name,
        Description  = e_trl.Description,
        Help         = e_trl.Help
    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND w_trl.ad_language = e_trl.ad_language
      AND EXISTS(SELECT 1 FROM ad_window w WHERE w.ad_element_id = e_trl.ad_element_id AND w.ad_window_id = w_trl.ad_window_id)
      AND issynchronisetrl(e_trl.ad_language) = 'Y';


    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Window_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE AD_Window w
        SET Name        = e_trl.Name,
            Description = e_trl.Description,
            Help        = e_trl.Help

        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND w.ad_element_id = e_trl.ad_element_id
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND issynchronisetrl(e_trl.ad_language) = 'Y';
        --
        GET DIAGNOSTICS update_count = ROW_COUNT;
        RAISE NOTICE 'Update % AD_Window rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;
    END IF;

END;
$$
;

COMMENT ON FUNCTION update_window_translation_from_ad_element(numeric, varchar) IS 'When the AD_Window.AD_Element_ID is changed, update all the AD_Window_Trl entries of the AD_Window, based on the AD_Element.'
;

ALTER FUNCTION update_window_translation_from_ad_element(numeric, varchar) OWNER TO metasfresh
;

---

DROP FUNCTION IF EXISTS update_menu_translation_from_ad_element(p_ad_element_id numeric, p_ad_language character varying);

CREATE OR REPLACE FUNCTION update_menu_translation_from_ad_element(p_ad_element_id numeric DEFAULT NULL::numeric, p_ad_language character varying DEFAULT NULL::character varying) RETURNS void
    SECURITY DEFINER
    LANGUAGE plpgsql
AS
$$
DECLARE
    update_count integer;
BEGIN

    UPDATE AD_Menu_Trl m_trl
    SET IsTranslated            = e_trl.IsTranslated,
        Name                    = e_trl.Name,
        Description             = e_trl.Description,
        webui_namebrowse        = e_trl.webui_namebrowse,
        webui_namenew           = e_trl.webui_namenew,
        webui_namenewbreadcrumb = e_trl.webui_namenewbreadcrumb
    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND m_trl.ad_language = e_trl.ad_language
      AND EXISTS(SELECT 1 FROM ad_menu m WHERE m.ad_element_id = e_trl.ad_element_id AND m.ad_menu_id = m_trl.ad_menu_id)
      AND issynchronisetrl(e_trl.ad_language) = 'Y';

    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Menu_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;


    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE AD_Menu m
        SET Name                    = e_trl.Name,
            Description             = e_trl.Description,
            webui_namebrowse        = e_trl.webui_namebrowse,
            webui_namenew           = e_trl.webui_namenew,
            webui_namenewbreadcrumb = e_trl.webui_namenewbreadcrumb

        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND m.ad_element_id = e_trl.ad_element_id
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND issynchronisetrl(e_trl.ad_language) = 'Y';
        --
        GET DIAGNOSTICS update_count = ROW_COUNT;
        RAISE NOTICE 'Update % AD_menu rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;
    END IF;

END;
$$
;

COMMENT ON FUNCTION update_menu_translation_from_ad_element(numeric, varchar) IS 'When the AD_Process_Para.AD_Element_ID is changed, update all the AD_Process_Para_Trl entries of the AD_Process_Para, based on the AD_Element.'
;

ALTER FUNCTION update_menu_translation_from_ad_element(numeric, varchar) OWNER TO metasfresh
;
