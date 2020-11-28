
-- 13.11.2015 12:41
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.order.process.C_Order_CreatePOFromSOs', Help='Nach Fertgstellung von Aufträgen können Sie eine oder mehrere Bestellungen für jeden Auftrag erstellen. Eine Bestellung bezieht sich auf mehr als einen Auftrag beziehen (d.h. Konsolidierung von Aufträgen).<br>
Die Organisation des Auftrages wird bei der Erstellung der Bestellung verwendet. 
Wenn eine (Standard-) Belegart für Bestellungen auf Organisationsebene definiert ist, wird diese verwendet statt der auf Mandantenebene definierten. 
Bestellungen werden für alle Auftragspositionen erstellt, auf denen das Produkt einen gegenwärtigen Lieferanten hat und dieser Lieferant eine Einkaufspreisliste mit allen Produkten auf der aktuellsten Preislistenversion hat. 
Die Maßeinheit wird kopiert.
Bestellung und Auftrag können verschiedene Währungen haben. 
Nachdem der Prozeß beendet ist, müssen Sie Auftrag und Bestellung manuell synchronisieren (z.B. im Fall von zusätzlichen Positionen und geänderten Positionen (Produkt und Menge)).',Updated=TO_TIMESTAMP('2015-11-13 12:41:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=193
;

-- 13.11.2015 12:41
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=193
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N', Name='Zugesagter Termin Von (Filter)',Updated=TO_TIMESTAMP('2015-11-13 12:42:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=254
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=254
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N', Name='Zugesagter Termin bis (Filter)',Updated=TO_TIMESTAMP('2015-11-13 12:42:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540625
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=540625
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='Zugesagter Termin von (Filter)',Updated=TO_TIMESTAMP('2015-11-13 12:42:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=254
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=254
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='Geschäftspartner (Filter)',Updated=TO_TIMESTAMP('2015-11-13 12:42:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=255
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=255
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='Lieferant (Filter)',Updated=TO_TIMESTAMP('2015-11-13 12:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=256
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=256
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N', Name='Auftrag (Filter)',Updated=TO_TIMESTAMP('2015-11-13 12:42:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=257
;

-- 13.11.2015 12:42
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=257
;

-- 13.11.2015 12:43
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2015-11-13 12:43:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=256
;

-- 13.11.2015 12:43
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2015-11-13 12:43:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=255
;

-- 13.11.2015 12:43
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N', Name='Referenz (Filter)',Updated=TO_TIMESTAMP('2015-11-13 12:43:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540636
;

-- 13.11.2015 12:43
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=540636
;

-- 13.11.2015 12:43
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=70,Updated=TO_TIMESTAMP('2015-11-13 12:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=471
;

-- 13.11.2015 12:52
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@C_Order_ID/0@=0',Updated=TO_TIMESTAMP('2015-11-13 12:52:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540636
;

-- 13.11.2015 12:52
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@C_Order_ID/0@=0',Updated=TO_TIMESTAMP('2015-11-13 12:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=256
;

-- 13.11.2015 12:52
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@C_Order_ID/0@=0',Updated=TO_TIMESTAMP('2015-11-13 12:52:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=255
;

-- 13.11.2015 12:52
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@C_Order_ID/0@=0',Updated=TO_TIMESTAMP('2015-11-13 12:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540625
;

-- 13.11.2015 12:52
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@C_Order_ID/0@=0',Updated=TO_TIMESTAMP('2015-11-13 12:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=254
;




-- 13.11.2015 13:53
-- URL zum Konzept
UPDATE AD_Process SET Value='C_Order_CreatePOFromSOs',Updated=TO_TIMESTAMP('2015-11-13 13:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=193
;

-- 13.11.2015 13:53
-- URL zum Konzept
UPDATE AD_Process SET EntityType='de.metas.order',Updated=TO_TIMESTAMP('2015-11-13 13:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=193
;

-- 13.11.2015 13:53
-- URL zum Konzept
UPDATE AD_Table_Process SET EntityType='de.metas.order',Updated=TO_TIMESTAMP('2015-11-13 13:53:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=193 AND AD_Table_ID=259
;

-- 13.11.2015 13:54
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic=NULL,Updated=TO_TIMESTAMP('2015-11-13 13:54:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=256
;

-- 13.11.2015 13:54
-- URL zum Konzept
UPDATE AD_Process_Para SET Description=NULL,Updated=TO_TIMESTAMP('2015-11-13 13:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=256
;

-- 13.11.2015 13:54
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=256
;

UPDATE AD_Process_Para SET EntityType='de.metas.order',Updated=TO_TIMESTAMP('2015-11-13 13:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=193
;


-- 13.11.2015 14:13
-- URL zum Konzept
UPDATE AD_Process SET Help='Nach Fertigstellung von Aufträgen können Sie eine oder mehrere Bestellungen für jeden Auftrag erstellen. Eine Bestellung bezieht sich auf mehr als einen Auftrag beziehen (d.h. Konsolidierung von Aufträgen).<br>
Die Organisation des Auftrages wird bei der Erstellung der Bestellung verwendet. 
Wenn eine (Standard-) Belegart für Bestellungen auf Organisationsebene definiert ist, wird diese verwendet statt der auf Mandantenebene definierten. 
Bestellungen werden für alle Auftragspositionen erstellt, auf denen das Produkt einen gegenwärtigen Lieferanten hat und dieser Lieferant eine Einkaufspreisliste mit allen Produkten auf der aktuellsten Preislistenversion hat. 
Die Maßeinheit wird kopiert.
Bestellung und Auftrag können verschiedene Währungen haben. 
Nachdem der Prozeß beendet ist, müssen Sie Auftrag und Bestellung manuell synchronisieren (z.B. im Fall von zusätzlichen Positionen und geänderten Positionen (Produkt und Menge)).',Updated=TO_TIMESTAMP('2015-11-13 14:13:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=193
;

-- 13.11.2015 14:13
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=193
;


-- 16.11.2015 08:02
-- URL zum Konzept
UPDATE AD_Process SET Help='Erstellt Bestellungen zu fertig gestellten Aufträgen.<br>
Die Bestellmengen entsprechen den noch offenen Mengen der jeweiligen Aufträge.<br>
Bestellungen werden für alle Auftragspositionen erstellt, auf denen das Produkt einen gegenwärtigen Lieferanten hat und dieser Lieferant eine Einkaufspreisliste hat. 
Eine Bestellung kann sich auf mehr als einen Auftrag beziehen (d.h. Konsolidierung von Aufträgen).
Auftragszeilen mit gleichem Lieferanten, Produkt und Merkmalssatz werden zu einer Bestellzeile aggregiert.<br>
Als Maßeinheit der Bestellzeile wird die Maßeinheit des jeweiligen Produktes benutzt.<br>
Bestellung und Auftrag können verschiedene Währungen haben.
Nachdem der Prozeß beendet ist, können die Bestellungen manuell nachbearbeitet werden.',Updated=TO_TIMESTAMP('2015-11-16 08:02:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=193
;

-- 16.11.2015 08:02
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=193
;

UPDATE AD_Process_Para SET EntityType='de.metas.order', Updated=TO_TIMESTAMP('2015-11-16 08:02:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=193;
