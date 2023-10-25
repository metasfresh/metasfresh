CREATE UNIQUE INDEX modcntr_settings_product_year_calendar
    ON ModCntr_Settings (M_Product_ID, C_Year_ID, c_calendar_id)
;
