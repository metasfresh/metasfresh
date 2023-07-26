
DROP FUNCTION IF EXISTS  "de.metas.async".DROP_C_Queue_PackageProcessor (IN C_Queue_PackageProcessor_ID numeric);
CREATE OR REPLACE FUNCTION "de.metas.async".DROP_C_Queue_PackageProcessor (IN C_Queue_PackageProcessor_ID numeric)
  RETURNS void 
AS


$BODY$
-- c_queue_element
begin
DELETE FROM c_queue_element qe
WHERE
	EXISTS ( SELECT 1
			FROM c_queue_workpackage qw
			JOIN c_queue_block qb  ON qb.c_queue_block_ID = qw.c_queue_block_ID
			WHERE qw.c_queue_workpackage_ID = qe.c_queue_workpackage_ID
				AND qb.C_Queue_PackageProcessor_ID=$1);


-- c_queue_workpackage

DELETE FROM c_queue_workpackage qw
WHERE 
	EXISTS ( SELECT 1 
			FROM  c_queue_block qb  
			WHERE qb.c_queue_block_ID = qw.c_queue_block_ID AND qb.C_Queue_PackageProcessor_ID=$1);


-- c_queue_block

DELETE FROM c_queue_block  qb WHERE qb.C_Queue_PackageProcessor_ID=$1;

-- c_queue_processor_assign

DELETE FROM c_queue_processor_assign qpa
WHERE qpa.C_Queue_PackageProcessor_ID=$1;


-- C_Queue_PackageProcessor

DELETE FROM C_Queue_PackageProcessor qpp WHERE qpp.C_Queue_PackageProcessor_ID=$1;

end;
$BODY$
LANGUAGE plpgsql;