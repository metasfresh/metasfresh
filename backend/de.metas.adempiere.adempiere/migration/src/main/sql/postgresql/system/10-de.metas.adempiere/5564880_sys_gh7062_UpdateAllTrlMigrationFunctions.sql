DROP FUNCTION IF EXISTS update_trl_tables_on_ad_element_trl_update(numeric, character varying);

CREATE OR REPLACE FUNCTION update_trl_tables_on_ad_element_trl_update
(
    p_AD_Element_ID numeric,
    p_AD_Language character varying = null
)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN
    --
    -- AD_Column_Trl
    PERFORM update_Column_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

    --
    -- AD_Field_TRL
    PERFORM update_FieldTranslation_From_AD_Name_Element(p_AD_Element_ID, p_AD_Language);

    --
    -- AD_Process_Para_Trl
    UPDATE AD_Process_Para_Trl t
    SET
        IsTranslated = x.IsTranslated,
        Name = x.Name,
        Description = x.Description,
        Help = x.Help
    FROM
        (
            select
                pp.AD_Process_Para_ID,
                etrl.AD_Element_ID,
                etrl.ad_language,
                etrl.IsTranslated,
                etrl.Name,
                etrl.Description,
                etrl.Help
            from AD_Element_Trl_Effective_v etrl
                     join AD_Process_Para pp on pp.AD_Element_ID = etrl.AD_Element_ID
            where
                    etrl.AD_Element_ID = p_AD_Element_ID
              and pp.IsCentrallyMaintained = 'Y'
              and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
        ) x
    WHERE
            t.AD_Process_Para_ID = x.AD_Process_Para_ID
      and t.AD_Language = x.AD_Language
    ;
    --
    GET DIAGNOSTICS update_count = ROW_COUNT;
    raise notice 'Update % AD_Process_Para_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;


    --
    -- AD_PrintFormatItem_TRL
    UPDATE  AD_PrintFormatItem_Trl t
    SET
        IsTranslated = x.IsTranslated,
        PrintName = x.PrintName
    FROM
        (
            select
                pfi.AD_PrintFormatItem_ID,
                etrl.AD_Element_ID,
                etrl.AD_Language,
                etrl.IsTranslated,
                etrl.PrintName
            from AD_Element_Trl_Effective_v etrl
                     join AD_Column c on c.AD_Element_ID = etrl.AD_Element_ID
                     join AD_PrintFormatItem pfi on c.AD_Column_ID = pfi.AD_Column_ID
            where
                    etrl.AD_Element_ID = p_AD_Element_ID
              and pfi.IsCentrallyMaintained = 'Y'
              and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
        ) x
    WHERE
            t.AD_PrintFormatItem_ID = x.AD_PrintFormatItem_ID
      and t.AD_Language = x.AD_Language
    ;
    --
    GET DIAGNOSTICS update_count = ROW_COUNT;
    raise notice 'Update % AD_PrintFormatItem_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

    --
    -- AD_Tab_TRL
    PERFORM update_Tab_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

    --
    -- AD_Window_TRL
    PERFORM update_Window_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

    --
    -- AD_Menu_TRL
    PERFORM update_Menu_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100;

COMMENT ON FUNCTION update_trl_tables_on_ad_element_trl_update(numeric, character varying) IS
    'When the AD_Element_trl has one of its values changed, the change shall also propagate to the linked table entries:
    -AD_Column_TRL -- name, isTranslated
    -AD_Process_Para_TRL -- name, description, help, isTranslated
    -AD_Field_TRL --name, description, help, isTranslated
    -AD_PrintFormatItem_TRL -- printname, isTranslated
    -AD_Tab_TRL -- name, isTranslated, description, help, commitwarning
    -AD_Window_TRL -- name, isTranslated, description, help
    -AD_Menu_TRL-- Name, IsTranslated, description, help, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb
    ';













------------------------------------





DROP FUNCTION IF EXISTS update_Column_Translation_From_AD_Element(numeric, character varying);

CREATE OR REPLACE FUNCTION update_Column_Translation_From_AD_Element
(
    p_AD_Element_ID numeric,
    p_AD_Language character varying = null
)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN
    UPDATE AD_Column_Trl t
    SET
        IsTranslated = x.IsTranslated,
        Name = x.Name,
        Description = x.Description
    FROM
        (
            select
                c.AD_Column_ID,
                etrl.AD_Element_ID,
                etrl.AD_Language,
                etrl.IsTranslated,
                etrl.Name,
                etrl.Description
            from AD_Element_Trl_Effective_v etrl
                     join AD_Column c on c.AD_Element_ID = etrl.AD_Element_ID
            where
                    etrl.AD_Element_ID = p_AD_Element_ID
              and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
        ) x
    WHERE
            t.AD_Column_ID = x.AD_Column_ID
      and t.AD_Language = x.AD_Language
    ;
    --
    GET DIAGNOSTICS update_count = ROW_COUNT;
    raise notice 'Update % AD_Column_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100;

