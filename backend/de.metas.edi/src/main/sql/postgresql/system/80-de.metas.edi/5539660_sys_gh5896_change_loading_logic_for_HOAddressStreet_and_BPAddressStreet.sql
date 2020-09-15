drop view if exists edi_desadvpack_sscc_label;

CREATE OR REPLACE VIEW edi_desadvpack_sscc_label
            (no_of_labels, SSCC, order_reference, date_shipped, GTIN, product_description, amount, weight, best_before, lot_no,
             bp_address_GLN, bp_address_name1, bp_address_name2, bp_address_street, bp_address_zip_code, bp_address_city,
             ho_address_GLN, ho_address_name1, ho_address_name2, ho_address_street, ho_address_zip_code, ho_address_city,
             p_instance_id)
AS
SELECT
    1                                                                           AS no_of_labels,
    dl_pack.ipa_sscc18                                                          AS sscc,
    dh.poreference                                                              AS order_reference,
    dh.movementdate                                                             AS date_shipped,
    dl.GTIN                                                                     AS GTIN,
    dl.productDescription                                                       AS product_description,
    dl_pack.movementqty                                                         AS amount,
    null::numeric                                                               AS weight,
    dl_pack.bestBeforeDate                                                      AS best_before,
    dl_pack.lotnumber                                                           AS lot_no,
    bp_address.GLN                                                              AS bp_address_gln,
    bp_address_location.address1                                                AS bp_address_name1,
    bp_address_location.address2                                                AS bp_address_name2,
    bp_address_location.address2                                                AS bp_address_street,
    bp_address_location.postal                                                  AS bp_address_zip_code,
    bp_address_location.city                                                    AS bp_address_city,
    ho_address.GLN                                                              AS ho_address_gln,
    ho_address_location.address1                                                AS ho_address_name1,
    ho_address_location.address2                                                AS ho_address_name2,
    ho_address_location.address2                                                AS ho_address_street,
    ho_address_location.postal                                                  AS ho_address_zip_code,
    ho_address_location.city                                                    AS ho_address_city,
    t_sel.ad_pinstance_id                                                       AS p_instance_id
from t_selection t_sel
         inner join edi_desadvline_pack dl_pack on t_sel.t_selection_id = dl_pack.edi_desadvline_pack_id
         inner join edi_desadvline dl on dl_pack.edi_desadvline_id = dl.edi_desadvline_id
         inner join edi_desadv dh on dl_pack.edi_desadv_id = dh.edi_desadv_id
         left join c_bpartner_location bp_address on dh.c_bpartner_location_id = bp_address.c_bpartner_location_id
         left join c_location bp_address_location on bp_address.c_location_id = bp_address_location.c_location_id
         left join c_bpartner_location ho_address on dh.handover_location_id = ho_address.c_bpartner_location_id
         left join c_location ho_address_location on ho_address.c_location_id = ho_address_location.c_location_id;
