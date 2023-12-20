UPDATE externalsystem_service_instance
SET ExternalSystem_Service_ID=540010, updated='2022-11-24 16:37', updatedby=99
WHERE ExternalSystem_Service_ID=(SELECT ExternalSystem_Service_ID FROM ExternalSystem_Service WHERE value = 'defaultRestAPIMetasfresh')
;

UPDATE ExternalSystem_Service
SET ExternalSystem_Service_ID=540010, updated='2022-11-24 16:37', updatedby=99
WHERE value = 'defaultRestAPIMetasfresh'
;