COMMENT ON FUNCTION update_Column_Translation_From_AD_Element(numeric, character varying) IS
    'Update AD_Column_Trl from AD_Column.AD_Element_ID.';






-----------------------





DROP FUNCTION IF EXISTS update_FieldTranslation_From_AD_Name_Element(numeric, character varying);

CREATE OR REPLACE FUNCTION update_FieldTranslation_From_AD_Name_Element
(
    p_AD_Element_ID numeric,
    p_AD_Language character varying = null
)
    RETURNS void
AS
$BODY$
DECLARE
    update_count_via_AD_Column integer;
    update_count_via_AD_Name integer;
BEGIN
    --
    -- AD_Field_Trl via AD_Column
    UPDATE AD_Field_Trl t
    SET
        IsTranslated = x.IsTranslated,
        Name = x.Name,
        Description = x.Description,
        Help = x.Help
    FROM
        (
            select
                f.AD_Field_ID,
                etrl.AD_Element_ID,
                etrl.AD_Language,
                etrl.IsTranslated,
                etrl.Name,
                etrl.Description,
                etrl.Help
            from AD_Element_Trl_Effective_v etrl
                     join AD_Column c on c.AD_Element_ID = etrl.AD_Element_ID
                     join AD_Field f on f.AD_Column_ID = c.AD_Column_ID
            where
                    etrl.AD_Element_ID = p_AD_Element_ID
              and f.AD_Name_ID is NULL
              and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
        ) x
    WHERE
            t.AD_Field_ID = x.AD_Field_ID
      and t.AD_Language = x.AD_Language
    ;
    --
    GET DIAGNOSTICS update_count_via_AD_Column = ROW_COUNT;


    --
    -- AD_Field_Trl via AD_Element -> AD_Name_ID
    UPDATE AD_Field_Trl t
    SET
        IsTranslated = x.IsTranslated,
        Name = x.Name,
        Description = x.Description,
        Help = x.Help
    FROM
        (
            select
                f.AD_Field_ID,
                etrl.AD_Element_ID,
                etrl.AD_Language,
                etrl.IsTranslated,
                etrl.Name,
                etrl.Description,
                etrl.Help
            from AD_Element_Trl_Effective_v etrl
                     join AD_Field f on f.AD_Name_ID = etrl.AD_Element_ID
            where
                    etrl.AD_Element_ID = p_AD_Element_ID
              and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
        ) x
    WHERE
            t.AD_Field_ID = x.AD_Field_ID
      and t.AD_Language = x.AD_Language
    ;
    --
    GET DIAGNOSTICS update_count_via_AD_Name = ROW_COUNT;

    raise notice 'Updated AD_Field_Trl for AD_Element_ID=%, AD_Language=%: % rows via AD_Column, % rows via AD_Name',
        p_AD_Element_ID, p_AD_Language, update_count_via_AD_Column, update_count_via_AD_Name;

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100;

COMMENT ON FUNCTION update_FieldTranslation_From_AD_Name_Element(numeric, character varying) IS
    'When the AD_Field.AD_Name_ID is changed, uypdate all the AD_Field_Trl entries of the AD_Field, based on the AD_Element behind the AD_Name';



------------------------------------



DROP FUNCTION IF EXISTS update_Tab_Translation_From_AD_Element(numeric, character varying);

CREATE OR REPLACE FUNCTION update_Tab_Translation_From_AD_Element
(
    p_AD_Element_ID numeric,
    p_AD_Language character varying = null
)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN

    UPDATE AD_Tab_Trl t
    SET
        IsTranslated = x.IsTranslated,
        Name = x.Name,
        Description = x.Description,
        Help = x.Help,
        CommitWarning = x.CommitWarning
    FROM
        (
            select
                tab.AD_Tab_ID,
                etrl.AD_Element_ID,
                etrl.AD_Language,
                etrl.IsTranslated,
                etrl.Name,
                etrl.Description,
                etrl.Help,
                etrl.CommitWarning
            from AD_Element_Trl_Effective_v etrl
                     join AD_Tab tab on tab.AD_Element_ID = etrl.AD_Element_ID
            where
                    etrl.AD_Element_ID = p_AD_Element_ID
              and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
        ) x
    WHERE
            t.AD_Tab_ID = x.AD_Tab_ID
      and t.AD_Language = x.AD_Language
    ;

    GET DIAGNOSTICS update_count = ROW_COUNT;
    raise notice 'Update % AD_Tab_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100;

