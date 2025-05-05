-- Run mode: SWING_CLIENT

-- Value: M_InOut_Generate (manual)
-- Classname: org.adempiere.inout.process.InOutGenerate
-- 2023-12-20T20:02:29.199Z
-- Reason: class not found
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=199
;

-- 2023-12-20T20:02:29.212Z
DELETE FROM AD_Process WHERE AD_Process_ID=199
;





-- Name: Rechnungen drucken
-- Action Type: P
-- Process: C_Invoice_Print(org.compiere.process.InvoicePrint)
-- 2023-12-20T20:03:39.503Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=347
;

-- 2023-12-20T20:03:39.508Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=347
;

-- 2023-12-20T20:03:39.511Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=347 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: C_Invoice_Print
-- Classname: org.compiere.process.InvoicePrint
-- 2023-12-20T20:03:42.825Z
-- reason: class was scheduled to be deleted (not even impl JavaProcess)
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=200
;

-- 2023-12-20T20:03:42.831Z
DELETE FROM AD_Process WHERE AD_Process_ID=200
;







-- Process: AD_Issue Report(org.compiere.process.IssueReport)
-- Table: AD_Issue
-- EntityType: D
-- 2023-12-20T20:05:13.428Z
-- reason: class not found
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540371
;

-- Value: AD_Issue Report
-- Classname: org.compiere.process.IssueReport
-- 2023-12-20T20:05:17.402Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=339
;

-- 2023-12-20T20:05:17.408Z
DELETE FROM AD_Process WHERE AD_Process_ID=339
;








-- Value: CM_Media_DirectDeploy
-- Classname: org.compiere.cm.MediaDirectDeploy
-- 2023-12-20T20:06:00.783Z
-- reason: invalid legacy javaprocess class
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=352
;

-- 2023-12-20T20:06:00.792Z
DELETE FROM AD_Process WHERE AD_Process_ID=352
;







-- Value: PackOut
-- Classname: org.adempiere.pipo.PackOut
-- 2023-12-20T20:06:40.472Z
-- reson: missing
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=50004
;

-- 2023-12-20T20:06:40.477Z
DELETE FROM AD_Process WHERE AD_Process_ID=50004
;






-- Value: File_Select
-- Classname: com.compiere.packbuilder.Select_File
-- 2023-12-20T20:07:19.396Z
-- reason: missing
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=50006
;

-- 2023-12-20T20:07:19.400Z
DELETE FROM AD_Process WHERE AD_Process_ID=50006
;










-- Value: HR_Payroll Concept
-- Classname: org.eevolution.process.HRCreateConcept
-- 2023-12-20T20:08:59.876Z
-- reason: legacy
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53078
;

-- 2023-12-20T20:08:59.884Z
DELETE FROM AD_Process WHERE AD_Process_ID=53078
;








-- Name: Setup Web POS
-- Action Type: P
-- Process: Setup Web POS(org.posterita.process.SetupWebPOS)
-- 2023-12-20T20:09:50.078Z
-- missing
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53013
;

-- 2023-12-20T20:09:50.082Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=53013
;

-- 2023-12-20T20:09:50.085Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53013 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: Setup Web POS
-- Classname: org.posterita.process.SetupWebPOS
-- 2023-12-20T20:09:53.463Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53002
;

-- 2023-12-20T20:09:53.471Z
DELETE FROM AD_Process WHERE AD_Process_ID=53002
;








-- Name: Gehaltsabrechnung Verarbeitung
-- Action Type: P
-- Process: HR_PayrollProcessing(org.eevolution.process.PayrollProcessing)
-- 2023-12-20T20:10:54.556Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53116
;

-- 2023-12-20T20:10:54.561Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=53116
;

-- 2023-12-20T20:10:54.575Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53116 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: HR_PayrollProcessing
-- Classname: org.eevolution.process.PayrollProcessing
-- 2023-12-20T20:10:57.743Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53082
;

-- 2023-12-20T20:10:57.748Z
DELETE FROM AD_Process WHERE AD_Process_ID=53082
;

