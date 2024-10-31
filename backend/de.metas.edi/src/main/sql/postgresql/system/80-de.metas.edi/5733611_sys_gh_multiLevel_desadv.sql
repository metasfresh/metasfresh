
drop view if exists M_InOut_DesadvLine_V
;

DROP VIEW IF EXISTS edi_desadvpack_sscc_label;

SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine RENAME COLUMN GTIN TO GTIN_CU')
;


create or replace view M_InOut_DesadvLine_V as
select shipment.m_inout_id                                                       as M_InOut_Desadv_ID,
       case
           when desadvInOutLine.edi_desadvline_id > 0 then
               desadvInOutLine.edi_desadvline_id || '-' || desadvInOutLine.m_inoutline_id
                                                      else
               uuid_generate_v4() || '' -- using a random string for those desadv lines that are not in the current shipment, as we don't want any packs to match it
       end                                                                        as M_InOut_DesadvLine_V_ID,

       shipmentLine.m_inout_id,
       shipmentLine.m_inoutline_id,

       desadv.edi_desadv_id,
       desadv.AD_Client_ID,
       desadv.ad_org_id,
       coalesce(desadvInOutLine.updated, dline.updated)                           as updated,
       coalesce(desadvInOutLine.updatedby, dline.updatedby)                       as updatedby,
       coalesce(desadvInOutLine.created, dline.created)                           as created,
       coalesce(desadvInOutLine.createdby, dline.createdby)                       as createdby,

       coalesce(desadvInOutLine.edi_desadvline_id, dline.edi_desadvline_id)       as edi_desadvline_id,
       coalesce(desadvInOutLine.M_Product_ID, dline.M_Product_ID)                 as M_Product_ID,

       coalesce(desadvInOutLine.qtydeliveredinstockinguom, 0)                     as qtydeliveredinstockinguom,
       coalesce(desadvInOutLine.C_UOM_ID, dline.C_UOM_ID)                         as C_UOM_ID,
       coalesce(desadvInOutLine.QtyDeliveredInUOM, 0)                             as QtyDeliveredInUOM,
       coalesce(desadvInOutLine.C_UOM_Invoice_ID, dline.C_UOM_Invoice_ID)         as C_UOM_Invoice_ID,
       coalesce(desadvInOutLine.QtyDeliveredInInvoiceUOM, 0)                      as QtyDeliveredInInvoiceUOM,
       coalesce(desadvInOutLine.C_UOM_BPartner_ID, dline.C_UOM_BPartner_ID)       as C_UOM_BPartner_ID,
       coalesce(desadvInOutLine.QtyEnteredInBPartnerUOM, 0)                       as QtyEnteredInBPartnerUOM,

       dline.isactive,
       dline.Line,
       dline.ProductDescription,
       dline.ProductNo,
       dline.QtyEntered,
       dline.IsSubsequentDeliveryPlanned,
       dline.EAN_CU,
       dline.EAN_TU,
       dline.GTIN_CU,
       dline.GTIN_TU,
       dline.UPC_TU,
       dline.upc_cu,
       dline.PriceActual,
       dline.QtyOrdered,
       dline.QtyOrdered_Override,
       (select x12de355 from C_UOM where C_UOM.C_UOM_ID = dline.C_UOM_Invoice_ID) as EanCom_Invoice_UOM,
       dline.InvoicableQtyBasedOn,
       dline.OrderPOReference,
       dline.QtyItemCapacity,
       dline.OrderLine,
       dline.ExternalSeqNo,
       dline.BPartner_QtyItemCapacity,
       case when desadvInOutLine.DesadvLineTotalQtyDelivered >= coalesce(dline.QtyOrdered_Override, dline.QtyOrdered) then 'Y' else 'N' end as IsDeliveryClosed

from edi_desadv desadv
         inner join edi_desadvline dline on desadv.edi_desadv_id = dline.edi_desadv_id
         left join m_inout shipment on desadv.edi_desadv_id = shipment.edi_desadv_id
         left join m_inoutline shipmentLine on shipmentLine.m_inout_id = shipment.m_inout_id
    and shipmentLine.edi_desadvline_id = dline.edi_desadvline_id
         left join EDI_DesadvLine_InOutLine desadvInOutLine on shipmentLine.m_inoutline_id = desadvInOutLine.m_inoutline_id
where coalesce(dline.QtyOrdered_Override, dline.QtyOrdered) > dline.qtydeliveredinstockinguom or dline.edi_desadvline_id = shipmentLine.edi_desadvline_id
;