COMMENT ON FUNCTION update_Tab_Translation_From_AD_Element(numeric, character varying) IS
    'When the AD_Tab.AD_Element_ID is changed, update all the AD_Tab_Trl entries of the AD_Tab, based on the AD_Element.';









---------------------








DROP FUNCTION IF EXISTS update_Window_Translation_From_AD_Element(numeric, character varying);

CREATE OR REPLACE FUNCTION update_Window_Translation_From_AD_Element
(
    p_AD_Element_ID numeric,
    p_AD_Language character varying = null
)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN

    UPDATE AD_Window_TRL t
    SET
        IsTranslated = x.IsTranslated,
        Name = x.Name,
        Description = x.Description,
        Help = x.Help
    FROM
        (
            select
                w.AD_Window_ID,
                etrl.AD_Element_ID,
                etrl.ad_language,
                etrl.IsTranslated,
                etrl.Name,
                etrl.description,
                etrl.help
            from AD_Element_Trl_Effective_v etrl
                     join AD_Window w on w.AD_Element_ID = etrl.AD_Element_ID
            where
                    etrl.AD_Element_ID = p_AD_Element_ID
              and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
        ) x
    WHERE
            t.AD_Window_ID = x.AD_Window_ID
      and t.AD_Language = x.AD_Language
    ;

    GET DIAGNOSTICS update_count = ROW_COUNT;
    raise notice 'Update % AD_Window_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100;

COMMENT ON FUNCTION update_Window_Translation_From_AD_Element(numeric, character varying) IS
    'When the AD_Window.AD_Element_ID is changed, update all the AD_Window_Trl entries of the AD_Window, based on the AD_Element.';







-----------------------



DROP FUNCTION IF EXISTS update_Menu_Translation_From_AD_Element(numeric, character varying);

CREATE OR REPLACE FUNCTION update_Menu_Translation_From_AD_Element
(
    p_AD_Element_ID numeric,
    p_AD_Language character varying = null
)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN

    UPDATE AD_Menu_Trl t
    SET
        IsTranslated = x.IsTranslated,
        Name = x.Name,
        Description = x.Description,
        webui_namebrowse = x.webui_namebrowse,
        webui_namenew = x.webui_namenew,
        webui_namenewbreadcrumb = x.webui_namenewbreadcrumb
    FROM
        (
            select
                m.AD_Menu_ID,
                etrl.AD_Element_ID,
                etrl.AD_Language,
                etrl.IsTranslated,
                etrl.Name,
                etrl.Description,
                etrl.webui_namebrowse,
                etrl.webui_namenew,
                etrl.webui_namenewbreadcrumb
            from AD_Element_Trl_Effective_v etrl
                     join AD_Menu m on m.AD_Element_ID = etrl.AD_Element_ID
            where
                    etrl.AD_Element_ID = p_AD_Element_ID
              and (p_AD_Language is null OR etrl.ad_language = p_AD_Language)
        ) x
    WHERE
            t.AD_Menu_ID = x.AD_Menu_ID
      and t.AD_Language = x.AD_Language
    ;

    GET DIAGNOSTICS update_count = ROW_COUNT;
    raise notice 'Update % AD_Menu_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100;

COMMENT ON FUNCTION update_Menu_Translation_From_AD_Element(numeric, character varying) IS
    'When the AD_Menu.AD_Element_ID is changed, update all the AD_Menu_Trl entries of the AD_Menu, based on the AD_Element.';






------------------------------------






-- Function: public.update_ad_element_on_ad_element_trl_update(numeric, character varying)

-- DROP FUNCTION public.update_ad_element_on_ad_element_trl_update(numeric, character varying);

CREATE OR REPLACE FUNCTION public.update_ad_element_on_ad_element_trl_update(
    ad_element_id numeric,
    ad_language character varying)
    RETURNS void AS
$BODY$