-- Name: Versende Gehaltsabrechnung über Email
-- Action Type: P
-- Process: HR_Payroll Send EMail(org.eevolution.process.PayrollViaEMail)
-- 2023-12-20T20:11:24.250Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53122
;

-- 2023-12-20T20:11:24.256Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=53122
;

-- 2023-12-20T20:11:24.259Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53122 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: HR_Payroll Send EMail
-- Classname: org.eevolution.process.PayrollViaEMail
-- 2023-12-20T20:11:27.281Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53084
;

-- 2023-12-20T20:11:27.285Z
DELETE FROM AD_Process WHERE AD_Process_ID=53084
;

-- Name: Cost BOM Multi Level Review
-- Action Type: R
-- Report: PP_Cost BOM Multi Level Review(org.eevolution.report.CostBillOfMaterial)
-- 2023-12-20T20:12:26.683Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53198
;

-- 2023-12-20T20:12:26.688Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=53198
;

-- 2023-12-20T20:12:26.691Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53198 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Name: Frozen/UnFrozen Cost
-- Action Type: P
-- Process: PP_Cost Frozen/UnFrozen(org.eevolution.process.FrozenUnFrozenCost)
-- 2023-12-20T20:12:59.212Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53199
;

-- 2023-12-20T20:12:59.218Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=53199
;

-- 2023-12-20T20:12:59.220Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53199 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: PP_Cost Frozen/UnFrozen
-- Classname: org.eevolution.process.FrozenUnFrozenCost
-- 2023-12-20T20:13:01.825Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53160
;

-- 2023-12-20T20:13:01.831Z
DELETE FROM AD_Process WHERE AD_Process_ID=53160
;

-- Value: C_Recurring Auto
-- Classname: org.adempiere.document.process.RecurringAuto
-- 2023-12-20T20:13:21.291Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=504520
;

-- 2023-12-20T20:13:21.295Z
DELETE FROM AD_Process WHERE AD_Process_ID=504520
;

-- Value: PerformPackaging
-- Classname: de.metas.adempiere.process.PerformPackaging
-- 2023-12-20T20:13:41.578Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540006
;

-- 2023-12-20T20:13:41.583Z
DELETE FROM AD_Process WHERE AD_Process_ID=540006
;

-- Value: PackageLabel
-- Classname: de.metas.adempiere.process.PackageLabel
-- 2023-12-20T20:13:59.494Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540059
;

-- 2023-12-20T20:13:59.499Z
DELETE FROM AD_Process WHERE AD_Process_ID=540059
;

-- Value: C_Sponsor UpdateStats
-- Classname: de.metas.commission.process.UpdateSponsorStats
-- 2023-12-20T20:14:26.250Z
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 20:14:26.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540066
;

