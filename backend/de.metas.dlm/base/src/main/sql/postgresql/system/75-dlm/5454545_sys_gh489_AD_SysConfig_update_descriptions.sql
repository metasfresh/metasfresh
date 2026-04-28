

UPDATE AD_SysConfig SET Description='The archive level on which the application shall operate. For DLM''ed tables, a select * from <table> will only return records with an archive-level that is less or equal to this value.
The archive levels currently in use are:
* 0: unspecified (equivalent to NULL)
* 1: operational data
* 2: operational data; this level is used by the partitioner. The partitioner temporarily updates records DLM_Level to 2 to find out if it already has found a complete partition
* 3: archived data'
WHERE Name='de.metas.dlm.DLM_Level';

UPDATE AD_SysConfig SET Description='Archive level to assume for records that were not yet explicitly assigned to a particular level:
* set it to 2 if you initially want to see everything as operational and then want the DLM system to decrease that number by moving older records to level 3
* set it to 3 if you initially want to see everything as archived and want the DLM system explicitly "pull" new records back into the operational realm.'
WHERE Name='de.metas.dlm.DLM_Coalesce_Level';
