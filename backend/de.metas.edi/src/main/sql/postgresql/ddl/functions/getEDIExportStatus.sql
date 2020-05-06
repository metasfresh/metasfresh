DROP FUNCTION "de.metas.edi".getediexportstatus(numeric, numeric);

CREATE OR REPLACE FUNCTION "de.metas.edi".getediexportstatus(p_ad_table_id numeric, p_record_id numeric)
  RETURNS character AS
$BODY$
	SELECT
		(CASE
			WHEN p_ad_table_id=318 AND (SELECT COUNT(0) FROM C_Invoice WHERE C_Invoice_ID=p_record_id)=1 -- C_Invoice
				THEN (
					SELECT CASE WHEN i.IsEdiEnabled='Y' THEN i.EDI_ExportStatus ELSE NULL END
					FROM C_Invoice i 
					WHERE i.C_Invoice_ID=p_record_id)
			ELSE NULL
		END) EDI_ExportStatus

$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
COMMENT ON FUNCTION "de.metas.edi".getediexportstatus(numeric, numeric) IS 'fresh 08038: Get EDI_ExportStatus from C_Doc_Outbound_Log';