-- Name: Update Sponsor-Statistics
-- Action Type: P
-- Process: C_Sponsor UpdateStats(de.metas.commission.process.UpdateSponsorStats)
-- 2023-12-20T20:14:26.259Z
UPDATE AD_Menu SET Description=NULL, IsActive='N', Name='Update Sponsor-Statistics',Updated=TO_TIMESTAMP('2023-12-20 20:14:26.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540105
;

-- 2023-12-20T20:14:26.260Z
UPDATE AD_Menu_Trl trl SET Name='Update Sponsor-Statistics' WHERE AD_Menu_ID=540105 AND AD_Language='de_DE'
;

-- Value: Dump Hierarchiebaum
-- Classname: de.metas.commission.process.DumpTree
-- 2023-12-20T20:14:48.202Z
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 20:14:48.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540105
;

-- Name: Dump Hierarchiebaum
-- Action Type: P
-- Process: Dump Hierarchiebaum(de.metas.commission.process.DumpTree)
-- 2023-12-20T20:14:48.208Z
UPDATE AD_Menu SET Description='Schreibt den Hierarchiebaum (zu einem Stichdatum) zu Info-Zwecken in eine Datei', IsActive='N', Name='Dump Hierarchiebaum',Updated=TO_TIMESTAMP('2023-12-20 20:14:48.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540146
;

-- 2023-12-20T20:14:48.210Z
UPDATE AD_Menu_Trl trl SET Description='Schreibt den Hierarchiebaum (zu einem Stichdatum) zu Info-Zwecken in eine Datei' WHERE AD_Menu_ID=540146 AND AD_Language='de_DE'
;

-- Value: Dump Berechnungsrevision
-- Classname: de.metas.commission.process.DumpCommissionReviewData
-- 2023-12-20T20:15:06.515Z
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 20:15:06.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540142
;

-- Name: Dump Berechnungsrevision
-- Action Type: P
-- Process: Dump Berechnungsrevision(de.metas.commission.process.DumpCommissionReviewData)
-- 2023-12-20T20:15:06.521Z
UPDATE AD_Menu SET Description='Schreibt den Hierarchiebaum (zu einem Stichdatum) zu Info-Zwecken in eine Datei', IsActive='N', Name='Dump Berechnungsrevision',Updated=TO_TIMESTAMP('2023-12-20 20:15:06.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540192
;

-- 2023-12-20T20:15:06.523Z
UPDATE AD_Menu_Trl trl SET Description='Schreibt den Hierarchiebaum (zu einem Stichdatum) zu Info-Zwecken in eine Datei',Name='Dump Berechnungsrevision' WHERE AD_Menu_ID=540192 AND AD_Language='de_DE'
;

-- Value: C_Postal_DPD_Validate
-- Classname: de.metas.adempiere.process.C_Postal_DPD_Validate
-- 2023-12-20T20:16:03.086Z
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 20:16:03.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540209
;

-- Name: Postleitzahlen-Datensätze verifizieren (aus DPD-Daten)
-- Action Type: P
-- Process: C_Postal_DPD_Validate(de.metas.adempiere.process.C_Postal_DPD_Validate)
-- 2023-12-20T20:16:03.094Z
UPDATE AD_Menu SET Description='Prozess stellt fest, zu welchen der im System hinterlegten Postleitzahlen es entsprechende DPD-Routendaten gibt und markiert die PLZ-Datensätze entsprechend.', IsActive='N', Name='Postleitzahlen-Datensätze verifizieren (aus DPD-Daten)',Updated=TO_TIMESTAMP('2023-12-20 20:16:03.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540292
;

-- Value: PP_MRP_RecreateForDocument
-- Classname: org.eevolution.mrp.process.PP_MRP_RecreateForDocument
-- 2023-12-20T20:16:24.820Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540512
;

-- 2023-12-20T20:16:24.824Z
DELETE FROM AD_Process WHERE AD_Process_ID=540512
;

-- Value: AD_Replication Run
-- Classname: org.compiere.process.ReplicationLocal
-- 2023-12-20T20:22:24.600Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=208
;

-- 2023-12-20T20:22:24.608Z
DELETE FROM AD_Process WHERE AD_Process_ID=208
;

-- Value: HR_Payroll Period
-- Classname: org.eevolution.process.HRCreatePeriods
-- 2023-12-20T20:23:01.648Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53080
;

-- 2023-12-20T20:23:01.654Z
DELETE FROM AD_Process WHERE AD_Process_ID=53080
;

-- Value: PP_Cost BOM Multi Level Review
-- Classname: org.eevolution.report.CostBillOfMaterial
-- 2023-12-20T20:24:14.641Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53159
;

-- 2023-12-20T20:24:14.646Z
DELETE FROM AD_Process WHERE AD_Process_ID=53159
;








-- Name: Calculate Forecast
-- Action Type: P
-- Process: M_Forecast Calculate(@script:beanshell:)
-- 2023-12-20T20:39:12.205Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53182
;

-- 2023-12-20T20:39:12.209Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=53182
;

-- 2023-12-20T20:39:12.213Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53182 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: M_Forecast Calculate
-- Classname: @script:beanshell:
-- 2023-12-20T20:39:15.753Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53148
;

-- 2023-12-20T20:39:15.757Z
DELETE FROM AD_Process WHERE AD_Process_ID=53148
;

