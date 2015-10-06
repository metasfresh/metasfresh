
-----

-- 21.08.2015 10:42
-- URL zum Konzept
UPDATE AD_Process SET Value='C_Order_OrderCheckup_Summary',Updated=TO_TIMESTAMP('2015-08-21 10:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540457
;

-- 21.08.2015 10:43
-- URL zum Konzept
UPDATE AD_Process SET Value='C_BPartner_OrderCheckup_Summary',Updated=TO_TIMESTAMP('2015-08-21 10:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540532
;

-- 21.08.2015 10:43
-- URL zum Konzept
UPDATE AD_Process SET Value='C_BPartner_OrderCheckup_Summary_Jasper',Updated=TO_TIMESTAMP('2015-08-21 10:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540532
;

-- 21.08.2015 10:43
-- URL zum Konzept
UPDATE AD_Process SET Value='C_Order_OrderCheckup_Summary_Jasper',Updated=TO_TIMESTAMP('2015-08-21 10:43:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540457
;

-- 21.08.2015 10:47
-- URL zum Konzept
UPDATE AD_Process SET Value='C_Order_OrderCheckup_Warehouse_Jasper',Updated=TO_TIMESTAMP('2015-08-21 10:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540601
;

----

--
-- creating a legacy/fallback report to preserve the old behavior
--
-- 24.08.2015 09:26
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540602,'org.compiere.report.ReportStarter','N',TO_TIMESTAMP('2015-08-24 09:26:50','YYYY-MM-DD HH24:MI:SS'),100,'Druckt den Zusatz zur Auftragsbestätigung, ohne das betreffende Druckstück einer bestimmten Person zuzuordnen. Dadurch wird das Druckstück letztlich der Person zugeordnet, die diesen Prozess ausführt.','de.metas.fresh','Y','N','N','N','Y','N','@PREFIX@de/metas/docs/sales/ordercheckup/report.jasper',0,'Bestellkontrolle Drucken','N','Y',0,0,'Java',TO_TIMESTAMP('2015-08-24 09:26:50','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_OrderCheckup_Jasper_NoRouting')
;
-- 24.08.2015 09:26
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540602 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

--------
-- 24.08.2015 10:00
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540871,'S',TO_TIMESTAMP('2015-08-24 10:00:23','YYYY-MM-DD HH24:MI:SS'),100,'If set to Y then for a completed sales order there will be a process available to create order checkup reports for the persons that are in charge of producing the products from the order lines (task 09028).','de.metas.fresh','Y','de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.EnableProcessGear',TO_TIMESTAMP('2015-08-24 10:00:23','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 24.08.2015 10:01
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540872,'S',TO_TIMESTAMP('2015-08-24 10:01:03','YYYY-MM-DD HH24:MI:SS'),100,'If set to Y and a sales order is completed, then the system will create order checkup reports for the persons that are in charge of producing the products from the order lines (task 09028).','de.metas.fresh','Y','de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete',TO_TIMESTAMP('2015-08-24 10:01:03','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 24.08.2015 10:12
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540603,'de.metas.fresh.ordercheckup.process.C_Order_OrderCheckup_CreateJasperReports','N',TO_TIMESTAMP('2015-08-24 10:12:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','N','N','N','N',0,'Bestellkontrollen für Produktionsverantworktliche erstellen','N','Y',0,0,'Java',TO_TIMESTAMP('2015-08-24 10:12:55','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_OrderCheckup_CreateJasperReports')
;

-- 24.08.2015 10:12
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540603 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 24.08.2015 10:13
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540603,259,TO_TIMESTAMP('2015-08-24 10:13:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y',TO_TIMESTAMP('2015-08-24 10:13:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.08.2015 10:13
-- URL zum Konzept
UPDATE AD_Process SET Name='Bestellkontrolle für Kunde Drucken',Updated=TO_TIMESTAMP('2015-08-24 10:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540532
;

-- 24.08.2015 10:13
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540532
;

-- 24.08.2015 10:13
-- URL zum Konzept
UPDATE AD_Menu SET Description='Druckt den Zusatz zur Auftragsbestätigung', IsActive='Y', Name='Bestellkontrolle für Kunde Drucken',Updated=TO_TIMESTAMP('2015-08-24 10:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540583
;

-- 24.08.2015 10:13
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540583
;

-- 24.08.2015 10:13
-- URL zum Konzept
UPDATE AD_Process SET Name='Bestellkontrolle zum Kunden Drucken',Updated=TO_TIMESTAMP('2015-08-24 10:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540532
;

-- 24.08.2015 10:13
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540532
;

-- 24.08.2015 10:13
-- URL zum Konzept
UPDATE AD_Menu SET Description='Druckt den Zusatz zur Auftragsbestätigung', IsActive='Y', Name='Bestellkontrolle zum Kunden Drucken',Updated=TO_TIMESTAMP('2015-08-24 10:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540583
;

-- 24.08.2015 10:13
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540583
;

-- 24.08.2015 10:14
-- URL zum Konzept
UPDATE AD_Process SET Name='Bestellkontrolle zum Auftrag Drucken',Updated=TO_TIMESTAMP('2015-08-24 10:14:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540457
;

-- 24.08.2015 10:14
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540457
;

-- 24.08.2015 10:14
-- URL zum Konzept
UPDATE AD_Process SET Name='Bestellkontrolle zu Auftrag und Lager Drucken',Updated=TO_TIMESTAMP('2015-08-24 10:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540601
;

-- 24.08.2015 10:14
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540601
;

-- 24.08.2015 10:14
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Bestellkontrolle zu Auftrag und Lager Drucken',Updated=TO_TIMESTAMP('2015-08-24 10:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540643
;

-- 24.08.2015 10:14
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540643
;

-- 24.08.2015 10:14
-- URL zum Konzept
UPDATE AD_Process SET Name='Bestellkontrolle zum Drucken',Updated=TO_TIMESTAMP('2015-08-24 10:14:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540602
;

-- 24.08.2015 10:14
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540602
;

-- 24.08.2015 10:15
-- URL zum Konzept
UPDATE AD_Process SET Name='Bestellkontrolle zum Auftrag eigener Ausdruck',Updated=TO_TIMESTAMP('2015-08-24 10:15:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540602
;

-- 24.08.2015 10:15
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540602
;

-- 24.08.2015 10:16
-- URL zum Konzept
UPDATE AD_Process SET Name='Bestellkontrolle zum Auftrag für Produktionsverantwortliche',Updated=TO_TIMESTAMP('2015-08-24 10:16:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540603
;

-- 24.08.2015 10:16
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540603
;

-- 24.08.2015 10:17
-- URL zum Konzept
UPDATE AD_Process SET Help='This jasper process is not directly accessible, but invoked by C_Order_OrderCheckup_CreateJasperReports (task 09028)',Updated=TO_TIMESTAMP('2015-08-24 10:17:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540457
;

-- 24.08.2015 10:17
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540457
;

-- 24.08.2015 10:17
-- URL zum Konzept
UPDATE AD_Process SET Help='This jasper process is not directly accessible, but invoked by C_Order_OrderCheckup_CreateJasperReports (task 09028)',Updated=TO_TIMESTAMP('2015-08-24 10:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540601
;

-- 24.08.2015 10:17
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540601
;

-- 24.08.2015 10:17
-- URL zum Konzept
DELETE FROM AD_Table_Process WHERE AD_Process_ID=540457 AND AD_Table_ID=259
;

-- 24.08.2015 10:18
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540602,259,TO_TIMESTAMP('2015-08-24 10:18:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y',TO_TIMESTAMP('2015-08-24 10:18:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.08.2015 10:20
-- URL zum Konzept
UPDATE AD_Process SET Description='Erstellt zu den Auftragspositionen Bestellkontroll-Berichte für die jeweiligen Produktionsverantwortlichen', Help='Die Produktionsverantwortlichen werden über Produkt-Plandaten und deren Lager ermittelt.',Updated=TO_TIMESTAMP('2015-08-24 10:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540603
;

-- 24.08.2015 10:20
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540603
;

-- 24.08.2015 10:20
-- URL zum Konzept
UPDATE AD_Process SET Description=NULL,Updated=TO_TIMESTAMP('2015-08-24 10:20:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540532
;

-- 24.08.2015 10:20
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540532
;

-- 24.08.2015 10:20
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Bestellkontrolle zum Kunden Drucken',Updated=TO_TIMESTAMP('2015-08-24 10:20:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540583
;

-- 24.08.2015 10:20
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540583
;

-- 24.08.2015 10:22
-- URL zum Konzept
UPDATE AD_Process SET Help='Die Produktionsverantwortlichen werden über Produkt-Plandaten und deren Lager ermittelt.<p>
Hinweis: Über System-Konfigurator "de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete" kann festgelegt werden, dass diese Bestellkontrolle automatisch beim fertigstellen eines Auftrags erstellt werden.',Updated=TO_TIMESTAMP('2015-08-24 10:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540603
;

-- 24.08.2015 10:22
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540603
;

-- 24.08.2015 10:23
-- URL zum Konzept
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2015-08-24 10:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540872
;

-- 24.08.2015 10:23
-- URL zum Konzept
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2015-08-24 10:23:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540871
;

