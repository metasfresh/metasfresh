-- 21.12.2016 08:24
-- URL zum Konzept
UPDATE AD_Process SET Description='Erweitert eine Paritionierungskonfiguration um Tabellen, die eine der bereits bestehenden Tabellen per [*_Table_ID,*Record_ID] referenzieren.',Updated=TO_TIMESTAMP('2016-12-21 08:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540732
;

-- 21.12.2016 08:24
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540732
;

-- 21.12.2016 10:04
-- URL zum Konzept
UPDATE AD_Process SET Description='Überprüpft die aktuelle Auswahl (falls aus dem Fenster heraus gestartet, sonst alle Partitionen) und legt ihre jeweiligen SOLL DLM-Level sowie ggf. den Zeitpunkt der der nächsten Überprüfung fest.',Updated=TO_TIMESTAMP('2016-12-21 10:04:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540734
;

-- 21.12.2016 10:04
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540734
;

-- 21.12.2016 10:05
-- URL zum Konzept
UPDATE AD_Process SET Description='Migriert die aktuelle Auswahl (falls aus dem Fenster heraus gestartet, sonst alle Partitionen) und aktualisiert die jeweiligen IST DLM-Level entsprechend der Vorgaben aus den SOLL DLM-Leveln.',Updated=TO_TIMESTAMP('2016-12-21 10:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540735
;

-- 21.12.2016 10:05
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540735
;

-- 21.12.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=31,Updated=TO_TIMESTAMP('2016-12-21 10:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557328
;

-- 21.12.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=40,Updated=TO_TIMESTAMP('2016-12-21 10:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557329
;

-- 21.12.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=30,Updated=TO_TIMESTAMP('2016-12-21 10:13:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557328
;


--
-- M_ReceiptSchedule M_HU_LUTUConfigration_ID
--
-- 21.12.2016 16:46
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-21 16:46:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550962
;

--
-- M_HU_Trx_Line_HU.Record_ID
-- Note that the corresponding AD_Table is know to point to both e.g. M_ReceiptSchedule (which prompts us to make the column a boundary) and also to M_HU and M_HU_Item.
-- However those lines that reference an M_HU* record with their AD_Table_ID/Record_ID also reference the same record with their M_HU_ID resp M_HU_Item_ID column.
-- I verified this using
-- select * from M_HU_Trx_Line where ad_Table_id=get_table_id('M_HU') and Record_ID!=M_HU_ID;
-- and
-- select * from M_HU_Trx_Line where ad_Table_id=get_table_id('M_HU_Item') and Record_ID!=M_HU_Item_ID;
--
-- 21.12.2016 17:04
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-21 17:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549842
;

COMMIT;
SELECT dlm.recreate_dlm_triggers('M_HU_Trx_Line');
SELECT dlm.recreate_dlm_triggers('M_ReceiptSchedule');
