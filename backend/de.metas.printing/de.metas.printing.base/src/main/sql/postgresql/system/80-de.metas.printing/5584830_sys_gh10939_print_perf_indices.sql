

create index if NOT EXISTS c_print_packageinfo_ad_printerhw_id
    on c_print_packageinfo (ad_printerhw_id);

create index if NOT EXISTS c_print_packageinfo_ad_printerhw_mediatray_id
    on c_print_packageinfo (ad_printerhw_mediatray_id);

create index if NOT EXISTS ad_printerhw_calibration_ad_printerhw_mediatray_id
    on ad_printerhw_calibration (ad_printerhw_mediatray_id);
