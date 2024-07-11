UPDATE m_picking_job_step_hualternative step_alt
SET pickfrom_warehouse_id=job_alt.pickfrom_warehouse_id,
    pickfrom_locator_id=job_alt.pickfrom_locator_id,
    pickfrom_hu_id=job_alt.pickfrom_hu_id
FROM m_picking_job_hualternative job_alt
WHERE step_alt.m_picking_job_hualternative_id = job_alt.m_picking_job_hualternative_id
;