BEGIN

    UPDATE AD_Element e
    SET
        name = x.name,
        printname = x.printname,
        description = x.description,
        help = x.help,
        commitwarning = x.commitwarning,
        webui_namebrowse = x.webui_namebrowse,
        webui_namenew = x.webui_namenew,
        webui_namenewbreadcrumb = x.webui_namenewbreadcrumb,
        po_name = x.po_name,
        po_printname = x.po_printname,
        po_description = x.po_description,
        po_help = x.po_help

    FROM
        (
            select
                e.AD_Element_ID,
                etrl.ad_language,
                etrl.Name,
                etrl.printname,
                etrl.description,
                etrl.help,
                etrl.commitwarning,
                etrl.webui_namebrowse,
                etrl.webui_namenew,
                etrl.webui_namenewbreadcrumb,
                etrl.po_name,
                etrl.po_printname,
                etrl.po_description,
                etrl.po_help

            from AD_Element e
                     join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID

            where e.AD_Element_ID = update_ad_element_on_ad_element_trl_update.AD_Element_ID and etrl.ad_language =  update_ad_element_on_ad_element_trl_update.AD_Language

        ) x
    WHERE e.AD_Element_ID = x.AD_Element_ID ;

END;
$BODY$
    LANGUAGE plpgsql VOLATILE SECURITY DEFINER
                     COST 100;
ALTER FUNCTION public.update_ad_element_on_ad_element_trl_update(numeric, character varying)
    OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_ad_element_on_ad_element_trl_update(numeric, character varying) IS 'When the AD_Element_trl has one of its values changed, the change shall also propagate to the parent AD_Element.
This is used for automatic updates on AD_Element which has column that are not manually updatable.
';




----------------------------






-- Function: public.update_ad_element_trl_from_ad_menu_trl(numeric, numeric)

-- DROP FUNCTION IF EXISTS public.update_ad_element_trl_from_ad_menu_trl(numeric, numeric);

CREATE OR REPLACE FUNCTION public.update_ad_element_trl_from_ad_menu_trl(ad_element_id numeric, ad_menu_id numeric)
    RETURNS void AS
$BODY$




BEGIN


    -- AD_Element_TRL via AD_Menu



    UPDATE AD_Element_TRL et SET
                                 AD_Client_ID = x.AD_Client_ID,
                                 AD_Org_ID = x.AD_Org_ID,
                                 Created = x.Created,
                                 CreatedBy = x.CreatedBy,
                                 Updated = x.Updated,
                                 UpdatedBy = x.UpdatedBy,
                                 IsActive = x.IsActive,
                                 AD_Element_ID = x.AD_Element_ID,
                                 AD_Language = x.AD_Language,
                                 Name = x.Name,
                                 PrintName = x.PrintName,
                                 Description = x.Description,
                                 IsTranslated = x.IsTranslated,
                                 WEBUI_NameBrowse = x.WEBUI_NameBrowse,
                                 WEBUI_NameNew = x.WEBUI_NameNew,
                                 WEBUI_NameNewBreadcrumb = x.WEBUI_NameNewBreadcrumb

    FROM

        (Select

             menu.AD_Client_ID,
             menu.AD_Org_ID,
             now() as created,
             100 as createdBy,
             now() as updated,
             100 as updatedBy,
             'Y' as IsActive,
             update_ad_element_trl_from_ad_menu_trl.ad_element_id,
             menut.AD_Language,
             menut.name,
             menut.name as Printname,
             menut.description,
             menut.IsTranslated,
             menut.WEBUI_NameBrowse,
             menut.WEBUI_NameNew,
             menut.WEBUI_NameNewBreadcrumb



         FROM AD_Menu menu
                  JOIn AD_Menu_TRL menut on menu.ad_menu_id = menut.ad_menu_id
         WHERE menu.ad_menu_id = update_ad_element_trl_from_ad_menu_trl.ad_menu_id) x
    WHERE et.AD_Element_ID = x.AD_Element_ID AND et.AD_Language = x.AD_Language;



END;
$BODY$
    LANGUAGE plpgsql VOLATILE SECURITY DEFINER
                     COST 100;
ALTER FUNCTION public.update_ad_element_trl_from_ad_menu_trl(numeric, numeric)
    OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_ad_element_trl_from_ad_menu_trl(numeric, numeric) IS 'When and AD_Element is create with the scope of being set in an AD_Menu entry we must make sure the element translations are copied from the existing AD_Menu_Trl entries.';






---------------------------------





-- Function: public.update_ad_element_trl_from_ad_tab_trl(numeric, numeric)

-- DROP FUNCTION IF EXISTS public.update_ad_element_trl_from_ad_tab_trl(numeric, numeric);

CREATE OR REPLACE FUNCTION public.update_ad_element_trl_from_ad_tab_trl(ad_element_id numeric, ad_tab_id numeric)
    RETURNS void AS
