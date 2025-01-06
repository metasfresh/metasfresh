DROP VIEW IF EXISTS M_InOut_Desadv_Pack_V
;

CREATE OR REPLACE VIEW M_InOut_Desadv_Pack_V AS
SELECT pack.*,
       inout_for_pack.m_inout_id,
       (SELECT c.PackagingCode FROM M_HU_PackagingCode c WHERE c.M_HU_PackagingCode_ID = pack.M_HU_PackagingCode_ID) AS M_HU_PackagingCode_Text
FROM EDI_Desadv_Pack pack
         inner join (select distinct item.edi_desadv_pack_id, item.m_inout_id from edi_desadv_pack_item item) inout_for_pack on pack.edi_desadv_pack_id = inout_for_pack.edi_desadv_pack_id
;