create or replace view edi_desadvpack_sscc_label
            (no_of_labels, sscc, order_reference, date_shipped, gtin, product_description, amount, amount_lu, weight,
             netweight, best_before, lot_no, bp_name, bp_address_gln, bp_address_name1, bp_address_name2,
             bp_address_street, bp_address_zip_code, bp_address_city, bp_address_country, ho_name, ho_address_gln,
             ho_address_name1, ho_address_name2, ho_address_street, ho_address_zip_code, ho_address_city, cnt_packs,
             cnt_in_packs, p_instance_id)
as
SELECT 1                                 AS no_of_labels,
       agg_pack.ipa_sscc18               AS sscc,
       dh.poreference                    AS order_reference,
       dh.movementdate                   AS date_shipped,
       agg_pack.GTIN_CU,
       agg_pack.product_description      AS product_description,
       agg_pack.amount                   AS amount,
       agg_pack.amount_lu                AS amount_lu,
       p.weight as netweight,
       p.grossweight as weight,
       agg_pack.best_before              AS best_before,
       agg_pack.lot_no                   AS lot_no,
       COALESCE(bp.companyname, bp.name) AS bp_name,
       bp_address.gln                    AS bp_address_gln,
       bp_address_location.address1      AS bp_address_name1,
       bp_address_location.address2      AS bp_address_name2,
       bp_address_location.address2      AS bp_address_street,
       bp_address_location.postal        AS bp_address_zip_code,
       bp_address_location.city          AS bp_address_city,
       bp_address_country.name           AS bp_address_country,
       COALESCE(ho.companyname, ho.name) AS ho_name,
       ho_address.gln                    AS ho_address_gln,
       ho_address_location.address1      AS ho_address_name1,
       ho_address_location.address2      AS ho_address_name2,
       ho_address_location.address2      AS ho_address_street,
       ho_address_location.postal        AS ho_address_zip_code,
       ho_address_location.city          AS ho_address_city,
       agg_pack.cnt_packs                AS cnt_packs,
       agg_pack.cnt_in_packs             AS cnt_in_packs,
       agg_pack.p_instance_id            AS p_instance_id
FROM (select dl_pack.edi_desadv_pack_id                       as pack_id,
             dl_pack.ipa_sscc18,
             dl_pack.edi_desadv_id,
             dl.m_product_id                                  as m_product_id,
             dl.GTIN_CU                                       AS GTIN_CU,
             dl.productdescription                            AS product_description,
             t_sel.ad_pinstance_id                            AS p_instance_id,
             (SELECT count(1) AS count
              FROM edi_desadv_pack pk
              WHERE pk.edi_desadv_id = dl_pack.edi_desadv_id) AS cnt_packs,
             dl_pack.edi_desadv_pack_id - ((SELECT min(pk.edi_desadvline_pack_id) AS min
                                            FROM edi_desadvline_pack pk
                                            WHERE pk.edi_desadv_id = dl_pack.edi_desadv_id)) +
             1::numeric                                       AS cnt_in_packs,
             min(dl_pack_item.bestbeforedate)                 AS best_before,
             STRING_AGG(distinct dl_pack_item.lotnumber, '')  AS lot_no,
             sum(dl_pack_item.qtytu)                          AS amount,
             sum(dl_pack_item.qtycusperlu)                    AS amount_lu
      FROM t_selection t_sel
               JOIN edi_desadv_pack dl_pack ON t_sel.t_selection_id = dl_pack.edi_desadv_pack_id
               JOIN edi_desadv_pack_item dl_pack_item ON dl_pack.edi_desadv_pack_id = dl_pack_item.edi_desadv_pack_id
               JOIN edi_desadvline dl ON dl_pack_item.edi_desadvline_id = dl.edi_desadvline_id
      group by dl_pack.edi_desadv_pack_id, dl.GTIN_CU, dl.productdescription, t_sel.ad_pinstance_id, dl.m_product_id) agg_pack
         inner JOIN edi_desadv dh ON dh.edi_desadv_id = agg_pack.edi_desadv_id
         LEFT JOIN c_bpartner_location bp_address ON dh.c_bpartner_location_id = bp_address.c_bpartner_location_id
         LEFT JOIN c_bpartner bp ON bp.c_bpartner_id = bp_address.c_bpartner_id
         LEFT JOIN c_location bp_address_location ON bp_address.c_location_id = bp_address_location.c_location_id
         LEFT JOIN c_country bp_address_country ON bp_address_location.c_country_id = bp_address_country.c_country_id
         LEFT JOIN c_bpartner_location ho_address ON dh.handover_location_id = ho_address.c_bpartner_location_id
         LEFT JOIN c_bpartner ho ON ho.c_bpartner_id = ho_address.c_bpartner_id
         LEFT JOIN c_location ho_address_location ON ho_address.c_location_id = ho_address_location.c_location_id
         LEFT JOIN m_product p ON p.m_product_id = agg_pack.m_product_id;
