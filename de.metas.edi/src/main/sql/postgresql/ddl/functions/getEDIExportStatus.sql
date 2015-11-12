DROP FUNCTION "de.metas.edi".getediexportstatus(numeric, numeric);

CREATE OR REPLACE FUNCTION "de.metas.edi".getediexportstatus(ad_table_id numeric, record_id numeric)
  RETURNS character AS
$BODY$
	SELECT
		(CASE
			WHEN $1=318 AND (SELECT COUNT(0) FROM C_Invoice WHERE C_Invoice_ID=$2)=1 -- C_Invoice
				THEN (
					SELECT CASE WHEN i.IsEdiEnabled='Y' THEN i.EDI_ExportStatus ELSE NULL END
					FROM C_Invoice i WHERE i.C_Invoice_ID=$2)
/* task 08456: // currently, InOuts are aggregated into EDI_Desadv records and exported as such, from the desadv window. Therfore, we don't show thereir status					
			WHEN $1=319 AND (SELECT COUNT(0) FROM M_InOut WHERE M_InOut_ID=$2)=1 -- M_InOut
				THEN (
					SELECT CASE WHEN io.IsEdiEnabled='Y' THEN io.EDI_ExportStatus ELSE NULL END
					FROM M_InOut io WHERE io.M_InOut_ID=$2)
*/
			ELSE NULL
		END) EDI_ExportStatus

$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
COMMENT ON FUNCTION "de.metas.edi".getediexportstatus(numeric, numeric) IS 'fresh 08038: Get EDI_ExportStatus from C_Doc_Outbound_Log';
