

INSERT INTO public.ad_column (ad_column_id, ad_client_id, ad_org_id, isactive, created, updated, createdby, updatedby, name, description, help, version, entitytype,
 columnname, ad_table_id, ad_reference_id, ad_reference_value_id, ad_val_rule_id, fieldlength, defaultvalue, iskey, isparent, ismandatory, isupdateable, readonlylogic, 
 isidentifier, seqno, istranslated, isencrypted, callout, vformat, valuemin, valuemax, isselectioncolumn, ad_element_id, ad_process_id, issyncdatabase, isalwaysupdateable,
 columnsql, mandatorylogic, infofactoryclass, isautocomplete, isallowlogging, formatpattern, isadvancedtext, islazyloading, iscalculated, isgenericzoomorigin, 
 isgenericzoomkeycolumn, isusedocsequence, isstaleable, ddl_noforeignkey, isdimension, isdlmpartitionboundary, cacheinvalidateparent, selectioncolumnseqno, 
 isshowfilterincrementbuttons, filterdefaultvalue, isforceincludeingeneratedmodel, technicalnote, personaldatacategory, allowzoomto, isautoapplyvalidationrule, 
 isfacetfilter, maxfacetstofetch, facetfilterseqno, isshowfilterinline, filteroperator)
 VALUES (572470, 0, 0, 'Y', '2021-01-21 11:46:47.000000', '2021-01-21 11:49:06.000000', 100, 100, 'Haddex Prüfung erforderlich', null, null, 0, 'D', 
 'IsHaddexCheck', 291, 20, null, null, 1, 'N', 'N', 'N', 'Y', 'Y', null, 'N', 0, 'N', 'N', null, null, null, null, 'N', 578664, null, 'N', 
 'N', null, null, null, 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 0, 'N', null, 'N', null, null, null, 'N', 'N', 0, 0, 'N', null);



-- 2021-01-21T11:49:06.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Help=NULL, ColumnName='IsHaddexCheck', Description=NULL, AD_Element_ID=578664, Name='Haddex Prüfung erforderlich',Updated=TO_TIMESTAMP('2021-01-21 13:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572470
;

-- 2021-01-21T11:49:06.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578664) 
;
