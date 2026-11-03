-- Element: BLDate
-- 2025-10-29T07:55:38.558Z
UPDATE AD_Element_Trl SET Description='Bill of Lading Date', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-29 07:55:38.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='de_CH'
;

-- 2025-10-29T07:55:38.572Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T07:55:39.140Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'de_CH')
;

-- Element: BLDate
-- 2025-10-29T07:55:49.101Z
UPDATE AD_Element_Trl SET Description='Bill of Lading Date',Updated=TO_TIMESTAMP('2025-10-29 07:55:49.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='en_US'
;

-- 2025-10-29T07:55:49.103Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T07:55:49.596Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'en_US')
;

-- Element: BLDate
-- 2025-10-29T07:56:09.883Z
UPDATE AD_Element_Trl SET Help='Date when the Bill of Lading was issued by the carrier.',Updated=TO_TIMESTAMP('2025-10-29 07:56:09.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='en_US'
;

-- 2025-10-29T07:56:09.884Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T07:56:10.140Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'en_US')
;

-- Element: BLDate
-- 2025-10-29T07:56:36.257Z
UPDATE AD_Element_Trl SET Description='Verladedatum', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-29 07:56:36.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='de_DE'
;

-- 2025-10-29T07:56:36.258Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T07:56:37.854Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584075,'de_DE')
;

-- 2025-10-29T07:56:37.855Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'de_DE')
;

-- Element: BLDate
-- 2025-10-29T07:56:41.743Z
UPDATE AD_Element_Trl SET Description='Verladedatum',Updated=TO_TIMESTAMP('2025-10-29 07:56:41.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='de_CH'
;

-- 2025-10-29T07:56:41.744Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T07:56:41.990Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'de_CH')
;

-- Element: BLDate
-- 2025-10-29T07:56:52.924Z
UPDATE AD_Element_Trl SET Description='Datum, an dem der Frachtbrief vom Frachtführer ausgestellt wurde.',Updated=TO_TIMESTAMP('2025-10-29 07:56:52.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='de_CH'
;

-- 2025-10-29T07:56:52.926Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T07:56:53.192Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'de_CH')
;

-- Element: BLDate
-- 2025-10-29T07:56:57.001Z
UPDATE AD_Element_Trl SET Description='Datum, an dem der Frachtbrief vom Frachtführer ausgestellt wurde.',Updated=TO_TIMESTAMP('2025-10-29 07:56:57.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='de_DE'
;

-- 2025-10-29T07:56:57.002Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T07:56:57.587Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584075,'de_DE')
;

-- 2025-10-29T07:56:57.589Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'de_DE')
;

-- Element: BLDate
-- 2025-10-29T07:57:05.670Z
UPDATE AD_Element_Trl SET Description='Date when the Bill of Lading was issued by the carrier.', Help='',Updated=TO_TIMESTAMP('2025-10-29 07:57:05.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='en_US'
;

-- 2025-10-29T07:57:05.671Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T07:57:05.924Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'en_US')
;

-- Element: BLDate
-- 2025-10-29T08:01:21.323Z
UPDATE AD_Element_Trl SET Help='The Bill of Lading Date is automatically synchronized from the Transport Order to the Purchase Order once the transport is completed.
Manual changes of the BL Date in the Purchase Order are not allowed — it is always derived from the corresponding Transport Order.',Updated=TO_TIMESTAMP('2025-10-29 08:01:21.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='en_US'
;

-- 2025-10-29T08:01:21.327Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:01:21.596Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'en_US')
;

-- Element: BLDate
-- 2025-10-29T08:01:42.053Z
UPDATE AD_Element_Trl SET Help='Das Frachtbriefdatum wird automatisch nach Abschluss des Transports aus dem Transportauftrag in die Bestellung übernommen.
Eine manuelle Änderung des Frachtbriefdatums in der Bestellung ist nicht zulässig, da es immer aus dem zugehörigen Transportauftrag synchronisiert wird.',Updated=TO_TIMESTAMP('2025-10-29 08:01:42.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='de_DE'
;

-- 2025-10-29T08:01:42.055Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:01:42.496Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584075,'de_DE')
;

-- 2025-10-29T08:01:42.497Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'de_DE')
;

