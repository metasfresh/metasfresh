-- 2019-10-11T15:36:28.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='', IsLazyLoading='N',Updated=TO_TIMESTAMP('2019-10-11 17:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551500
;

-- 2019-10-11T15:36:29.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log','ALTER TABLE public.C_Doc_Outbound_Log ADD COLUMN EDI_ExportStatus CHAR(1)')
;

-- 2019-10-11T15:37:32.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doc_outbound_log','EDI_ExportStatus','CHAR(1)',null,null)
;

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


-- 2019-10-11T15:39:00.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2019-10-11 17:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540244
;

-- 2019-10-11T15:39:17.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2019-10-11 17:39:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540573
;

-- 2019-10-11T15:39:20.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2019-10-11 17:39:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540574
;

