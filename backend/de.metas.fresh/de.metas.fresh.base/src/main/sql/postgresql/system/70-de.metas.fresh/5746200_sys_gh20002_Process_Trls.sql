-- Run mode: SWING_CLIENT

-- Value: C_BPartner_OrderCheckup_Summary_Jasper
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/sales/ordercheckup/legacy/report.jasper
-- 2025-02-10T13:55:25.381Z
UPDATE AD_Process SET Description='Achtung: Wenn dieser Prozess aus der Einzelansicht des Auftragsfensters gestartet wird, dann wird die Bestellkontrolle für den Geschäftspartner und den zugesagten Termin aus genau diesem Auftrag erstellt!',Updated=TO_TIMESTAMP('2025-02-10 13:55:25.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540532
;

-- 2025-02-10T13:55:25.395Z
UPDATE AD_Process_Trl trl SET Description='Achtung: Wenn dieser Prozess aus der Einzelansicht des Auftragsfensters gestartet wird, dann wird die Bestellkontrolle für den Geschäftspartner und den zugesagten Termin aus genau diesem Auftrag erstellt!' WHERE AD_Process_ID=540532 AND AD_Language='de_DE'
;

-- Name: Bestellkontrolle zum Kunden Drucken
-- Action Type: R
-- Report: C_BPartner_OrderCheckup_Summary_Jasper(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-02-10T13:55:25.453Z
UPDATE AD_Menu SET Description='Achtung: Wenn dieser Prozess aus der Einzelansicht des Auftragsfensters gestartet wird, dann wird die Bestellkontrolle für den Geschäftspartner und den zugesagten Termin aus genau diesem Auftrag erstellt!', IsActive='Y', Name='Bestellkontrolle zum Kunden Drucken',Updated=TO_TIMESTAMP('2025-02-10 13:55:25.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540583
;

-- 2025-02-10T13:55:25.463Z
UPDATE AD_Menu_Trl trl SET Description='Achtung: Wenn dieser Prozess aus der Einzelansicht des Auftragsfensters gestartet wird, dann wird die Bestellkontrolle für den Geschäftspartner und den zugesagten Termin aus genau diesem Auftrag erstellt!' WHERE AD_Menu_ID=540583 AND AD_Language='de_DE'
;

-- Process: C_BPartner_OrderCheckup_Summary_Jasper(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-02-10T13:55:39.370Z
UPDATE AD_Process_Trl SET Description='Achtung: Wenn dieser Prozess aus der Einzelansicht des Auftragsfensters gestartet wird, dann wird die Bestellkontrolle für den Geschäftspartner und den zugesagten Termin aus genau diesem Auftrag erstellt!',Updated=TO_TIMESTAMP('2025-02-10 13:55:39.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540532
;

-- 2025-02-10T13:55:39.371Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_BPartner_OrderCheckup_Summary_Jasper(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-02-10T13:56:36.290Z
UPDATE AD_Process_Trl SET Description='Attention: If this process is started from the individual view of the order window, the order control for the business partner and the confirmed date is created from this very order!',Updated=TO_TIMESTAMP('2025-02-10 13:56:36.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=540532
;

-- 2025-02-10T13:56:36.293Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_BPartner_OrderCheckup_Summary_Jasper(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-02-10T13:56:39.947Z
UPDATE AD_Process_Trl SET Description='Attention: If this process is started from the individual view of the order window, the order control for the business partner and the confirmed date is created from this very order!',Updated=TO_TIMESTAMP('2025-02-10 13:56:39.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Process_ID=540532
;

-- 2025-02-10T13:56:39.949Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

