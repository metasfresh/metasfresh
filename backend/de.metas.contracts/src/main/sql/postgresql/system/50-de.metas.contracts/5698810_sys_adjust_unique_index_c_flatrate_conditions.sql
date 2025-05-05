DROP INDEX IF EXISTS c_flatrate_conditions_modcntr_settings_id_uindex
;

CREATE UNIQUE INDEX c_flatrate_conditions_modcntr_settings_id_uindex
    ON c_flatrate_conditions (modcntr_settings_id, type_conditions)
    WHERE ((isactive = 'Y'::bpchar) AND ((docstatus)::text = 'CO'::text)) AND (modcntr_settings_id IS NOT NULL)
;

