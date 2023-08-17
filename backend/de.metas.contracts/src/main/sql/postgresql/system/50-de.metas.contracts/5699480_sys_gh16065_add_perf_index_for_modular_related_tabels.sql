CREATE INDEX idx_ModCntr_Module_ModCntr_Settings_ID ON ModCntr_Module (ModCntr_Settings_ID)
;

CREATE INDEX idx_C_Flatrate_Conditions_ModCntr_Settings_ID ON C_Flatrate_Conditions (ModCntr_Settings_ID)
;

CREATE INDEX idx_C_BPartner_IntrCtr_BP_ID_Harvesting_Calendar_And_Year ON C_BPartner_InterimContract (C_BPartner_ID, C_Harvesting_Calendar_ID, Harvesting_Year_ID)
;

CREATE INDEX idx_ModCntr_Log_Record_ID_AD_Table_ID ON ModCntr_Log (Record_ID, AD_Table_ID)
;