-- Element: BLDate
-- 2025-10-29T08:01:55.934Z
UPDATE AD_Element_Trl SET Help='Das Frachtbriefdatum wird automatisch nach Abschluss des Transports aus dem Transportauftrag in die Bestellung übernommen.
Eine manuelle Änderung des Frachtbriefdatums in der Bestellung ist nicht zulässig, da es immer aus dem zugehörigen Transportauftrag synchronisiert wird.',Updated=TO_TIMESTAMP('2025-10-29 08:01:55.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='de_CH'
;

-- 2025-10-29T08:01:55.936Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:01:56.195Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'de_CH')
;

-- Element: ETA
-- 2025-10-29T08:21:33.019Z
UPDATE AD_Element_Trl SET Description='Specifies the expected arrival date and time of the goods at their destination, as determined in the Transport Order. The ETA is used for shipment tracking, delivery planning, and scheduling of warehouse operations.', Help='The ETA is automatically synchronized from the Transport Order to the Purchase Order when the transport information is updated.
Manual changes of the ETA in the Purchase Order are not allowed — it is always maintained in the corresponding Transport Order.',Updated=TO_TIMESTAMP('2025-10-29 08:21:33.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584067 AND AD_Language='en_US'
;

-- 2025-10-29T08:21:33.025Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:21:33.303Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584067,'en_US')
;

-- Element: ETA
-- 2025-10-29T08:22:02.586Z
UPDATE AD_Element_Trl SET Description='Gibt das voraussichtliche Ankunftsdatum und die Uhrzeit der Waren am Zielort an, wie sie im Transportauftrag festgelegt sind. Die ETA wird für Sendungsverfolgung, Lieferplanung und Terminierung von Lagerprozessen verwendet.', Help='Die ETA wird automatisch aus dem Transportauftrag in die Bestellung übernommen, wenn die Transportinformationen aktualisiert werden.
Eine manuelle Änderung der ETA in der Bestellung ist nicht zulässig, da sie immer im entsprechenden Transportauftrag gepflegt wird.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-29 08:22:02.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584067 AND AD_Language='de_DE'
;

-- 2025-10-29T08:22:02.587Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:22:03.043Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584067,'de_DE')
;

-- 2025-10-29T08:22:03.045Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584067,'de_DE')
;

-- Element: ETA
-- 2025-10-29T08:22:16.270Z
UPDATE AD_Element_Trl SET Description='Gibt das voraussichtliche Ankunftsdatum und die Uhrzeit der Waren am Zielort an, wie sie im Transportauftrag festgelegt sind. Die ETA wird für Sendungsverfolgung, Lieferplanung und Terminierung von Lagerprozessen verwendet.', Help='Die ETA wird automatisch aus dem Transportauftrag in die Bestellung übernommen, wenn die Transportinformationen aktualisiert werden.
Eine manuelle Änderung der ETA in der Bestellung ist nicht zulässig, da sie immer im entsprechenden Transportauftrag gepflegt wird.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-29 08:22:16.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584067 AND AD_Language='de_CH'
;

-- 2025-10-29T08:22:16.271Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:22:16.544Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584067,'de_CH')
;

-- Element: ETA
-- 2025-10-29T08:29:21.306Z
UPDATE AD_Element_Trl SET Description='The ETA is used for shipment tracking, delivery planning, and scheduling of warehouse operations. The ETA is automatically synchronized from the Transport Order to the Purchase Order when the transport information is updated. Manual changes of the ETA in the Purchase Order are not allowed — it is always maintained in the corresponding Transport Order.', Help='',Updated=TO_TIMESTAMP('2025-10-29 08:29:21.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584067 AND AD_Language='en_US'
;

-- 2025-10-29T08:29:21.307Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:29:21.570Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584067,'en_US')
;

-- Element: ETA
-- 2025-10-29T08:29:48.918Z
UPDATE AD_Element_Trl SET Description='Die ETA wird für die Sendungsverfolgung, die Lieferplanung und die Terminierung von Lagerprozessen verwendet. Die ETA wird automatisch aus dem Transportauftrag in die Bestellung übernommen, wenn die Transportinformationen aktualisiert werden. Eine manuelle Änderung der ETA in der Bestellung ist nicht zulässig, da sie immer im entsprechenden Transportauftrag gepflegt wird.', Help='',Updated=TO_TIMESTAMP('2025-10-29 08:29:48.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584067 AND AD_Language='de_DE'
;

-- 2025-10-29T08:29:48.921Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:29:49.334Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584067,'de_DE')
;

