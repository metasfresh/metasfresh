INSERT INTO ad_column (ad_column_id, ad_client_id, ad_org_id, isactive, created, updated, createdby, updatedby, name, description, help, version, entitytype, columnname, ad_table_id, ad_reference_id, ad_reference_value_id, ad_val_rule_id, fieldlength, defaultvalue, iskey, isparent, ismandatory, isupdateable, readonlylogic, isidentifier, seqno, istranslated, isencrypted, callout, vformat, valuemin, valuemax, isselectioncolumn, ad_element_id, ad_process_id, issyncdatabase, isalwaysupdateable, columnsql, mandatorylogic, infofactoryclass, isautocomplete, isallowlogging, formatpattern, isadvancedtext, islazyloading, iscalculated, isgenericzoomorigin, isgenericzoomkeycolumn, isusedocsequence, isstaleable, ddl_noforeignkey, isdimension, isdlmpartitionboundary, cacheinvalidateparent, selectioncolumnseqno, israngefilter, isshowfilterincrementbuttons, filterdefaultvalue, isforceincludeingeneratedmodel, technicalnote, personaldatacategory, allowzoomto, isautoapplyvalidationrule, isfacetfilter, maxfacetstofetch, facetfilterseqno, isshowfilterinline, filteroperator, isexcludefromzoomtargets)
select 567911, 0, 0, 'Y', '2019-05-08 17:33:59.000000 +02:00', '2021-12-10 13:25:45.000000 +01:00', 100, 100, 'Alternative eMail', 'EMail-Adresse', '', 0, 'D', 'EMail2', 114, 10, NULL, NULL, 100, NULL, 'N', 'N', 'N', 'Y', '', 'N', 0, 'N', 'N', NULL, NULL, NULL, NULL, 'N', 576305, NULL, 'N', 'N', NULL, NULL, NULL, 'N', 'Y', NULL, 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 0, 'N', 'N', NULL, 'N', NULL, NULL, NULL, 'N', 'N', 0, NULL, 'N', NULL, 'N'
where not exists (select 1 from ad_column where ad_column_id=567911);

INSERT INTO ad_element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew) 
 select 576712, 0, 0, 'Y', '2019-05-08 17:21:04.000000 +02:00', 100, '2019-05-08 17:21:04.000000 +02:00', 100, 'EMail3', 'D', 'EMail3', 'EMail3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL
where not exists (select 1 from ad_element where ad_element_id=576712);

INSERT INTO ad_column (ad_column_id, ad_client_id, ad_org_id, isactive, created, updated, createdby, updatedby, name, description, help, version, entitytype, columnname, ad_table_id, ad_reference_id, ad_reference_value_id, ad_val_rule_id, fieldlength, defaultvalue, iskey, isparent, ismandatory, isupdateable, readonlylogic, isidentifier, seqno, istranslated, isencrypted, callout, vformat, valuemin, valuemax, isselectioncolumn, ad_element_id, ad_process_id, issyncdatabase, isalwaysupdateable, columnsql, mandatorylogic, infofactoryclass, isautocomplete, isallowlogging, formatpattern, isadvancedtext, islazyloading, iscalculated, isgenericzoomorigin, isgenericzoomkeycolumn, isusedocsequence, isstaleable, ddl_noforeignkey, isdimension, isdlmpartitionboundary, cacheinvalidateparent, selectioncolumnseqno, israngefilter, isshowfilterincrementbuttons, filterdefaultvalue, isforceincludeingeneratedmodel, technicalnote, personaldatacategory, allowzoomto, isautoapplyvalidationrule, isfacetfilter, maxfacetstofetch, facetfilterseqno, isshowfilterinline, filteroperator, isexcludefromzoomtargets) 
select 567912, 0, 0, 'Y', '2019-05-08 17:34:09.000000 +02:00', '2021-12-10 13:25:55.000000 +01:00', 100, 100, 'EMail3', NULL, NULL, 0, 'D', 'EMail3', 114, 10, NULL, NULL, 100, NULL, 'N', 'N', 'N', 'Y', '', 'N', 0, 'N', 'N', NULL, NULL, NULL, NULL, 'N', 576712, NULL, 'N', 'N', NULL, NULL, NULL, 'N', 'Y', NULL, 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 0, 'N', 'N', NULL, 'N', NULL, NULL, NULL, 'N', 'N', 0, NULL, 'N', NULL, 'N'
where not exists (select 1 from ad_column where ad_column_id=567912);-- 2021-12-17T11:02:08.680Z

DO $$
    BEGIN
        BEGIN
            -- 2021-12-20T10:40:14.774
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            /* DDL */ ALTER TABLE public.AD_User ADD COLUMN EMail3 VARCHAR(100);

        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column EMail3 already exists in AD_User.';
        END;
    END;
$$;

DO $$
    BEGIN
        BEGIN
            -- 2021-12-20T10:40:14.774
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            /* DDL */ ALTER TABLE public.AD_User ADD COLUMN EMail2 VARCHAR(100);

        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column EMail2 already exists in AD_User.';
        END;
    END;
$$;

