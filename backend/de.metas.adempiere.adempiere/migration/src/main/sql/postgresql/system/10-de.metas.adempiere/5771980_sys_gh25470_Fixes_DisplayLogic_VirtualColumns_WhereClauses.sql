UPDATE AD_Ref_Table
SET WhereClause='exists(select 1 from C_Dunning_Candidate c join C_DunningLevel l on c.C_DunningLevel_ID = l.C_DunningLevel_ID    join C_Dunningdoc d on d.C_DunningLevel_ID = l.C_DunningLevel_ID where c.C_Dunning_Candidate_ID = C_Dunning_Candidate.C_Dunning_Candidate_ID and d.C_Dunningdoc_ID = @C_Dunningdoc_ID/-1@)', Updated=TO_TIMESTAMP('2025-10-02 12:49:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Reference_ID = 541441
;

-- Column: M_Shipment_Constraint.C_Invoice_ID
-- Column SQL (old): ( select i.c_invoice_ID from c_invoice i   join c_dunning_candidate dc on  i.c_invoice_id = dc.record_id and dc.ad_table_id = get_table_ID('C_Invoice')   join c_dunningdoc_line_source dls on dc.c_dunning_candidate_id = dls.c_dunning_candidate_id   join c_dunningdoc_line ddl on dls.c_dunningdoc_line_id = ddl.c_dunningdoc_line_id   join c_dunningdoc dd on ddl.c_dunningdoc_id =  dd.c_dunningdoc_id join m_shipment_constraint sc on dd.c_dunningdoc_id = sc.sourcedoc_record_id and sc.Sourcedoc_Table_ID = get_Table_ID ('C_DunningDoc') where sc.m_shipment_constraint_ID = @JoinTableNameOrAliasIncludingDot@m_shipment_constraint_ID )
-- 2025-10-01T17:32:04.604Z
UPDATE AD_Column SET ColumnSQL='(SELECT i.c_invoice_ID         from c_invoice i                  JOIN c_dunning_candidate dc on i.c_invoice_id = dc.record_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')                  JOIN c_dunningdoc_line_source dls on dc.c_dunning_candidate_id = dls.c_dunning_candidate_id                  JOIN c_dunningdoc_line ddl on dls.c_dunningdoc_line_id = ddl.c_dunningdoc_line_id                  JOIN c_dunningdoc dd on ddl.c_dunningdoc_id = dd.c_dunningdoc_id                  JOIN m_shipment_constraint sc on dd.c_dunningdoc_id = sc.sourcedoc_record_id AND sc.Sourcedoc_Table_ID = get_Table_ID(''C_DunningDoc'')         where sc.m_shipment_constraint_ID = @M_Shipment_Constraint_ID/-1@)',Updated=TO_TIMESTAMP('2025-10-01 17:32:04.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=559520
;

-- Field: Lieferung(169,D) -> Lieferung(257,D) -> Incoterm Ort
-- Column: M_InOut.IncotermLocation
-- 2025-10-01T17:37:05.116Z
UPDATE AD_Field SET DisplayLogic='@Incoterm/null@!=null',Updated=TO_TIMESTAMP('2025-10-01 17:37:05.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=501623
;

-- Field: Handling Unit(540189,de.metas.handlingunits) -> Handling Unit(540508,de.metas.handlingunits) -> Freigabedatum
-- Column: M_HU.ClearanceDate
-- 2025-10-01T17:58:39.746Z
UPDATE AD_Field SET DisplayLogic='@ClearanceNote/null@!=null',Updated=TO_TIMESTAMP('2025-10-01 17:58:39.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=707858
;

-- Field: Handling Unit(540189,de.metas.handlingunits) -> Handling Unit(540508,de.metas.handlingunits) -> Freigabe-Notiz
-- Column: M_HU.ClearanceNote
-- 2025-10-01T17:59:08.817Z
UPDATE AD_Field SET DisplayLogic='@ClearanceNote/null@!=null',Updated=TO_TIMESTAMP('2025-10-01 17:59:08.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=678502
;

-- Column: C_BPartner_Export.BPValue
-- 2025-10-03T11:56:52.938Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2025-10-03 11:56:52.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=574783
;

-- Column: C_BPartner_Export.BPName
-- 2025-10-03T11:57:04.959Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2025-10-03 11:57:04.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=574784
;
