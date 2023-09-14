DROP INDEX IF EXISTS modcntr_settings_product_year_calendar
;

DROP INDEX IF EXISTS modcntr_settings_calender_year_product_unique_idx
;

CREATE UNIQUE INDEX modcntr_settings_calender_year_product_issotrx_unique_idx
    ON modcntr_settings (ad_org_id, c_calendar_id, c_year_id, m_product_id, issotrx)
;

