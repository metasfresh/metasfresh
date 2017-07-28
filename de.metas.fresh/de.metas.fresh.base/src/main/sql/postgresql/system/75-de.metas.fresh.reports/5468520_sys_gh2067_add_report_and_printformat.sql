-- 2017-07-24T11:20:31.669
-- URL zum Konzept
UPDATE AD_Process SET Description='Erstellt zu Auftragspositionen Bestellkontroll-Berichte (C_Order_MFGWarehouse_Report) für die jeweiligen Produktionsverantwortlichen sowie für die Spedition.
Zu diesen werden via "Ausgehende Belege" i.d.R. Druckstücke erzeugt.',Updated=TO_TIMESTAMP('2017-07-24 11:20:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540603
;

-- 2017-07-24T11:22:12.679
-- URL zum Konzept
UPDATE AD_Process SET Description='Erstellt zu Auftragspositionen Bestellkontroll-Berichte (C_Order_MFGWarehouse_Report) für die jeweiligen Produktionsverantwortlichen sowie für die Spedition.
Zu diesen Bericht-Datensätzen werden via "Ausgehende Belege Konfig" i.d.R. Druckstücke erzeugt.',Updated=TO_TIMESTAMP('2017-07-24 11:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540603
;

-- 2017-07-24T11:25:48.415
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540814,'N','org.compiere.report.ReportStarter','N',TO_TIMESTAMP('2017-07-24 11:25:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','N','N','N','Y','N','Y','@PREFIX@de/metas/docs/sales/ordercheckup_with_barcode/report.jasper',0,'Bestellkontrolle','N','N','Java',TO_TIMESTAMP('2017-07-24 11:25:43','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_MFGWarehouse_Report_With_Barcode')
;

-- 2017-07-24T11:25:48.418
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540814 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-07-24T11:29:23.760
-- URL zum Konzept
UPDATE AD_Process SET Name='Bestellkontrolle (Barcode)',Updated=TO_TIMESTAMP('2017-07-24 11:29:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540814
;

-- 2017-07-24T11:29:36.976
-- URL zum Konzept
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_PrintTableFormat_ID,AD_Table_ID,CreateCopy,Created,CreatedBy,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,JasperProcess_ID,Name,Updated,UpdatedBy) VALUES (0,0,100,540008,'SO',540068,102,100,540683,'N',TO_TIMESTAMP('2017-07-24 11:29:36','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Y','N','N','Y','Y',540814,'C_Order_MFGWarehouse_Report_Whith_Barcode',TO_TIMESTAMP('2017-07-24 11:29:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T11:40:49.092
-- URL zum Konzept
UPDATE AD_PrintFormat SET Name='C_Order_MFGWarehouse_Report_With_Barcode',Updated=TO_TIMESTAMP('2017-07-24 11:40:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540068
;

