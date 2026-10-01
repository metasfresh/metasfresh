-- me03 #30308 — restore the DESADV recipient GLN on the legacy replication-interface export
-- https://github.com/metasfresh/me03/issues/30308
--
-- Regression introduced by https://github.com/metasfresh/me03/issues/30238
-- (PR https://github.com/metasfresh/metasfresh/pull/24423): the EDI recipient-GLN config was
-- moved from C_BPartner columns into the new child table C_BPartner_EDI_Setting, and migration
-- 5806040 DROPPed C_BPartner.EdiDesadvRecipientGLN. Dropping that physical column cascade-deleted
-- the EXP_FormatLine in the legacy replication export format EDI_Exp_C_BPartner that produced the
-- XML element /EDI_Exp_Desadv/C_BPartner_ID/EdiRecipientGLN. PR 24423 repointed the JSON + cctop
-- export VIEWS to C_BPartner_EDI_Setting, but NOT the legacy replication (EXP_Format) export path,
-- so DESADV XML files generated via the replication interface no longer carry the recipient GLN
-- ("no receiver provided" on the customer side).
--
-- Fix (the "SQL-Column" approach): recreate a virtual ColumnSQL column EdiDesadvRecipientGLN on
-- C_BPartner that resolves the GLN from the partner-default row of C_BPartner_EDI_Setting, and
-- recreate the cascade-deleted EXP_FormatLine (XML element name 'EdiRecipientGLN', preserving the
-- exact prior XML path) referencing that virtual column.
--
-- Resolution is partner-level (partner-default row, C_BPartner_Location_ID IS NULL) because the
-- legacy export's recipient GLN is emitted under the partner node (C_BPartner_ID), which is
-- partner-keyed. For partners migrated by 5806030 this is exactly the prior behaviour (each got a
-- single partner-default row). The XML tag is driven by EXP_FormatLine.Value (ExportHelper:
-- outDocument.createElement(formatLine.getValue())), not by the column name.
--
-- IDs allocated from idserver.metas.de on 2026-06-08:
--   AD_Column      592733 (C_BPartner.EdiDesadvRecipientGLN, virtual ColumnSQL)
--   EXP_FormatLine 550960 (EDI_Exp_C_BPartner -> EdiRecipientGLN)
-- Reused: AD_Element 542001 (EdiRecipientGLN / "EDI-ID des DESADV-Empfängers").
--
-- NOTE: no AD_SQLColumn_SourceTableColumn (cache-invalidation) entry is created for this virtual
-- column. That mechanism only matters for columns rendered in a WebUI grid (so the grid refreshes
-- when the source table changes); this column has NO AD_Field and is read ONLY by the replication
-- EXP_Format export, which re-evaluates the ColumnSQL fresh on every export — there is no cached
-- grid value to invalidate, so an entry would guard against no real failure.
-- Reused: EXP_Format 540385 (EDI_Exp_C_BPartner), AD_Table 291 (C_BPartner),
--         AD_Table 542610 (C_BPartner_EDI_Setting), AD_Column 592678 (C_BPartner_EDI_Setting.C_BPartner_ID).

-- ---------------------------------------------------------------------------------------------
-- Step 1: recreate the virtual ColumnSQL column on C_BPartner.
-- Clone an existing C_BPartner virtual string column (City, AD_Column 557178) so every standard /
-- NOT NULL column is carried over, then override the fields that differ. IsSyncDatabase='N' keeps
-- it virtual (no physical DDL). Keywords lowercased per the Convert_PostgreSQL rule.
--
-- The own-table key is referenced as the RAW name `C_BPartner.C_BPartner_ID`, NOT the
-- @JoinTableNameOrAliasIncludingDot@ placeholder: this column is read ONLY by the legacy
-- replication EXP_Format export (ExportHelper), which loads the C_BPartner PO under its raw table
-- name and does NOT perform the placeholder substitution (that substitution is a WebUI/ColumnSql
-- step). The placeholder would reach PostgreSQL verbatim -> "column jointablenameoraliasincludingdot
-- does not exist". This matches the sibling EDI-export virtual columns on C_BPartner (City,
-- Address1, EMail, Invoice_Email), which all use the raw `C_BPartner.C_BPartner_ID` form. The
-- column has no AD_Field and never appears in a WebUI grid, so the master-alias concern behind the
-- placeholder rule does not apply here.
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Column (
    ad_column_id, ad_client_id, ad_org_id, isactive, created, updated, createdby, updatedby,
    name, description, help, version, entitytype, columnname, ad_table_id, ad_reference_id,
    ad_reference_value_id, ad_val_rule_id, fieldlength, defaultvalue, iskey, isparent, ismandatory,
    isupdateable, readonlylogic, isidentifier, seqno, istranslated, isencrypted, callout, vformat,
    valuemin, valuemax, isselectioncolumn, ad_element_id, ad_process_id, issyncdatabase,
    isalwaysupdateable, columnsql, mandatorylogic, infofactoryclass, isautocomplete, isallowlogging,
    formatpattern, isadvancedtext, islazyloading, iscalculated, isgenericzoomorigin,
    isgenericzoomkeycolumn, isusedocsequence, isstaleable, ddl_noforeignkey, isdimension,
    isdlmpartitionboundary, cacheinvalidateparent, selectioncolumnseqno, israngefilter,
    isshowfilterincrementbuttons, filterdefaultvalue, isforceincludeingeneratedmodel, technicalnote,
    personaldatacategory, allowzoomto, isautoapplyvalidationrule, isfacetfilter, maxfacetstofetch,
    facetfilterseqno, isshowfilterinline, filteroperator, isexcludefromzoomtargets,
    isrestapicustomcolumn, cloningstrategy, isshowfilterinactivevalues
)
SELECT
    592733 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-06-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2026-06-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 100,
    (SELECT Name        FROM AD_Element WHERE AD_Element_ID=542001),  -- name
    (SELECT Description FROM AD_Element WHERE AD_Element_ID=542001),  -- description
    (SELECT Help        FROM AD_Element WHERE AD_Element_ID=542001),  -- help
    0, 'de.metas.esb.edi',
    'EdiDesadvRecipientGLN',                                   -- columnname
    291,                                                       -- ad_table_id (C_BPartner)
    10,                                                        -- ad_reference_id (String)
    ad_reference_value_id, ad_val_rule_id,
    255,                                                       -- fieldlength
    defaultvalue, 'N', 'N', 'N',
    'N',                                                       -- isupdateable
    readonlylogic, 'N', seqno, 'N', 'N', callout, vformat,
    valuemin, valuemax, isselectioncolumn,
    542001,                                                    -- ad_element_id
    ad_process_id,
    'N',                                                       -- issyncdatabase (virtual)
    isalwaysupdateable,
    '(select s.edidesadvrecipientgln from c_bpartner_edi_setting s'
    || ' where s.c_bpartner_id = C_BPartner.C_BPartner_ID'
    || ' and s.c_bpartner_location_id is null and s.isactive = ''Y'''
    || ' order by s.c_bpartner_edi_setting_id limit 1)',       -- columnsql
    mandatorylogic, infofactoryclass, isautocomplete, isallowlogging,
    formatpattern, isadvancedtext, islazyloading, iscalculated, isgenericzoomorigin,
    isgenericzoomkeycolumn, isusedocsequence, isstaleable, ddl_noforeignkey, isdimension,
    isdlmpartitionboundary, cacheinvalidateparent, selectioncolumnseqno, israngefilter,
    isshowfilterincrementbuttons, filterdefaultvalue, isforceincludeingeneratedmodel, technicalnote,
    personaldatacategory, allowzoomto, isautoapplyvalidationrule, isfacetfilter, maxfacetstofetch,
    facetfilterseqno, isshowfilterinline, filteroperator, isexcludefromzoomtargets,
    isrestapicustomcolumn, cloningstrategy, isshowfilterinactivevalues
