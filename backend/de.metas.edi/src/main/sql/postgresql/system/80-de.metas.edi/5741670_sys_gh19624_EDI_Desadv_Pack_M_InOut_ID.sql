-- Column: EDI_Desadv_Pack.M_InOut_ID
-- Column SQL (old): (SELECT line.m_inout_id         from M_HU hu                  INNER JOIN M_HU_Assignment assignment on hu.m_hu_id = assignment.m_hu_id AND assignment.ad_table_id = get_table_id('M_InOutLine')                  INNER JOIN M_InOutLine line on line.m_inoutline_id = assignment.record_id         where hu.m_hu_id = EDI_Desadv_Pack.m_hu_id)
-- 2024-12-13T11:35:20.642Z
UPDATE AD_Column SET ColumnSQL='(SELECT DISTINCT EDI_Desadv_Pack_Item.M_InOut_ID         from EDI_Desadv_Pack_Item         where EDI_Desadv_Pack_Item.EDI_Desadv_Pack_ID = EDI_Desadv_Pack.EDI_Desadv_Pack_ID)',Updated=TO_TIMESTAMP('2024-12-13 12:38:30.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589362
;
