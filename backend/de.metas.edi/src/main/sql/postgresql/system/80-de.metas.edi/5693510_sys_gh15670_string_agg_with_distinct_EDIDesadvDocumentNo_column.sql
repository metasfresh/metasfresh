-- Column: C_Invoice.EDIDesadvDocumentNo
-- Column SQL (old): 1=1
-- Column: C_Invoice.EDIDesadvDocumentNo
-- Column SQL (old): 1=1
-- 2023-06-27T13:17:01.395Z
UPDATE AD_Column SET ColumnSQL='(select string_agg(distinct edi.DocumentNo, '','')         from c_invoiceline icl                  inner join c_invoice_line_alloc inalloc on icl.c_invoiceline_id = inalloc.c_invoiceline_id                  inner join C_InvoiceCandidate_InOutLine candinout                             on inalloc.c_invoice_candidate_id = candinout.c_invoice_candidate_id                  inner join M_InOutLine inoutline on candinout.m_inoutline_id = inoutline.m_inoutline_id                  inner join m_inout inout on inoutline.m_inout_id = inout.m_inout_id                  inner join EDI_Desadv edi on inout.EDI_Desadv_ID = edi.EDI_Desadv_ID         where icl.C_Invoice_ID = C_Invoice.C_Invoice_ID)',Updated=TO_TIMESTAMP('2023-06-27 16:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586846
;