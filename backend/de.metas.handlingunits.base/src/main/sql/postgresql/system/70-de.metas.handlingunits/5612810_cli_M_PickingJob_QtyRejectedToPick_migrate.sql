UPDATE m_picking_job_step
SET qtyrejectedtopick=qtytopick - qtypicked
WHERE rejectreason IS NOT NULL
;

