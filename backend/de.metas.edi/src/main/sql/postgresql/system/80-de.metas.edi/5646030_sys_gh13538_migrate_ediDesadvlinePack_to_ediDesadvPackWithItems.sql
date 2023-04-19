
insert into edi_desadv_pack(edi_desadv_pack_id, ad_client_id, ad_org_id, created, createdby, isactive,
                            updated, updatedby, edi_desadv_id, ipa_sscc18, ismanual_ipa_sscc18, gtin_lu_packingmaterial,
                            m_hu_packagingcode_lu_id, m_hu_id, edi_desadv_parent_pack_id, edi_desadvline_pack_id)
select nextval('edi_desadv_pack_seq'),
       ad_client_id,
       ad_org_id,
       created,
       createdby,
       isactive,
       updated,
       updatedby,
       edi_desadv_id,
       ipa_sscc18,
       ismanual_ipa_sscc18,
       gtin_lu_packingmaterial,
       m_hu_packagingcode_lu_id,
       m_hu_id,
       null,
       edi_desadvline_pack_id
from edi_desadvline_pack
;

insert into edi_desadv_pack_item(edi_desadv_pack_item_id, ad_client_id, ad_org_id, bestbeforedate, created, createdby,
                                 edi_desadvline_id,
                                 isactive, lotnumber, m_inout_id, m_inoutline_id, movementqty, qtycu, qtycusperlu,
                                 qtyitemcapacity, qtytu,
                                 updated, updatedby, edi_desadv_pack_id, gtin_tu_packingmaterial,
                                 m_hu_packagingcode_tu_id)
select nextval('edi_desadv_pack_item_seq'),
       oldPackLine.ad_client_id,
       oldPackLine.ad_org_id,
       oldPackLine.bestbeforedate,
       oldPackLine.created,
       oldPackLine.createdby,
       oldPackLine.edi_desadvline_id,
       oldPackLine.isactive,
       oldPackLine.lotnumber,
       oldPackLine.m_inout_id,
       oldPackLine.m_inoutline_id,
       oldPackLine.movementqty,
       oldPackLine.qtycu,
       oldPackLine.qtycusperlu,
       oldPackLine.qtyitemcapacity,
       oldPackLine.qtytu,
       oldPackLine.updated,
       oldPackLine.updatedby,
       pack.edi_desadv_pack_id,
       oldPackLine.gtin_tu_packingmaterial,
       oldPackLine.m_hu_packagingcode_tu_id
from edi_desadvline_pack oldPackLine
         inner join edi_desadv_pack pack on oldPackLine.edi_desadvline_pack_id = pack.edi_desadvline_pack_id
;
