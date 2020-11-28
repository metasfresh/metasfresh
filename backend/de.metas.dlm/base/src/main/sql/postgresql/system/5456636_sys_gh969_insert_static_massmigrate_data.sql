
--
-- SQL to mass-archive particular years.
-- Creates one DLM_Partition and a number of dlm.massmigrate records for 2014, 2015 /*and 2016*/
--

--
-- 2014
--
-- hardcoded partition for 2014
INSERT INTO DLM_Partition(
  ad_client_id, -- numeric(10,0) NOT NULL,
  ad_org_id, -- numeric(10,0) NOT NULL,
  created, -- timestamp with time zone NOT NULL,
  createdby, -- numeric(10,0) NOT NULL,
  dlm_partition_id, -- numeric(10,0) NOT NULL,
  isactive, -- character(1) NOT NULL,
  updated, -- timestamp with time zone NOT NULL,
  updatedby, -- numeric(10,0) NOT NULL,
  dlm_partition_config_id, -- numeric(10,0) NOT NULL,
  current_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  target_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  datenextinspection, -- timestamp with time zone,
  partitionsize, -- numeric(10,0) DEFAULT NULL::numeric,
  ispartitioncomplete -- character(1) NOT NULL DEFAULT 'N'::bpchar,
)
VALUES (
  1000000, -- ad_client_id, -- numeric(10,0) NOT NULL,
  0, -- ad_org_id, -- numeric(10,0) NOT NULL,
  now(), -- created, -- timestamp with time zone NOT NULL,
  99, -- createdby, -- numeric(10,0) NOT NULL,
  10000, -- dlm_partition_id, -- numeric(10,0) NOT NULL,
  'Y', --isactive, -- character(1) NOT NULL,
  now(), --updated, -- timestamp with time zone NOT NULL,
  99, -- updatedby, -- numeric(10,0) NOT NULL,
  1000000, -- dlm_partition_config_id, the "standard" one
  0, --current_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  2, -- target_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  null, -- datenextinspection, -- timestamp with time zone,
  0, -- partitionsize, -- numeric(10,0) DEFAULT NULL::numeric,
  'Y' -- ispartitioncomplete -- we don't want the "normal" DLM system to try and fiddle with this partition
);

-- for 2014
INSERT INTO dlm.massmigrate (dlm_partition_config_id, dlm_partition_config_line_id, ad_table_id, dlm_partition_id, whereclause, status)
SELECT 
	l.DLM_Partition_Config_ID, 
	l.DLM_Partition_Config_Line_ID, 
	l.DLM_Referencing_Table_ID AS AD_Table_ID,
	10000 AS DLM_Partition_ID, /*"static" partition to represent the respective year*/
	CASE WHEN t.TableName='AD_PInstance_Log' /* AD_PInstanceLog has neither Updated nor Created and needs special threatment */
		THEN 'extract(''YEAR'' from (select pi.Updated from AD_PInstance pi where pi.AD_Pinstance_ID=AD_PInstance_Log.AD_Pinstance_ID))=2014'::character varying
		ELSE 'extract(''YEAR'' from Updated)=2014'::character varying
	END AS WhereClause,
	'pending'::character varying AS Status /*pending, load_rows, update_rows, done*/
FROM DLM_Partition_Config_Line l
	JOIN AD_Table t ON t.AD_Table_ID=l.DLM_Referencing_Table_ID
WHERE DLM_Partition_Config_ID=1000000 /*Standard*/
	AND NOT EXISTS (select 1 from dlm.massmigrate m where m.DLM_Partition_ID=10000 AND m.DLM_Partition_Config_Line_ID=l.DLM_Partition_Config_Line_ID);

--
-- 2015
--
-- hardcoded partition for 2015
INSERT INTO DLM_Partition(
  ad_client_id, -- numeric(10,0) NOT NULL,
  ad_org_id, -- numeric(10,0) NOT NULL,
  created, -- timestamp with time zone NOT NULL,
  createdby, -- numeric(10,0) NOT NULL,
  dlm_partition_id, -- numeric(10,0) NOT NULL,
  isactive, -- character(1) NOT NULL,
  updated, -- timestamp with time zone NOT NULL,
  updatedby, -- numeric(10,0) NOT NULL,
  dlm_partition_config_id, -- numeric(10,0) NOT NULL,
  current_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  target_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  datenextinspection, -- timestamp with time zone,
  partitionsize, -- numeric(10,0) DEFAULT NULL::numeric,
  ispartitioncomplete -- character(1) NOT NULL DEFAULT 'N'::bpchar,
)
VALUES (
  1000000, -- ad_client_id, -- numeric(10,0) NOT NULL,
  0, -- ad_org_id, -- numeric(10,0) NOT NULL,
  now(), -- created, -- timestamp with time zone NOT NULL,
  99, -- createdby, -- numeric(10,0) NOT NULL,
  10001, -- dlm_partition_id, -- numeric(10,0) NOT NULL,
  'Y', --isactive, -- character(1) NOT NULL,
  now(), --updated, -- timestamp with time zone NOT NULL,
  99, -- updatedby, -- numeric(10,0) NOT NULL,
  1000000, -- dlm_partition_config_id, the "standard" one
  0, --current_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  2, -- target_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  null, -- datenextinspection, -- timestamp with time zone,
  0, -- partitionsize, -- numeric(10,0) DEFAULT NULL::numeric,
  'Y' -- ispartitioncomplete -- we don't want the "normal" DLM system to try and fiddle with this partition
);
	
