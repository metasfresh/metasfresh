DROP VIEW IF EXISTS "de.metas.async".C_Queue_Element_Preceeding_Unprocessed_V;
CREATE OR REPLACE VIEW "de.metas.async".C_Queue_Element_Preceeding_Unprocessed_V AS
SELECT 
	e.C_Queue_Element_ID, 
	MIN(e2.C_Queue_Workpackage_ID) as C_Queue_Workpackage_Preceeding_ID -- selectonly the oldest one

FROM C_Queue_Element e
	JOIN C_Queue_Workpackage wp1 ON wp1.C_Queue_Workpackage_ID=e.C_Queue_Workpackage_ID
    -- Same package processor; note that we don't even exclude wp1 = wp2
	JOIN C_Queue_Workpackage wp2 ON wp2.C_Queue_PackageProcessor_ID=wp1.C_Queue_PackageProcessor_ID

	JOIN C_Queue_Element e2 ON e2.C_Queue_Workpackage_ID=wp2.C_Queue_Workpackage_ID
		-- Same record
		AND e2.AD_Table_ID=e.AD_Table_ID AND e2.Record_ID=e.Record_ID
		-- Older workpackage
		AND e2.C_Queue_Workpackage_ID < e.C_Queue_Workpackage_ID

WHERE true
	-- All other records active
	AND wp2.IsActive='Y'
	AND e2.IsActive='Y'
	-- And those older workpackages were not processed yet and didn't have an error yet!
	AND wp2.Processed='N'
	AND wp2.IsError='N'
GROUP BY e.C_Queue_Element_ID;


