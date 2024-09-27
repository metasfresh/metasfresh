


-- Please, ignore or comment out the content of this script if the column already exists!





INSERT INTO public.ad_column (ad_column_id, ad_client_id, ad_org_id, isactive, created, updated, createdby, updatedby, name, description, help, version, entitytype, columnname, ad_table_id, ad_reference_id, ad_reference_value_id, ad_val_rule_id, fieldlength, defaultvalue, iskey, isparent, ismandatory, isupdateable, readonlylogic, isidentifier, seqno, istranslated, isencrypted, callout, vformat,
                              valuemin, valuemax, isselectioncolumn, ad_element_id, ad_process_id, issyncdatabase, isalwaysupdateable, columnsql, mandatorylogic, infofactoryclass, isautocomplete, isallowlogging, formatpattern, isadvancedtext, islazyloading, iscalculated, isgenericzoomorigin, isgenericzoomkeycolumn, isusedocsequence, isstaleable, ddl_noforeignkey, isdimension,
                              isdlmpartitionboundary, cacheinvalidateparent, selectioncolumnseqno, israngefilter, isshowfilterincrementbuttons, filterdefaultvalue, isforceincludeingeneratedmodel, technicalnote, personaldatacategory, allowzoomto, isautoapplyvalidationrule, isfacetfilter, maxfacetstofetch, facetfilterseqno, isshowfilterinline, filteroperator, isexcludefromzoomtargets,
                              isrestapicustomcolumn, filter_val_rule_id, ad_sequence_id)
VALUES (587016, 0, 0, 'Y', '2023-06-29 16:38:12.626000 +02:00', '2021-06-23 14:13:33.000000 +02:00', 100, 100, 'Externe ID', NULL, NULL, 0, 'D', 'ExternalId', 541761, 10, NULL, NULL, 255, '', 'N', 'N', 'N', 'Y', NULL, 'N', 0, 'N', 'N', NULL, NULL, NULL, NULL, 'N', 543939, NULL, 'N', 'N', NULL, NULL, NULL, 'N', 'Y', NULL, 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 0, 'N', 'N', NULL, 'N', NULL, NULL, NULL, 'N', 'N', 0, 0, 'N', NULL, 'Y', 'N', NULL, NULL)
;



SELECT public.db_alter_table('C_BPartner_Adv_Search', 'ALTER TABLE public.C_BPartner_Adv_Search ADD COLUMN ExternalId VARCHAR(255)')
;
