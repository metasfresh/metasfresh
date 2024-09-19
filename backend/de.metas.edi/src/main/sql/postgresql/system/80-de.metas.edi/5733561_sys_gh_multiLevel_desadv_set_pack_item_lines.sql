
UPDATE edi_desadv_pack_item item
SET UpdatedBy=99, Updated='2024-09-17 15:09:00', Line=data.Line
FROM (SELECT pi.edi_desadv_pack_item_id,
             pi.edi_desadv_pack_id,
             p.edi_desadv_id,
             ROW_NUMBER() OVER (
                 PARTITION BY p.edi_desadv_id
                 ORDER BY pi.edi_desadv_pack_id, pi.edi_desadv_pack_item_id
                 ) * 10 AS Line
      FROM edi_desadv_pack_item pi
               JOIN edi_desadv_pack p
                    ON p.edi_desadv_pack_id = pi.edi_desadv_pack_id
      ORDER BY p.edi_desadv_id, pi.edi_desadv_pack_id, pi.edi_desadv_pack_item_id) data
WHERE item.edi_desadv_pack_item_id = data.edi_desadv_pack_item_id
;