FROM AD_Column
WHERE AD_Column_ID=557178   -- C_BPartner.City (template virtual ColumnSQL column)
;

-- Step 1b: skeleton AD_Column_Trl rows for all system languages, then propagate from AD_Element.
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592733
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542001);

-- ---------------------------------------------------------------------------------------------
-- Step 2: recreate the cascade-deleted EXP_FormatLine in EDI_Exp_C_BPartner (EXP_Format 540385).
-- Clone an existing scalar (type 'E') line of the same format (CreditorId / Kreditoren-Nr,
-- EXP_FormatLine 549319) to carry every standard column, then override Value (= XML element name),
-- Name, the referenced AD_Column, Position and activate it.
-- ---------------------------------------------------------------------------------------------
INSERT INTO EXP_FormatLine (
    exp_formatline_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
    value, name, description, help, exp_format_id, position, ismandatory, type, ad_column_id,
    exp_embeddedformat_id, ispartuniqueindex, dateformat, entitytype, defaultvalue,
    ad_reference_override_id, filteroperator
)
SELECT
    550960 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-06-08 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-08 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'EdiRecipientGLN',                                          -- value (drives the XML tag)
    'EdiRecipientGLN',                                          -- name
    description, help, exp_format_id,
    1120,                                                       -- position (after current max 1110)
    ismandatory, type,
    592733,                                                     -- ad_column_id (new virtual column)
    exp_embeddedformat_id, ispartuniqueindex, dateformat, entitytype, defaultvalue,
    ad_reference_override_id, filteroperator
FROM EXP_FormatLine
WHERE EXP_FormatLine_ID=549319   -- EDI_Exp_C_BPartner / CreditorId (template scalar 'E' line)
;
