drop view if exists M_InOut_DesadvLine_Pack_V
;

create or replace view M_InOut_DesadvLine_Pack_V as
select edi_desadvline_id || '-' || m_inoutline_id as M_InOut_DesadvLine_V_ID,
       ad_client_id,
       ad_org_id,
       created,
       createdby,
       edi_desadvline_id,
       edi_desadvline_pack_id,
       ipa_sscc18,
       isactive,
       updated,
       updatedby,
       qtytu,
       qtycu,
       qtycusperlu,
       m_hu_id,
       edi_desadv_id,
       ismanual_ipa_sscc18,
       bestbeforedate,
       m_hu_packagingcode_lu_id,
       m_hu_packagingcode_tu_id,
       c_uom_id,
       qtyitemcapacity,
       movementqty,
       m_inoutline_id,
       m_inout_id,
       lotnumber,
       gtin_tu_packingmaterial,
       gtin_lu_packingmaterial,
       (select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_LU_ID) as M_HU_PackagingCode_LU_Text,
       (select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_TU_ID) as M_HU_PackagingCode_TU_Text
from edi_desadvline_pack
;