-- 2025-10-29T08:29:49.336Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584067,'de_DE')
;

-- Element: ETA
-- 2025-10-29T08:29:55.961Z
UPDATE AD_Element_Trl SET Description='Die ETA wird für die Sendungsverfolgung, die Lieferplanung und die Terminierung von Lagerprozessen verwendet. Die ETA wird automatisch aus dem Transportauftrag in die Bestellung übernommen, wenn die Transportinformationen aktualisiert werden. Eine manuelle Änderung der ETA in der Bestellung ist nicht zulässig, da sie immer im entsprechenden Transportauftrag gepflegt wird.', Help='',Updated=TO_TIMESTAMP('2025-10-29 08:29:55.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584067 AND AD_Language='de_CH'
;

-- 2025-10-29T08:29:55.963Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:29:56.216Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584067,'de_CH')
;

-- Element: BLDate
-- 2025-10-29T08:30:59.009Z
UPDATE AD_Element_Trl SET Description='Date when the Bill of Lading was issued by the carrier. The Bill of Lading Date is automatically synchronized from the Transport Order to the Purchase Order once the transport is completed. Manual changes of the BL Date in the Purchase Order are not allowed — it is always derived from the corresponding Transport Order.', Help='',Updated=TO_TIMESTAMP('2025-10-29 08:30:59.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='en_US'
;

-- 2025-10-29T08:30:59.011Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:30:59.301Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'en_US')
;

-- Element: BLDate
-- 2025-10-29T08:31:34.124Z
UPDATE AD_Element_Trl SET Description='Datum, an dem der Frachtbrief vom Frachtführer ausgestellt wurde. Das Frachtbriefdatum wird automatisch nach Abschluss des Transports aus dem Transportauftrag in die Bestellung übernommen. Eine manuelle Änderung des Frachtbriefdatums in der Bestellung ist nicht zulässig, da es immer aus dem entsprechenden Transportauftrag abgeleitet wird.', Help='',Updated=TO_TIMESTAMP('2025-10-29 08:31:34.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='de_DE'
;

-- 2025-10-29T08:31:34.125Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:31:34.548Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584075,'de_DE')
;

-- 2025-10-29T08:31:34.550Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'de_DE')
;

-- Element: BLDate
-- 2025-10-29T08:31:42.332Z
UPDATE AD_Element_Trl SET Description='Datum, an dem der Frachtbrief vom Frachtführer ausgestellt wurde. Das Frachtbriefdatum wird automatisch nach Abschluss des Transports aus dem Transportauftrag in die Bestellung übernommen. Eine manuelle Änderung des Frachtbriefdatums in der Bestellung ist nicht zulässig, da es immer aus dem entsprechenden Transportauftrag abgeleitet wird.', Help='',Updated=TO_TIMESTAMP('2025-10-29 08:31:42.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584075 AND AD_Language='de_CH'
;

-- 2025-10-29T08:31:42.334Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:31:42.601Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584075,'de_CH')
;

-- 2025-10-29T08:34:49.433Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584182,0,TO_TIMESTAMP('2025-10-29 08:34:49.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Date when the supplier’s invoice was issued. The Invoice Date is automatically synchronized from the Supplier Invoice to the Purchase Order when the invoice is received or posted in the system. Manual changes of the Invoice Date in the Purchase Order are not allowed — it is always derived from the corresponding Supplier Invoice.','D','Y','Invoice date','Invoice date',TO_TIMESTAMP('2025-10-29 08:34:49.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-29T08:34:49.434Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584182 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-29T08:34:57.182Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-29 08:34:57.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584182 AND AD_Language='en_US'
;

-- 2025-10-29T08:34:57.186Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584182,'en_US')
;

-- Element: null
-- 2025-10-29T08:36:04.407Z
UPDATE AD_Element_Trl SET Description='Datum, an dem die Kreditorenrechnung ausgestellt wurde. Das Rechnungsdatum wird automatisch aus der Kreditorenrechnung in die Bestellung übernommen, wenn die Rechnung im System empfangen oder gebucht wird. Eine manuelle Änderung des Rechnungsdatums in der Bestellung ist nicht zulässig, da es immer aus der entsprechenden Kreditorenrechnung abgeleitet wird.', IsTranslated='Y', Name='Rechnungsdatum', PrintName='Rechnungsdatum',Updated=TO_TIMESTAMP('2025-10-29 08:36:04.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584182 AND AD_Language='de_DE'
;

-- 2025-10-29T08:36:04.409Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:36:05.109Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584182,'de_DE')
;

-- 2025-10-29T08:36:05.112Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584182,'de_DE')
;

