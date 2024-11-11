
CREATE TABLE migration_data."AD_Menu_Trad"
(
    ad_menu_id                   numeric NOT NULL,
    name_de                      text,
    name_trad                    text,
    name_trl                     text,
    description_de               text,
    description_trad             text,
    description_trl              text,
    webui_namebrowse_de          text,
    webui_namebrowse_trad        text,
    webui_namebrowse_trl         text,
    webui_namenew_de             text,
    webui_namenew_trad           text,
    webui_namenew_trl            text,
    webui_namenewbreadcrumb_de   text,
    webui_namenewbreadcrumb_trad text,
    webui_namenewbreadcrumb_trl  text
)
;

ALTER TABLE migration_data."AD_Menu_Trad"
    OWNER TO metasfresh
;

