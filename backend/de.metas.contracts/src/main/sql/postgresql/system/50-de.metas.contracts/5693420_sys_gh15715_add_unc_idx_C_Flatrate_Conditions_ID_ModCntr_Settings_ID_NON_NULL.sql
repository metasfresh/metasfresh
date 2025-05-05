drop index if exists C_Flatrate_Conditions_ID_ModCntr_Settings_ID_NON_NULL;
create unique index C_Flatrate_Conditions_ID_ModCntr_Settings_ID_NON_NULL on C_Flatrate_Conditions (C_Flatrate_Conditions_ID, ModCntr_Settings_ID)
    where ModCntr_Settings_ID IS NOT NULL and IsActive = 'Y';