-- Element: null
-- 2025-10-29T08:36:15.328Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungsdatum', PrintName='Rechnungsdatum',Updated=TO_TIMESTAMP('2025-10-29 08:36:15.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584182 AND AD_Language='de_CH'
;

-- 2025-10-29T08:36:15.329Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:36:15.583Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584182,'de_CH')
;

-- Element: null
-- 2025-10-29T08:36:20.583Z
UPDATE AD_Element_Trl SET Description='Datum, an dem die Kreditorenrechnung ausgestellt wurde. Das Rechnungsdatum wird automatisch aus der Kreditorenrechnung in die Bestellung übernommen, wenn die Rechnung im System empfangen oder gebucht wird. Eine manuelle Änderung des Rechnungsdatums in der Bestellung ist nicht zulässig, da es immer aus der entsprechenden Kreditorenrechnung abgeleitet wird.',Updated=TO_TIMESTAMP('2025-10-29 08:36:20.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584182 AND AD_Language='de_CH'
;

-- 2025-10-29T08:36:20.584Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-29T08:36:20.847Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584182,'de_CH')
;

-- Field: Bestellung_OLD(181,D) -> Bestellung(294,D) -> Rechnungsdatum
-- Column: C_Order.InvoiceDate
-- 2025-10-29T08:36:43.645Z
UPDATE AD_Field SET AD_Name_ID=584182, Description='Datum, an dem die Kreditorenrechnung ausgestellt wurde. Das Rechnungsdatum wird automatisch aus der Kreditorenrechnung in die Bestellung übernommen, wenn die Rechnung im System empfangen oder gebucht wird. Eine manuelle Änderung des Rechnungsdatums in der Bestellung ist nicht zulässig, da es immer aus der entsprechenden Kreditorenrechnung abgeleitet wird.', Help=NULL, Name='Rechnungsdatum',Updated=TO_TIMESTAMP('2025-10-29 08:36:43.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755109
;

-- 2025-10-29T08:36:43.647Z
UPDATE AD_Field_Trl trl SET Description='Datum, an dem die Kreditorenrechnung ausgestellt wurde. Das Rechnungsdatum wird automatisch aus der Kreditorenrechnung in die Bestellung übernommen, wenn die Rechnung im System empfangen oder gebucht wird. Eine manuelle Änderung des Rechnungsdatums in der Bestellung ist nicht zulässig, da es immer aus der entsprechenden Kreditorenrechnung abgeleitet wird.',Name='Rechnungsdatum' WHERE AD_Field_ID=755109 AND AD_Language='de_DE'
;

-- 2025-10-29T08:36:43.649Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584182)
;

-- 2025-10-29T08:36:43.654Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755109
;

-- 2025-10-29T08:36:43.658Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755109)
;

-- Column: C_Order.InvoiceDate
-- 2025-10-29T08:37:50.882Z
UPDATE AD_Column SET Description='Date when the supplier’s invoice was issued. The Invoice Date is automatically synchronized from the Supplier Invoice to the Purchase Order when the invoice is received or posted in the system. Manual changes of the Invoice Date in the Purchase Order are not allowed — it is always derived from the corresponding Supplier Invoice.',Updated=TO_TIMESTAMP('2025-10-29 08:37:50.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591443
;

-- 2025-10-29T08:37:50.887Z
UPDATE AD_Field SET Name='Invoice date', Description='Date when the supplier’s invoice was issued. The Invoice Date is automatically synchronized from the Supplier Invoice to the Purchase Order when the invoice is received or posted in the system. Manual changes of the Invoice Date in the Purchase Order are not allowed — it is always derived from the corresponding Supplier Invoice.', Help=NULL WHERE AD_Column_ID=591443
;


-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> dates.Invoice date
-- Column: C_Order.InvoiceDate
-- 2025-10-29T08:59:42.396Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-10-29 08:59:42.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637957
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> dates.ETA
-- Column: C_Order.ETA
-- 2025-10-29T08:59:45.590Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-10-29 08:59:45.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637959
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> dates.B/L Datum
-- Column: C_Order.BLDate
-- 2025-10-29T08:59:48.620Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-10-29 08:59:48.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637958
;
