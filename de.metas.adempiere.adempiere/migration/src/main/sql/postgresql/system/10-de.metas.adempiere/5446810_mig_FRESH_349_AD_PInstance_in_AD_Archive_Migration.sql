
DROP VIEW IF EXISTS tmp_RV_IO_TO_Archive;

CREATE OR REPLACE VIEW tmp_RV_IO_API_Archive
AS

SELECT 
		io.m_inout_id,
		min(a.AD_Archive_ID) as AD_ARchive_ID,
		min(api.AD_PInstance_ID) as AD_PInstance_ID
		
	FROM
		M_InOut io 
		JOIN AD_Archive a ON a.AD_Table_ID = get_table_id('M_InOut')
			AND a.Record_ID = io.m_inout_id
		JOIN AD_PInstance api ON api.AD_Table_ID = get_table_id('M_InOut')
			AND api.Record_ID = io.m_inout_id
		WHERE 
		a.AD_Process_ID is null
		AND api.result is not null
		-- suppose that the archive was created after the ad_PInstance was created
		AND a.created > api.created
	GROUP BY m_inout_id;

		
UPDATE AD_Archive a 
SET 

AD_Pinstance_ID = x.AD_Pinstance_ID

FROM	
	(
		SELECT *
		FROM tmp_RV_IO_API_Archive api
		
	)x
WHERE a.ad_archive_ID = x.ad_Archive_ID
;

DROP VIEW tmp_RV_IO_API_Archive;