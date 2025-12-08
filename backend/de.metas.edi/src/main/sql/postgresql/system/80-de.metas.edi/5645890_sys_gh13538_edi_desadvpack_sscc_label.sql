/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

DROP VIEW IF EXISTS edi_desadvpack_sscc_label;
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
       agg_pack.gtin,
       agg_pack.product_description      AS product_description,
       agg_pack.amount                   AS amount,
       agg_pack.amount_lu                AS amount_lu,
       p.weight,
       p.netweight,
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
             dl.gtin                                          as gtin,
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

      group by dl_pack.edi_desadv_pack_id, dl.gtin, dl.productdescription, t_sel.ad_pinstance_id, dl.m_product_id) agg_pack
         inner JOIN edi_desadv dh ON dh.edi_desadv_id = agg_pack.edi_desadv_id
         LEFT JOIN c_bpartner_location bp_address ON dh.c_bpartner_location_id = bp_address.c_bpartner_location_id
         LEFT JOIN c_bpartner bp ON bp.c_bpartner_id = bp_address.c_bpartner_id
         LEFT JOIN c_location bp_address_location ON bp_address.c_location_id = bp_address_location.c_location_id
         LEFT JOIN c_country bp_address_country ON bp_address_location.c_country_id = bp_address_country.c_country_id
         LEFT JOIN c_bpartner_location ho_address ON dh.handover_location_id = ho_address.c_bpartner_location_id
         LEFT JOIN c_bpartner ho ON ho.c_bpartner_id = ho_address.c_bpartner_id
         LEFT JOIN c_location ho_address_location ON ho_address.c_location_id = ho_address_location.c_location_id
         LEFT JOIN m_product p ON p.m_product_id = agg_pack.m_product_id;

alter table edi_desadvpack_sscc_label
    owner to postgres;

