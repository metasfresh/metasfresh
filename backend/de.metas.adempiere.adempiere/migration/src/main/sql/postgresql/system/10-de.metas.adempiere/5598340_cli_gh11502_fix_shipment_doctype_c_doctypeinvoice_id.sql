
-- sync the invoice-doctype-IDs from "order"-doctypes to their respective "shipment"-doctypes
UPDATE c_doctype dt
SET c_doctypeinvoice_id=data.dt_o_c_doctypeinvoice_id,
    updated='2021-07-14 07:29:22.315990',
    updatedby=99
FROM (
         SELECT dt_o.c_doctype_id         AS dt_o_c_doctype_id,
                dt_o.name                 AS dt_o_name,
                dt_o.docbasetype          AS dt_o_docbasetype,
                dt_o.c_doctypeinvoice_id  AS dt_o_c_doctypeinvoice_id,
                dt_io.c_doctype_id        AS dt_io_c_doctype_id,
                dt_io.name                AS dt_io_name,
                dt_io.c_doctypeinvoice_id AS dt_io_c_doctypeinvoice_id
         FROM c_doctype dt_o
                  JOIN c_doctype dt_io ON dt_io.c_doctype_id = dt_o.c_doctypeshipment_id
         WHERE dt_o.docbasetype = 'SOO'
           AND dt_o.c_doctypeinvoice_id IS NOT NULL
     ) data
WHERE dt.c_doctype_id = data.dt_io_c_doctype_id
  AND dt.c_doctypeinvoice_id IS NULL
;
