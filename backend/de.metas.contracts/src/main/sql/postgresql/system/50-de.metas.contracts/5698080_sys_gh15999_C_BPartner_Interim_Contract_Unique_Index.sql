create unique index c_bpartner_interimcontract_c_bpartner_id_harvesting_year_id_uindex
    on c_bpartner_interimcontract (c_bpartner_id, harvesting_year_id)
    where IsActive = 'Y';