-- for 2015
INSERT INTO dlm.massmigrate (dlm_partition_config_id, dlm_partition_config_line_id, ad_table_id, dlm_partition_id, whereclause, status)
SELECT 
	l.DLM_Partition_Config_ID, 
	l.DLM_Partition_Config_Line_ID, 
	l.DLM_Referencing_Table_ID AS AD_Table_ID,
	10001 AS DLM_Partition_ID, /*"static" partition to represent the respective year*/
	CASE WHEN t.TableName='AD_PInstance_Log' /* AD_PInstanceLog has neither Updated nor Created and needs special threatment */
		THEN 'extract(''YEAR'' from (select pi.Updated from AD_PInstance pi where pi.AD_Pinstance_ID=AD_PInstance_Log.AD_Pinstance_ID))=2015'::character varying
		ELSE 'extract(''YEAR'' from Updated)=2015'::character varying
	END AS WhereClause,
	'pending'::character varying AS Status /*pending, load_rows, update_rows, done*/
FROM DLM_Partition_Config_Line l
	JOIN AD_Table t ON t.AD_Table_ID=l.DLM_Referencing_Table_ID
WHERE DLM_Partition_Config_ID=1000000 /*Standard*/
	AND NOT EXISTS (select 1 from dlm.massmigrate m where m.DLM_Partition_ID=10001 AND m.DLM_Partition_Config_Line_ID=l.DLM_Partition_Config_Line_ID);

/*
--
-- 2016
--
-- hardcoded partition for 2016
INSERT INTO DLM_Partition(
  ad_client_id, -- numeric(10,0) NOT NULL,
  ad_org_id, -- numeric(10,0) NOT NULL,
  created, -- timestamp with time zone NOT NULL,
  createdby, -- numeric(10,0) NOT NULL,
  dlm_partition_id, -- numeric(10,0) NOT NULL,
  isactive, -- character(1) NOT NULL,
  updated, -- timestamp with time zone NOT NULL,
  updatedby, -- numeric(10,0) NOT NULL,
  dlm_partition_config_id, -- numeric(10,0) NOT NULL,
  current_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  target_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  datenextinspection, -- timestamp with time zone,
  partitionsize, -- numeric(10,0) DEFAULT NULL::numeric,
  ispartitioncomplete -- character(1) NOT NULL DEFAULT 'N'::bpchar,
)
VALUES (
  1000000, -- ad_client_id, -- numeric(10,0) NOT NULL,
  0, -- ad_org_id, -- numeric(10,0) NOT NULL,
  now(), -- created, -- timestamp with time zone NOT NULL,
  99, -- createdby, -- numeric(10,0) NOT NULL,
  10002, -- dlm_partition_id, -- numeric(10,0) NOT NULL,
  'Y', --isactive, -- character(1) NOT NULL,
  now(), --updated, -- timestamp with time zone NOT NULL,
  99, -- updatedby, -- numeric(10,0) NOT NULL,
  1000000, -- dlm_partition_config_id, the "standard" one
  0, --current_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  2, -- target_dlm_level, -- numeric(10,0) DEFAULT NULL::numeric,
  null, -- datenextinspection, -- timestamp with time zone,
  0, -- partitionsize, -- numeric(10,0) DEFAULT NULL::numeric,
  'Y' -- ispartitioncomplete -- we don't want the "normal" DLM system to try and fiddle with this partition
);
	
-- for 2016
INSERT INTO dlm.massmigrate (dlm_partition_config_id, dlm_partition_config_line_id, ad_table_id, dlm_partition_id, whereclause, status)
SELECT 
	l.DLM_Partition_Config_ID, 
	l.DLM_Partition_Config_Line_ID, 
	l.DLM_Referencing_Table_ID AS AD_Table_ID,
	10002 AS DLM_Partition_ID, -- "static" partition to represent the respective year
	CASE WHEN t.TableName='AD_PInstance_Log' -- AD_PInstanceLog has neither Updated nor Created and needs special threatment
		THEN 'extract(''YEAR'' from (select pi.Updated from AD_PInstance pi where pi.AD_Pinstance_ID=AD_PInstance_Log.AD_Pinstance_ID))=2016'::character varying
		ELSE 'extract(''YEAR'' from Updated)=2016'::character varying
	END AS WhereClause,
	'pending'::character varying AS Status -- pending, load_rows, update_rows, done
FROM DLM_Partition_Config_Line l
	JOIN AD_Table t ON t.AD_Table_ID=l.DLM_Referencing_Table_ID
WHERE DLM_Partition_Config_ID=1000000 -- Standard 
	AND NOT EXISTS (select 1 from dlm.massmigrate m where m.DLM_Partition_ID=10002 AND m.DLM_Partition_Config_Line_ID=l.DLM_Partition_Config_Line_ID);
*/