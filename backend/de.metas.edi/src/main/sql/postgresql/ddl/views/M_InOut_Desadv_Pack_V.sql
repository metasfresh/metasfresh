drop view if exists M_InOut_Desadv_Pack_V
;

create or replace view M_InOut_Desadv_Pack_V as
select *,
       (SELECT line.m_inout_id
        from M_HU hu
                 INNER JOIN M_HU_Assignment assignment on hu.m_hu_id = assignment.m_hu_id AND assignment.ad_table_id = get_table_id('M_InOutLine')
                 INNER JOIN M_InOutLine line on line.m_inoutline_id = assignment.record_id
        where hu.m_hu_id = EDI_Desadv_Pack.m_hu_id) as m_inout_id,
       (select c.PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=EDI_Desadv_Pack.M_HU_PackagingCode_ID) as M_HU_PackagingCode_Text
from EDI_Desadv_Pack
;