$BODY$




BEGIN


    -- AD_Element_TRL via AD_Tab
    UPDATE AD_Element_TRL et SET
                                 AD_Client_ID = x.AD_Client_ID,
                                 AD_Org_ID = x.AD_Org_ID,
                                 Created = x.Created,
                                 CreatedBy = x.CreatedBy,
                                 Updated = x.Updated,
                                 UpdatedBy = x.UpdatedBy,
                                 IsActive = x.IsActive,
                                 AD_Element_ID = x.AD_Element_ID,
                                 AD_Language = x.AD_Language,
                                 Name = x.Name,
                                 PrintName = x.PrintName,
                                 Description = x.Description,
                                 Help = x.Help,
                                 CommitWarning = x.CommitWarning,
                                 IsTranslated = x.IsTranslated

    FROM

        (Select

             tab.AD_Client_ID,
             tab.AD_Org_ID,
             now() as created,
             100 as createdBy,
             now() as updated,
             100 as updatedBy,
             'Y' as IsActive,
             update_ad_element_trl_from_ad_tab_trl.ad_element_id as AD_Element_ID,
             tabt.AD_Language,
             tabt.name,
             tabt.name as Printname,
             tabt.description,
             tabt.help,
             tabt.commitWarning,
             tabt.IsTranslated

         FROM AD_Tab tab
                  JOIn AD_Tab_TRL tabt on tab.AD_Tab_ID = tabt.AD_Tab_ID
         WHERE tab.ad_tab_ID = update_ad_element_trl_from_ad_tab_trl.ad_tab_ID)x
    WHERE et.AD_Element_ID = x.AD_Element_ID AND et.AD_Language = x.AD_Language;



END;
$BODY$
    LANGUAGE plpgsql VOLATILE SECURITY DEFINER
                     COST 100;
ALTER FUNCTION public.update_ad_element_trl_from_ad_tab_trl(numeric, numeric)
    OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_ad_element_trl_from_ad_tab_trl(numeric, numeric) IS 'When and AD_Element is create with the scope of being set in an AD_Tab entry we must make sure the element translations are copied from the existing ad_tab_trl entries.';










-----------------------








-- Function: public.update_ad_element_trl_from_ad_window_trl(numeric, numeric)

-- DROP FUNCTION IF EXISTS public.update_ad_element_trl_from_ad_window_trl(numeric, numeric);

CREATE OR REPLACE FUNCTION public.update_ad_element_trl_from_ad_window_trl(ad_element_id numeric, ad_window_id numeric)
    RETURNS void AS
$BODY$




BEGIN


    -- AD_Element_TRL via AD_Window

    UPDATE AD_Element_TRL et SET
                                 AD_Client_ID = x.AD_Client_ID,
                                 AD_Org_ID = x.AD_Org_ID,
                                 Created = x.Created,
                                 CreatedBy = x.CreatedBy,
                                 Updated = x.Updated,
                                 UpdatedBy = x.UpdatedBy,
                                 IsActive = x.IsActive,
                                 AD_Element_ID = x.AD_Element_ID,
                                 AD_Language = x.AD_Language,
                                 Name = x.Name,
                                 PrintName = x.PrintName,
                                 Description = x.Description,
                                 Help = x.Help,
                                 IsTranslated = x.IsTranslated

    FROM

        (Select

             win.AD_Client_ID,
             win.AD_Org_ID,
             now() as created,
             100 as createdBy,
             now() as updated,
             100 as updatedBy,
             'Y' as IsActive,
             update_ad_element_trl_from_ad_window_trl.ad_element_id,
             wint.AD_Language,
             wint.name,
             wint.name as Printname,
             wint.description,
             wint.help,
             wint.IsTranslated

         FROM AD_Window win
                  JOIn AD_Window_TRL wint on win.ad_window_id = wint.ad_window_id
         WHERE win.ad_window_id = update_ad_element_trl_from_ad_window_trl.ad_window_id) x
    WHERE et.AD_Element_ID = x.AD_Element_ID AND et.AD_Language = x.AD_Language;






END;
$BODY$
    LANGUAGE plpgsql VOLATILE SECURITY DEFINER
                     COST 100;
ALTER FUNCTION public.update_ad_element_trl_from_ad_window_trl(numeric, numeric)
    OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_ad_element_trl_from_ad_window_trl(numeric, numeric) IS 'When and AD_Element is create with the scope of being set in an AD_Window entry we must make sure the element translations are copied from the existing AD_Window_Trl entries.';







