DO
$$
    BEGIN
    
        PERFORM backup_table('ad_fieldgroup_trl');
        PERFORM backup_table('ad_index_table_trl');
        PERFORM backup_table('ad_infocolumn_trl');
        PERFORM backup_table('ad_ui_section_trl');
        PERFORM backup_table('ad_workflow_trl');
        PERFORM backup_table('c_country_trl');
        PERFORM backup_table('c_currency_trl');
        PERFORM backup_table('c_elementvalue_trl');
        PERFORM backup_table('c_incoterms_trl');
        PERFORM backup_table('c_paymentterm_trl');
        PERFORM backup_table('c_period_trl');
        PERFORM backup_table('c_tax_trl');
        PERFORM backup_table('c_uom_trl');
        PERFORM backup_table('r_requesttype_trl');
        PERFORM backup_table('webui_kpi_field_trl');

        UPDATE ad_fieldgroup_trl
        SET name = data.name_trl
        FROM migration_data."AD_FieldGroup_trad" data
        WHERE ad_fieldgroup_trl.ad_fieldgroup_id = data.ad_fieldgroup_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE ad_index_table_trl
        SET errormsg            = data.errormsg_trl,
            description         = data.description_trl,
            beforechangewarning = data.beforechangewarning_trl
        FROM migration_data.ad_index_table_trad data
        WHERE ad_index_table_trl.ad_index_table_id = data.ad_index_table_id
          AND ad_language IN ('fr_CH', 'fr_FR');


        UPDATE ad_infocolumn_trl
        SET name        = data.name_trl,
            description = data.description_trl,
            help        = data.help_trl
        FROM migration_data."AD_InfoColumn_trad" data
        WHERE ad_infocolumn_trl.ad_infocolumn_id = data.ad_infocolumn_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE ad_ui_section_trl
        SET name        = data.name_trl,
            description = data.description_trl
        FROM migration_data."AD_UI_Section_trad" data
        WHERE ad_ui_section_trl.ad_ui_section_id = data.ad_ui_section_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE ad_workflow_trl
        SET name        = data.name_trl,
            description = data.description_trl,
            help        = data.help_trl
        FROM migration_data.ad_workflow_trad data
        WHERE ad_workflow_trl.ad_workflow_id = data.ad_workflow_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE c_country_trl
        SET name        = data.name_trl,
            description = data.description_trl,
            regionname  = data.regionname_trl
        FROM migration_data.c_country_trad data
        WHERE c_country_trl.c_country_id = data.c_country_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE c_currency_trl
        SET description = data.description_trl
        FROM migration_data.c_currency_trad data
        WHERE c_currency_trl.c_currency_id = data.c_currency_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.description_trl)) > 0;


        UPDATE c_elementvalue_trl
        SET name        = data.name_trl,
            description = data.description_trl
        FROM migration_data."C_ElementValue_trad" data
        WHERE c_elementvalue_trl.c_elementvalue_id = data.c_elementvalue_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE c_incoterms_trl
        SET name        = data.name_trl,
            description = data.description_trl
        FROM migration_data."C_Incoterms_trad" data
        WHERE c_incoterms_trl.c_incoterms_id = data.c_incoterms_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE c_paymentterm_trl
        SET name         = data.name_trl,
            description  = data.description_trl,
            name_invoice = data.name_invoice_trl
        FROM migration_data.c_paymentterm_trad data
        WHERE c_paymentterm_trl.c_paymentterm_id = data.c_paymentterm_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE c_period_trl
        SET name = data.name_trl
        FROM migration_data."C_Period_trad" data
        WHERE c_period_trl.c_period_id = data.c_period_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE c_tax_trl
        SET name         = data.name_trl
          , description  = data.description_trl
          , taxindicator = data.taxindicator_trl
        FROM migration_data."C_Tax_trad" data
        WHERE c_tax_trl.c_tax_id = data.c_tax_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE c_uom_trl
        SET name        = data.name_trl
          , description = data.description_trl
          , uomsymbol   = data.uomsymbol_trl
        FROM migration_data.c_uom_trad data
        WHERE c_uom_trl.c_uom_id = data.c_uom_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE r_requesttype_trl
        SET name        = data.name_trl
          , description = data.description_trl
        FROM migration_data.r_requesttype_trad data
        WHERE r_requesttype_trl.r_requesttype_id = data.r_requesttype_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;


        UPDATE webui_kpi_field_trl
        SET name       = data.name_trl
          , offsetname = data.offsetname_trl
        FROM migration_data."WEBUI_KPI_Field_trad" data
        WHERE webui_kpi_field_trl.webui_kpi_field_id = data.webui_kpi_field_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;

    END;
    
$$
;

