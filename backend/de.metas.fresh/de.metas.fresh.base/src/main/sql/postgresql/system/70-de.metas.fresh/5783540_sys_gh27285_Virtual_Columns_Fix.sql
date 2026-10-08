-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Candidate.QualityDiscountPercent_ReceiptSchedule
-- Column SQL (old): (select rs.qualityDiscountPercent from M_ReceiptSchedule rs  where exists (select 1 from M_ReceiptSchedule_Alloc rsa where rsa.m_receiptschedule_id=rs.m_receiptschedule_id) and rs.C_OrderLine_ID = C_Invoice_Candidate.C_OrderLine_ID)
-- 2026-01-13T09:57:10.676Z
UPDATE AD_Column SET ColumnSQL='(select MAX(rs.qualityDiscountPercent) from M_ReceiptSchedule rs  where exists (select 1 from M_ReceiptSchedule_Alloc rsa where rsa.m_receiptschedule_id=rs.m_receiptschedule_id) and rs.C_OrderLine_ID = C_Invoice_Candidate.C_OrderLine_ID)',Updated=TO_TIMESTAMP('2026-01-13 09:57:10.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549869
;

-- Column: C_Invoice_Candidate.QualityNote_ReceiptSchedule
-- Column SQL (old): ( select rs.QualityNote from M_ReceiptSchedule rs where exists (select 1 from M_ReceiptSchedule_Alloc rsa where rsa.m_receiptschedule_id=rs.m_receiptschedule_id and rs.C_OrderLine_ID = C_Invoice_Candidate.C_OrderLine_ID) )
-- 2026-01-13T10:00:54.127Z
UPDATE AD_Column SET ColumnSQL='( select STRING_AGG(DISTINCT rs.QualityNote, '', '') from M_ReceiptSchedule rs where exists (select 1 from M_ReceiptSchedule_Alloc rsa where rsa.m_receiptschedule_id=rs.m_receiptschedule_id and rs.C_OrderLine_ID = C_Invoice_Candidate.C_OrderLine_ID) )',Updated=TO_TIMESTAMP('2026-01-13 10:00:54.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=550198
;

-- Column: EDI_Desadv_Pack.M_InOut_ID
-- Column SQL (old): (SELECT DISTINCT EDI_Desadv_Pack_Item.M_InOut_ID         from EDI_Desadv_Pack_Item         where EDI_Desadv_Pack_Item.EDI_Desadv_Pack_ID = EDI_Desadv_Pack.EDI_Desadv_Pack_ID)
-- 2026-01-13T10:37:14.273Z
UPDATE AD_Column SET ColumnSQL='(SELECT MAX(EDI_Desadv_Pack_Item.M_InOut_ID)        from EDI_Desadv_Pack_Item         where EDI_Desadv_Pack_Item.EDI_Desadv_Pack_ID = EDI_Desadv_Pack.EDI_Desadv_Pack_ID)',Updated=TO_TIMESTAMP('2026-01-13 10:37:14.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589362
;

