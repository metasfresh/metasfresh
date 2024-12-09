
drop view if exists M_InOut_DesadvLine_Pack_V;

SELECT public.db_alter_table('EDI_Desadv_Pack','ALTER TABLE public.EDI_Desadv_Pack RENAME COLUMN M_HU_PackagingCode_LU_ID TO M_HU_PackagingCode_ID')
;

SELECT public.db_alter_table('EDI_Desadv_Pack','ALTER TABLE public.EDI_Desadv_Pack RENAME COLUMN GTIN_LU_PackingMaterial TO GTIN_PackingMaterial')
;


create or replace view M_InOut_DesadvLine_Pack_V as
select line.edi_desadvline_id || '-' || m_inoutline_id as M_InOut_DesadvLine_V_ID,
       pack.ad_client_id,
       pack.ad_org_id,
       pack.created,
       pack.createdby,
       line.edi_desadvline_id,
       ipa_sscc18,
       pack.isactive,
       pack.updated,
       pack.updatedby,
       qtytu,
       QtyCUsPerTU as qtycu,
       qtycusperlu,
       m_hu_id,
       pack.edi_desadv_id,
       ismanual_ipa_sscc18,
       bestbeforedate,
       m_hu_packagingcode_id as m_hu_packagingcode_lu_id,
       m_hu_packagingcode_tu_id,
       c_uom_id,
       item.qtyitemcapacity,
       movementqty,
       m_inoutline_id,
       m_inout_id,
       lotnumber,
       gtin_tu_packingmaterial,
       gtin_packingmaterial as gtin_lu_packingmaterial,
       (select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_ID) as M_HU_PackagingCode_LU_Text,
       (select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_TU_ID) as M_HU_PackagingCode_TU_Text
from EDI_Desadv_Pack pack
         INNER JOIN EDI_Desadv_Pack_Item item on pack.edi_desadv_pack_id = item.edi_desadv_pack_id
         INNER JOIN EDI_DesadvLine line ON item.edi_desadvline_id = line.edi_desadvline_id
;
