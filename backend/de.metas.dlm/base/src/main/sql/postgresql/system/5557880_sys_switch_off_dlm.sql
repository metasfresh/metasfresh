
UPDATE AD_ModelValidator 
SET IsActive='N', 
	Updated='2020-04-24 11:12:39.650199+02', 
	UpdatedBy=99, 
	Description=COALESCE(Description,'') || '
Deactivated for now, until we revised the topic of "partition-boundaries". Until then we can''t make sure that E.g. a connected graph starting at one invoice contains only a limited subset of all invoices.' 
WHERE ModelVAlidationClass='de.metas.dlm.model.interceptor.Main';

UPDATE AD_Sysconfig 
SET Value='N', 
	Updated='2020-04-24 11:12:39.650199+02', 
	UpdatedBy=99, 
	Description=COALESCE(Description,'') || ' 
Deactivated for now, until we revised the topic of "partition-boundaries". Until then we can''t make sure that E.g. a connected graph starting at one invoice contains only a limited subset of all invoices.' 
WHERE Name='de.metas.dlm.PartitionerInterceptor.enabled';
