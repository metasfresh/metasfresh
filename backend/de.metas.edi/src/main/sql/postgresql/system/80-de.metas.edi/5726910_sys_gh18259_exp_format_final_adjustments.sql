-- 2024-06-20T09:47:07.194Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588622,0,TO_TIMESTAMP('2024-06-20 09:47:07.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540406,550698,'Y','N','N','IsDeliveryClosed',480,'E',TO_TIMESTAMP('2024-06-20 09:47:07.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsDeliveryClosed')
;

-- 2024-06-20T09:47:59.878Z
UPDATE EXP_Format SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-20 09:47:59.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540405
;

-- 2024-06-20T09:48:13.595Z
UPDATE EXP_Format SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-20 09:48:13.594000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540428
;

-- 2024-06-20T09:48:15.372Z
UPDATE EXP_Format SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-20 09:48:15.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540405
;

-- 2024-06-20T09:48:21.180Z
UPDATE EXP_Format SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-20 09:48:21.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540428
;

-- 2024-06-20T09:48:44.864Z
UPDATE EXP_Format SET Value='EDI_Exp_Desadv',Updated=TO_TIMESTAMP('2024-06-20 09:48:44.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540428
;

-- 2024-06-20T09:48:50.246Z
UPDATE EXP_Format SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-20 09:48:50.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540428
;

-- 2024-06-20T09:48:51.776Z
UPDATE EXP_Format SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-20 09:48:51.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540405
;

-- Value: EDI_Desadv_InOut_EnqueueForExport
-- Classname: de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport
-- 2024-06-20T10:40:20.208Z
UPDATE AD_Process SET Description='Übergibt alle Lieferavis-Datensätze, die noch nicht erfolgreich gesendet wurden, an die EDI-Sendewarteschlange ',Updated=TO_TIMESTAMP('2024-06-20 10:40:20.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585400
;

-- Process: EDI_Desadv_InOut_EnqueueForExport(de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport)
-- 2024-06-20T10:40:25.648Z
UPDATE AD_Process_Trl SET Description='Übergibt alle Lieferavis-Datensätze, die noch nicht erfolgreich gesendet wurden, an die EDI-Sendewarteschlange ',Updated=TO_TIMESTAMP('2024-06-20 10:40:25.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585400
;

-- Process: EDI_Desadv_InOut_EnqueueForExport(de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport)
-- 2024-06-20T10:40:27.288Z
UPDATE AD_Process_Trl SET Description='Übergibt alle Lieferavis-Datensätze, die noch nicht erfolgreich gesendet wurden, an die EDI-Sendewarteschlange ',Updated=TO_TIMESTAMP('2024-06-20 10:40:27.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585400
;

-- Process: EDI_Desadv_InOut_EnqueueForExport(de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport)
-- 2024-06-20T10:40:30.912Z
UPDATE AD_Process_Trl SET Description='Übergibt alle Lieferavis-Datensätze, die noch nicht erfolgreich gesendet wurden, an die EDI-Sendewarteschlange ',Updated=TO_TIMESTAMP('2024-06-20 10:40:30.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585400
;

-- Process: EDI_Desadv_InOut_EnqueueForExport(de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport)
-- 2024-06-20T10:40:33.174Z
UPDATE AD_Process_Trl SET Description='Übergibt alle Lieferavis-Datensätze, die noch nicht erfolgreich gesendet wurden, an die EDI-Sendewarteschlange ',Updated=TO_TIMESTAMP('2024-06-20 10:40:33.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585400
;

-- Process: EDI_Desadv_InOut_EnqueueForExport(de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport)
-- 2024-06-20T10:40:35.374Z
UPDATE AD_Process_Trl SET Description='Übergibt alle Lieferavis-Datensätze, die noch nicht erfolgreich gesendet wurden, an die EDI-Sendewarteschlange ',Updated=TO_TIMESTAMP('2024-06-20 10:40:35.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585400
;

-- Process: EDI_Desadv_InOut_EnqueueForExport(de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport)
-- 2024-06-20T10:40:40.111Z
UPDATE AD_Process_Trl SET Name='EDI DESADV Auswahl senden',Updated=TO_TIMESTAMP('2024-06-20 10:40:40.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585400
;

-- Process: EDI_Desadv_InOut_EnqueueForExport(de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport)
-- 2024-06-20T10:40:41.923Z
UPDATE AD_Process_Trl SET Name='EDI DESADV Auswahl senden',Updated=TO_TIMESTAMP('2024-06-20 10:40:41.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585400
;

-- Process: EDI_Desadv_InOut_EnqueueForExport(de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport)
-- 2024-06-20T10:40:44.531Z
UPDATE AD_Process_Trl SET Name='EDI DESADV Auswahl senden',Updated=TO_TIMESTAMP('2024-06-20 10:40:44.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585400
;

