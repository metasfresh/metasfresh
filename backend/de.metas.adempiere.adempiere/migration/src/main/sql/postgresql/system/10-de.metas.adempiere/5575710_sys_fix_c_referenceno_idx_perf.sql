
drop index uc_referenceno_and_type;
create unique index uc_referenceno_and_type
    on c_referenceno (referenceno,c_referenceno_type_id);
