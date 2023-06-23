

-- Should not be needed since these tables only contain dummy data

CREATE TABLE backup.BKP_Contract_Module_Log_23062023 as select * from Contract_module_log;

CREATE TABLE backup.BKP_Contract_Module_Type_23062023 as select * from Contract_Module_Type;

CREATE TABLE backup.BKP_C_ModuleSettings_23062023 as select * from C_ModuleSettings;

CREATE TABLE backup.BKP_C_ModularContractSettings_23062023 as select * from C_ModularContractSettings;

CREATE TABLE backup.BKP_C_FlatrateConditions_23062023 as select * from c_flatrate_conditions;



DROP TABLE Contract_module_log;


DROP TABLE C_ModuleSettings;

DROP TABLE Contract_Module_Type;

ALTER TABLE c_flatrate_conditions drop column C_ModularContractSettings_ID;

DROP TABLE C_ModularContractSettings;
