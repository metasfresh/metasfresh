DROP INDEX IF EXISTS m_picking_job_order_docstatus_uq
;

CREATE UNIQUE INDEX m_picking_job_order_docstatus_uq
    ON m_picking_job (c_order_id, docstatus)
    WHERE docstatus = 'DR'
;

