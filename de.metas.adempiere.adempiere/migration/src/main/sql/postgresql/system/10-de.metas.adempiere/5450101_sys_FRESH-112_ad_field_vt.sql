-- NOTE: keep in sync with ad_field_v

drop view if exists ad_field_vt;
CREATE OR REPLACE VIEW ad_field_vt AS 
SELECT
c_trl.ad_language
, t.ad_window_id
, f.colorlogic
, t.ad_tab_id
, f.ad_field_id
, tbl.ad_table_id
, c.ad_column_id
, COALESCE(f.name, c.name) AS name_BaseLang
, COALESCE(f_trl.name, f.name, c_trl.name, c.name) AS name
, COALESCE(f.description, c.description) AS description_BaseLang
, COALESCE(f.help, t.help) AS help_BaseLang
, COALESCE(f_trl.description, f.description, c.description) AS description
, COALESCE(f_trl.help, f.help, t.help) AS help
, f.isdisplayed
, f.isdisplayedgrid
, f.displaylogic
, COALESCE(f.displaylength, 0::numeric) AS displaylength
, f.columndisplaylength
, f.seqno
, f.seqnogrid
, f.sortno
, COALESCE(f.issameline, 'N'::bpchar) AS issameline
, COALESCE(f.isheading, 'N'::bpchar) AS isheading
, COALESCE(f.isfieldonly, 'N'::bpchar) AS isfieldonly
, COALESCE(f.isreadonly, 'Y'::bpchar) AS isreadonly
, COALESCE(f.isencrypted, c.isencrypted) AS isencryptedfield
, f.obscuretype
, c.columnname
, c.columnsql
, c.fieldlength
, c.vformat
, COALESCE(f.defaultvalue, c.defaultvalue) AS defaultvalue
, c.iskey
, c.isparent
, COALESCE(f.ismandatory, c.ismandatory) AS ismandatory
, c.IsMandatory as IsMandatoryDB
, c.isidentifier
, c.istranslated
, COALESCE(f.ad_reference_value_id, c.ad_reference_value_id) AS ad_reference_value_id
, c.callout
, COALESCE(f.ad_reference_id, c.ad_reference_id) AS ad_reference_id
, COALESCE(f.ad_val_rule_id, c.ad_val_rule_id) AS ad_val_rule_id
, c.ad_process_id
, c.isalwaysupdateable
, c.readonlylogic
, c.mandatorylogic
, c.isupdateable
, c.isencrypted AS isencryptedcolumn
, c.isselectioncolumn
, tbl.tablename
, c.valuemin
, c.valuemax
, fg.name AS fieldgroup_BaseLang
, COALESCE(fg_trl.name, fg.name) AS fieldgroup
, vr.code AS validationcode
, f.included_tab_id
, fg.fieldgrouptype
, fg.iscollapsedbydefault
, COALESCE(f.infofactoryclass, c.infofactoryclass) AS infofactoryclass
, c.isautocomplete
, f.includedtabheight
, c.iscalculated
, c.FormatPattern
, f.SpanX
, f.SpanY
, f.EntityType as FieldEntityType
FROM ad_tab t
JOIN ad_table tbl ON tbl.ad_table_id = t.ad_table_id
JOIN ad_column c ON c.ad_table_id = t.ad_table_id
JOIN ad_column_trl c_trl ON c_trl.ad_column_id = c.ad_column_id
LEFT JOIN ad_field f ON f.ad_tab_id = t.ad_tab_id AND f.ad_column_id = c.ad_column_id
LEFT JOIN ad_field_trl f_trl ON f_trl.ad_field_id = f.ad_field_id AND f_trl.ad_language::text = c_trl.ad_language::text
LEFT JOIN ad_fieldgroup fg ON fg.ad_fieldgroup_id = f.ad_fieldgroup_id
LEFT JOIN ad_fieldgroup_trl fg_trl ON fg_trl.ad_fieldgroup_id = f.ad_fieldgroup_id AND fg_trl.ad_language::text = c_trl.ad_language::text
LEFT JOIN ad_val_rule vr ON vr.ad_val_rule_id = COALESCE(f.ad_val_rule_id, c.ad_val_rule_id)
WHERE (f.ad_field_id IS NULL OR f.isactive = 'Y'::bpchar) AND c.isactive = 'Y'::bpchar
;



