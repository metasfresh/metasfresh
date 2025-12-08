-- 2022-09-15T13:56:15.092Z
UPDATE AD_Element SET ColumnName='QtyStockEstimateSeqNo_AtDate',Updated=TO_TIMESTAMP('2022-09-15 16:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580208
;

-- 2022-09-15T13:56:15.109Z
UPDATE AD_Column SET ColumnName='QtyStockEstimateSeqNo_AtDate', Name='Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE AD_Element_ID=580208
;

-- 2022-09-15T13:56:15.110Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateSeqNo_AtDate', Name='Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL, AD_Element_ID=580208 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATESEQNO_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T13:56:15.114Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateSeqNo_AtDate', Name='Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE AD_Element_ID=580208 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T13:57:08.049Z
UPDATE AD_Element SET ColumnName='PMM_QtyPromised_OnDate_AtDate',Updated=TO_TIMESTAMP('2022-09-15 16:57:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070
;

-- 2022-09-15T13:57:08.051Z
UPDATE AD_Column SET ColumnName='PMM_QtyPromised_OnDate_AtDate', Name='Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID=543070
;

-- 2022-09-15T13:57:08.052Z
UPDATE AD_Process_Para SET ColumnName='PMM_QtyPromised_OnDate_AtDate', Name='Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL, AD_Element_ID=543070 WHERE UPPER(ColumnName)='PMM_QTYPROMISED_ONDATE_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T13:57:08.053Z
UPDATE AD_Process_Para SET ColumnName='PMM_QtyPromised_OnDate_AtDate', Name='Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID=543070 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T13:57:41.266Z
UPDATE AD_Element SET ColumnName='QtyDemand_SalesOrder_AtDate',Updated=TO_TIMESTAMP('2022-09-15 16:57:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579889
;

-- 2022-09-15T13:57:41.268Z
UPDATE AD_Column SET ColumnName='QtyDemand_SalesOrder_AtDate', Name='Beauftragt - offen', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579889
;

-- 2022-09-15T13:57:41.270Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_SalesOrder_AtDate', Name='Beauftragt - offen', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL, AD_Element_ID=579889 WHERE UPPER(ColumnName)='QTYDEMAND_SALESORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T13:57:41.271Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_SalesOrder_AtDate', Name='Beauftragt - offen', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579889 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T13:58:19.730Z
UPDATE AD_Element SET ColumnName='QtyDemand_DD_Order_AtDate',Updated=TO_TIMESTAMP('2022-09-15 16:58:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579891
;

-- 2022-09-15T13:58:19.733Z
UPDATE AD_Column SET ColumnName='QtyDemand_DD_Order_AtDate', Name='Distribution ab - offen', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579891
;

-- 2022-09-15T13:58:19.734Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_DD_Order_AtDate', Name='Distribution ab - offen', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL, AD_Element_ID=579891 WHERE UPPER(ColumnName)='QTYDEMAND_DD_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T13:58:19.735Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_DD_Order_AtDate', Name='Distribution ab - offen', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579891 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T13:58:42.457Z
UPDATE AD_Element SET ColumnName='QtyDemandSum_AtDate',Updated=TO_TIMESTAMP('2022-09-15 16:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579892
;

-- 2022-09-15T13:58:42.460Z
UPDATE AD_Column SET ColumnName='QtyDemandSum_AtDate', Name='Summe Abgänge - offen', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='' WHERE AD_Element_ID=579892
;

-- 2022-09-15T13:58:42.462Z
UPDATE AD_Process_Para SET ColumnName='QtyDemandSum_AtDate', Name='Summe Abgänge - offen', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='', AD_Element_ID=579892 WHERE UPPER(ColumnName)='QTYDEMANDSUM_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T13:58:42.465Z
UPDATE AD_Process_Para SET ColumnName='QtyDemandSum_AtDate', Name='Summe Abgänge - offen', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='' WHERE AD_Element_ID=579892 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T13:59:02.666Z
UPDATE AD_Element SET ColumnName='QtySupply_PP_Order_AtDate',Updated=TO_TIMESTAMP('2022-09-15 16:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579894
;

-- 2022-09-15T13:59:02.667Z
UPDATE AD_Column SET ColumnName='QtySupply_PP_Order_AtDate', Name='Produktionsempfang - offen', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579894
;

-- 2022-09-15T13:59:02.667Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PP_Order_AtDate', Name='Produktionsempfang - offen', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL, AD_Element_ID=579894 WHERE UPPER(ColumnName)='QTYSUPPLY_PP_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T13:59:02.669Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PP_Order_AtDate', Name='Produktionsempfang - offen', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579894 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T13:59:24.314Z
UPDATE AD_Element SET ColumnName='QtySupply_PurchaseOrder_AtDate',Updated=TO_TIMESTAMP('2022-09-15 16:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579895
;

-- 2022-09-15T13:59:24.316Z
UPDATE AD_Column SET ColumnName='QtySupply_PurchaseOrder_AtDate', Name='Bestellt - offen', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579895
;

-- 2022-09-15T13:59:24.317Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PurchaseOrder_AtDate', Name='Bestellt - offen', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL, AD_Element_ID=579895 WHERE UPPER(ColumnName)='QTYSUPPLY_PURCHASEORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T13:59:24.318Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PurchaseOrder_AtDate', Name='Bestellt - offen', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579895 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T13:59:43.044Z
UPDATE AD_Element SET ColumnName='QtySupply_DD_Order_AtDate',Updated=TO_TIMESTAMP('2022-09-15 16:59:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579896
;

-- 2022-09-15T13:59:43.046Z
UPDATE AD_Column SET ColumnName='QtySupply_DD_Order_AtDate', Name='Distribution an - offen', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579896
;

-- 2022-09-15T13:59:43.048Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_DD_Order_AtDate', Name='Distribution an - offen', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL, AD_Element_ID=579896 WHERE UPPER(ColumnName)='QTYSUPPLY_DD_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T13:59:43.049Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_DD_Order_AtDate', Name='Distribution an - offen', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579896 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:00:07.405Z
UPDATE AD_Element SET ColumnName='QtySupplySum_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579897
;

-- 2022-09-15T14:00:07.407Z
UPDATE AD_Column SET ColumnName='QtySupplySum_AtDate', Name='Summe Zugänge - offen', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL WHERE AD_Element_ID=579897
;

-- 2022-09-15T14:00:07.409Z
UPDATE AD_Process_Para SET ColumnName='QtySupplySum_AtDate', Name='Summe Zugänge - offen', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL, AD_Element_ID=579897 WHERE UPPER(ColumnName)='QTYSUPPLYSUM_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:00:07.410Z
UPDATE AD_Process_Para SET ColumnName='QtySupplySum_AtDate', Name='Summe Zugänge - offen', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL WHERE AD_Element_ID=579897 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:00:30.148Z
UPDATE AD_Element SET ColumnName='QtySupplyRequired_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:00:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898
;

-- 2022-09-15T14:00:30.151Z
UPDATE AD_Column SET ColumnName='QtySupplyRequired_AtDate', Name='Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID=579898
;

-- 2022-09-15T14:00:30.152Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyRequired_AtDate', Name='Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL, AD_Element_ID=579898 WHERE UPPER(ColumnName)='QTYSUPPLYREQUIRED_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:00:30.154Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyRequired_AtDate', Name='Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID=579898 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:00:43.402Z
UPDATE AD_Element SET ColumnName='QtySupplyToSchedule_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899
;

-- 2022-09-15T14:00:43.404Z
UPDATE AD_Column SET ColumnName='QtySupplyToSchedule_AtDate', Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID=579899
;

-- 2022-09-15T14:00:43.405Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule_AtDate', Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL, AD_Element_ID=579899 WHERE UPPER(ColumnName)='QTYSUPPLYTOSCHEDULE_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:00:43.406Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule_AtDate', Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID=579899 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:01:17.585Z
UPDATE AD_Element SET ColumnName='QtyMaterialentnahme_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:01:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653
;

-- 2022-09-15T14:01:17.587Z
UPDATE AD_Column SET ColumnName='QtyMaterialentnahme_AtDate', Name='Materialentnahme', Description=NULL, Help=NULL WHERE AD_Element_ID=542653
;

-- 2022-09-15T14:01:17.588Z
UPDATE AD_Process_Para SET ColumnName='QtyMaterialentnahme_AtDate', Name='Materialentnahme', Description=NULL, Help=NULL, AD_Element_ID=542653 WHERE UPPER(ColumnName)='QTYMATERIALENTNAHME_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:01:17.588Z
UPDATE AD_Process_Para SET ColumnName='QtyMaterialentnahme_AtDate', Name='Materialentnahme', Description=NULL, Help=NULL WHERE AD_Element_ID=542653 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:01:36.305Z
UPDATE AD_Element SET ColumnName='QtyDemand_PP_Order_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:01:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579890
;

-- 2022-09-15T14:01:36.307Z
UPDATE AD_Column SET ColumnName='QtyDemand_PP_Order_AtDate', Name='Produktionszuteilung - offen', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579890
;

-- 2022-09-15T14:01:36.308Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_PP_Order_AtDate', Name='Produktionszuteilung - offen', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL, AD_Element_ID=579890 WHERE UPPER(ColumnName)='QTYDEMAND_PP_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:01:36.309Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_PP_Order_AtDate', Name='Produktionszuteilung - offen', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579890 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:01:53.867Z
UPDATE AD_Element SET ColumnName='QtyStockCurrent_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579905
;

-- 2022-09-15T14:01:53.869Z
UPDATE AD_Column SET ColumnName='QtyStockCurrent_AtDate', Name='Planbestand', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL WHERE AD_Element_ID=579905
;

-- 2022-09-15T14:01:53.871Z
UPDATE AD_Process_Para SET ColumnName='QtyStockCurrent_AtDate', Name='Planbestand', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL, AD_Element_ID=579905 WHERE UPPER(ColumnName)='QTYSTOCKCURRENT_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:01:53.872Z
UPDATE AD_Process_Para SET ColumnName='QtyStockCurrent_AtDate', Name='Planbestand', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL WHERE AD_Element_ID=579905 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:02:07.085Z
UPDATE AD_Element SET ColumnName='QtyStockEstimateCount_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:02:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900
;

-- 2022-09-15T14:02:07.087Z
UPDATE AD_Column SET ColumnName='QtyStockEstimateCount_AtDate', Name='Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID=579900
;

-- 2022-09-15T14:02:07.089Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateCount_AtDate', Name='Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL, AD_Element_ID=579900 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATECOUNT_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:02:07.090Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateCount_AtDate', Name='Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID=579900 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:02:26.507Z
UPDATE AD_Element SET ColumnName='QtyStockEstimateTime_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:02:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902
;

-- 2022-09-15T14:02:26.508Z
UPDATE AD_Column SET ColumnName='QtyStockEstimateTime_AtDate', Name='Zeitpunkt der Zählung', Description=NULL, Help=NULL WHERE AD_Element_ID=579902
;

-- 2022-09-15T14:02:26.509Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateTime_AtDate', Name='Zeitpunkt der Zählung', Description=NULL, Help=NULL, AD_Element_ID=579902 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATETIME_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:02:26.510Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateTime_AtDate', Name='Zeitpunkt der Zählung', Description=NULL, Help=NULL WHERE AD_Element_ID=579902 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:02:42.323Z
UPDATE AD_Element SET ColumnName='QtyInventoryCount_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:02:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903
;

-- 2022-09-15T14:02:42.325Z
UPDATE AD_Column SET ColumnName='QtyInventoryCount_AtDate', Name='Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID=579903
;

-- 2022-09-15T14:02:42.327Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryCount_AtDate', Name='Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL, AD_Element_ID=579903 WHERE UPPER(ColumnName)='QTYINVENTORYCOUNT_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:02:42.329Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryCount_AtDate', Name='Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID=579903 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:02:53.727Z
UPDATE AD_Element SET ColumnName='QtyInventoryTime_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:02:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904
;

-- 2022-09-15T14:02:53.728Z
UPDATE AD_Column SET ColumnName='QtyInventoryTime_AtDate', Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID=579904
;

-- 2022-09-15T14:02:53.729Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryTime_AtDate', Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL, AD_Element_ID=579904 WHERE UPPER(ColumnName)='QTYINVENTORYTIME_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:02:53.730Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryTime_AtDate', Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID=579904 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T14:03:06.825Z
UPDATE AD_Element SET ColumnName='QtyExpectedSurplus_AtDate',Updated=TO_TIMESTAMP('2022-09-15 17:03:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579906
;

-- 2022-09-15T14:03:06.826Z
UPDATE AD_Column SET ColumnName='QtyExpectedSurplus_AtDate', Name='Erw. Überschuss', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL WHERE AD_Element_ID=579906
;

-- 2022-09-15T14:03:06.827Z
UPDATE AD_Process_Para SET ColumnName='QtyExpectedSurplus_AtDate', Name='Erw. Überschuss', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL, AD_Element_ID=579906 WHERE UPPER(ColumnName)='QTYEXPECTEDSURPLUS_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T14:03:06.828Z
UPDATE AD_Process_Para SET ColumnName='QtyExpectedSurplus_AtDate', Name='Erw. Überschuss', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL WHERE AD_Element_ID=579906 AND IsCentrallyMaintained='Y'
;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyStockEstimateSeqNo TO QtyStockEstimateSeqNo_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN PMM_QtyPromised_OnDate TO PMM_QtyPromised_OnDate_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyDemand_SalesOrder TO QtyDemand_SalesOrder_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyDemand_DD_Order TO QtyDemand_DD_Order_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyDemandSum TO QtyDemandSum_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtySupply_PP_Order TO QtySupply_PP_Order_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtySupply_PurchaseOrder TO QtySupply_PurchaseOrder_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtySupply_DD_Order TO QtySupply_DD_Order_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtySupplySum TO QtySupplySum_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtySupplyRequired TO QtySupplyRequired_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtySupplyToSchedule TO QtySupplyToSchedule_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyMaterialentnahme TO QtyMaterialentnahme_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyDemand_PP_Order TO QtyDemand_PP_Order_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyStockCurrent TO QtyStockCurrent_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyStockEstimateCount TO QtyStockEstimateCount_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyStockEstimateTime TO QtyStockEstimateTime_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyInventoryCount TO QtyInventoryCount_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyInventoryTime TO QtyInventoryTime_AtDate;

ALTER TABLE md_cockpit
    RENAME COLUMN QtyExpectedSurplus TO QtyExpectedSurplus_AtDate;

-- 2022-09-16T11:08:51.021Z
UPDATE AD_Element_Trl SET Name='Zählbestand Reihenfolge 📆', PrintName='Zählbestand Reihenfolge 📆',Updated=TO_TIMESTAMP('2022-09-16 14:08:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580208 AND AD_Language='de_CH'
;

-- 2022-09-16T11:08:51.059Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580208,'de_CH')
;

-- 2022-09-16T11:08:54.570Z
UPDATE AD_Element_Trl SET Name='Zählbestand Reihenfolge 📆', PrintName='Zählbestand Reihenfolge 📆',Updated=TO_TIMESTAMP('2022-09-16 14:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580208 AND AD_Language='de_DE'
;

-- 2022-09-16T11:08:54.574Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580208,'de_DE')
;

-- 2022-09-16T11:08:54.595Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580208,'de_DE')
;

-- 2022-09-16T11:08:54.596Z
UPDATE AD_Column SET ColumnName='QtyStockEstimateSeqNo_AtDate', Name='Zählbestand Reihenfolge 📆', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE AD_Element_ID=580208
;

-- 2022-09-16T11:08:54.597Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateSeqNo_AtDate', Name='Zählbestand Reihenfolge 📆', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL, AD_Element_ID=580208 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATESEQNO_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:08:54.598Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateSeqNo_AtDate', Name='Zählbestand Reihenfolge 📆', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE AD_Element_ID=580208 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:08:54.599Z
UPDATE AD_Field SET Name='Zählbestand Reihenfolge 📆', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580208) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580208)
;

-- 2022-09-16T11:08:54.613Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zählbestand Reihenfolge 📆', Name='Zählbestand Reihenfolge 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580208)
;

-- 2022-09-16T11:08:54.614Z
UPDATE AD_Tab SET Name='Zählbestand Reihenfolge 📆', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580208
;

-- 2022-09-16T11:08:54.615Z
UPDATE AD_WINDOW SET Name='Zählbestand Reihenfolge 📆', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE AD_Element_ID = 580208
;

-- 2022-09-16T11:08:54.616Z
UPDATE AD_Menu SET   Name = 'Zählbestand Reihenfolge 📆', Description = '"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580208
;

-- 2022-09-16T11:08:57.689Z
UPDATE AD_Element_Trl SET Name='Zählbestand Reihenfolge 📆', PrintName='Zählbestand Reihenfolge 📆',Updated=TO_TIMESTAMP('2022-09-16 14:08:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580208 AND AD_Language='en_US'
;

-- 2022-09-16T11:08:57.691Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580208,'en_US')
;

-- 2022-09-16T11:09:01.223Z
UPDATE AD_Element_Trl SET Name='Zählbestand Reihenfolge 📆', PrintName='Zählbestand Reihenfolge 📆',Updated=TO_TIMESTAMP('2022-09-16 14:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580208 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:09:01.224Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580208,'nl_NL')
;

-- 2022-09-16T11:09:26.537Z
UPDATE AD_Element_Trl SET Name='Zusage Lieferant 📆', PrintName='Zusage Lieferant 📆',Updated=TO_TIMESTAMP('2022-09-16 14:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070 AND AD_Language='de_CH'
;

-- 2022-09-16T11:09:26.538Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543070,'de_CH')
;

-- 2022-09-16T11:09:31.298Z
UPDATE AD_Element_Trl SET Name='Vendor Promised 📆', PrintName='Vendor Promised 📆',Updated=TO_TIMESTAMP('2022-09-16 14:09:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070 AND AD_Language='en_US'
;

-- 2022-09-16T11:09:31.300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543070,'en_US')
;

-- 2022-09-16T11:09:34.258Z
UPDATE AD_Element_Trl SET Name='Zusage Lieferant 📆', PrintName='Zusage Lieferant 📆',Updated=TO_TIMESTAMP('2022-09-16 14:09:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:09:34.261Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543070,'nl_NL')
;

-- 2022-09-16T11:09:37.199Z
UPDATE AD_Element_Trl SET Name='Zusage Lieferant 📆', PrintName='Zusage Lieferant 📆',Updated=TO_TIMESTAMP('2022-09-16 14:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070 AND AD_Language='de_DE'
;

-- 2022-09-16T11:09:37.200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543070,'de_DE')
;

-- 2022-09-16T11:09:37.207Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543070,'de_DE')
;

-- 2022-09-16T11:09:37.208Z
UPDATE AD_Column SET ColumnName='PMM_QtyPromised_OnDate_AtDate', Name='Zusage Lieferant 📆', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID=543070
;

-- 2022-09-16T11:09:37.209Z
UPDATE AD_Process_Para SET ColumnName='PMM_QtyPromised_OnDate_AtDate', Name='Zusage Lieferant 📆', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL, AD_Element_ID=543070 WHERE UPPER(ColumnName)='PMM_QTYPROMISED_ONDATE_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:09:37.211Z
UPDATE AD_Process_Para SET ColumnName='PMM_QtyPromised_OnDate_AtDate', Name='Zusage Lieferant 📆', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID=543070 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:09:37.211Z
UPDATE AD_Field SET Name='Zusage Lieferant 📆', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543070) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543070)
;

-- 2022-09-16T11:09:37.227Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zusage Lieferant 📆', Name='Zusage Lieferant 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543070)
;

-- 2022-09-16T11:09:37.228Z
UPDATE AD_Tab SET Name='Zusage Lieferant 📆', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543070
;

-- 2022-09-16T11:09:37.230Z
UPDATE AD_WINDOW SET Name='Zusage Lieferant 📆', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID = 543070
;

-- 2022-09-16T11:09:37.231Z
UPDATE AD_Menu SET   Name = 'Zusage Lieferant 📆', Description = 'Vom Lieferanten per Webapplikation zugesagte Menge', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543070
;

-- 2022-09-16T11:09:53.725Z
UPDATE AD_Element_Trl SET Name='Beauftragt - offen 📆', PrintName='Beauftragt - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:09:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579889 AND AD_Language='de_CH'
;

-- 2022-09-16T11:09:53.726Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579889,'de_CH')
;

-- 2022-09-16T11:09:56.400Z
UPDATE AD_Element_Trl SET Name='Beauftragt - offen 📆', PrintName='Beauftragt - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:09:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579889 AND AD_Language='de_DE'
;

-- 2022-09-16T11:09:56.402Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579889,'de_DE')
;

-- 2022-09-16T11:09:56.411Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579889,'de_DE')
;

-- 2022-09-16T11:09:56.412Z
UPDATE AD_Column SET ColumnName='QtyDemand_SalesOrder_AtDate', Name='Beauftragt - offen 📆', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579889
;

-- 2022-09-16T11:09:56.413Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_SalesOrder_AtDate', Name='Beauftragt - offen 📆', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL, AD_Element_ID=579889 WHERE UPPER(ColumnName)='QTYDEMAND_SALESORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:09:56.414Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_SalesOrder_AtDate', Name='Beauftragt - offen 📆', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579889 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:09:56.414Z
UPDATE AD_Field SET Name='Beauftragt - offen 📆', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579889) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579889)
;

-- 2022-09-16T11:09:56.424Z
UPDATE AD_PrintFormatItem pi SET PrintName='Beauftragt - offen 📆', Name='Beauftragt - offen 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579889)
;

-- 2022-09-16T11:09:56.425Z
UPDATE AD_Tab SET Name='Beauftragt - offen 📆', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579889
;

-- 2022-09-16T11:09:56.426Z
UPDATE AD_WINDOW SET Name='Beauftragt - offen 📆', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID = 579889
;

-- 2022-09-16T11:09:56.427Z
UPDATE AD_Menu SET   Name = 'Beauftragt - offen 📆', Description = 'Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579889
;

-- 2022-09-16T11:09:58.960Z
UPDATE AD_Element_Trl SET Name='Sold - pending 📆', PrintName='Sold - pending 📆',Updated=TO_TIMESTAMP('2022-09-16 14:09:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579889 AND AD_Language='en_US'
;

-- 2022-09-16T11:09:58.963Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579889,'en_US')
;

-- 2022-09-16T11:10:01.290Z
UPDATE AD_Element_Trl SET Name='Beauftragt - offen 📆', PrintName='Beauftragt - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:10:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579889 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:10:01.292Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579889,'nl_NL')
;

-- 2022-09-16T11:10:23.152Z
UPDATE AD_Element_Trl SET Name='Pending distribution source 📆', PrintName='Pending distribution source 📆',Updated=TO_TIMESTAMP('2022-09-16 14:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579891 AND AD_Language='en_US'
;

-- 2022-09-16T11:10:23.153Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579891,'en_US')
;

-- 2022-09-16T11:10:25.673Z
UPDATE AD_Element_Trl SET Name='Distribution ab - offen 📆', PrintName='Distribution ab - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579891 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:10:25.675Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579891,'nl_NL')
;

-- 2022-09-16T11:10:28.314Z
UPDATE AD_Element_Trl SET Name='Distribution ab - offen 📆', PrintName='Distribution ab - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:10:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579891 AND AD_Language='de_DE'
;

-- 2022-09-16T11:10:28.316Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579891,'de_DE')
;

-- 2022-09-16T11:10:28.324Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579891,'de_DE')
;

-- 2022-09-16T11:10:28.325Z
UPDATE AD_Column SET ColumnName='QtyDemand_DD_Order_AtDate', Name='Distribution ab - offen 📆', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579891
;

-- 2022-09-16T11:10:28.326Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_DD_Order_AtDate', Name='Distribution ab - offen 📆', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL, AD_Element_ID=579891 WHERE UPPER(ColumnName)='QTYDEMAND_DD_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:10:28.327Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_DD_Order_AtDate', Name='Distribution ab - offen 📆', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579891 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:10:28.327Z
UPDATE AD_Field SET Name='Distribution ab - offen 📆', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579891) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579891)
;

-- 2022-09-16T11:10:28.341Z
UPDATE AD_PrintFormatItem pi SET PrintName='Distribution ab - offen 📆', Name='Distribution ab - offen 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579891)
;

-- 2022-09-16T11:10:28.342Z
UPDATE AD_Tab SET Name='Distribution ab - offen 📆', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579891
;

-- 2022-09-16T11:10:28.343Z
UPDATE AD_WINDOW SET Name='Distribution ab - offen 📆', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID = 579891
;

-- 2022-09-16T11:10:28.343Z
UPDATE AD_Menu SET   Name = 'Distribution ab - offen 📆', Description = 'Noch weg zu bewegende Menge eines Distributionsauftrags.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579891
;

-- 2022-09-16T11:10:33.190Z
UPDATE AD_Element_Trl SET Name='Distribution ab - offen 📆', PrintName='Distribution ab - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:10:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579891 AND AD_Language='de_CH'
;

-- 2022-09-16T11:10:33.191Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579891,'de_CH')
;

-- 2022-09-16T11:10:51.679Z
UPDATE AD_Element_Trl SET Name='Summe Abgänge - offen 📆', PrintName='Summe Abgänge - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:10:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579892 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:10:51.681Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579892,'nl_NL')
;

-- 2022-09-16T11:10:54.118Z
UPDATE AD_Element_Trl SET Name='Pending demands 📆', PrintName='Pending demands 📆',Updated=TO_TIMESTAMP('2022-09-16 14:10:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579892 AND AD_Language='en_US'
;

-- 2022-09-16T11:10:54.119Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579892,'en_US')
;

-- 2022-09-16T11:10:56.383Z
UPDATE AD_Element_Trl SET Name='Summe Abgänge - offen 📆', PrintName='Summe Abgänge - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579892 AND AD_Language='de_DE'
;

-- 2022-09-16T11:10:56.385Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579892,'de_DE')
;

-- 2022-09-16T11:10:56.393Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579892,'de_DE')
;

-- 2022-09-16T11:10:56.394Z
UPDATE AD_Column SET ColumnName='QtyDemandSum_AtDate', Name='Summe Abgänge - offen 📆', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='' WHERE AD_Element_ID=579892
;

-- 2022-09-16T11:10:56.395Z
UPDATE AD_Process_Para SET ColumnName='QtyDemandSum_AtDate', Name='Summe Abgänge - offen 📆', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='', AD_Element_ID=579892 WHERE UPPER(ColumnName)='QTYDEMANDSUM_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:10:56.397Z
UPDATE AD_Process_Para SET ColumnName='QtyDemandSum_AtDate', Name='Summe Abgänge - offen 📆', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='' WHERE AD_Element_ID=579892 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:10:56.398Z
UPDATE AD_Field SET Name='Summe Abgänge - offen 📆', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579892) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579892)
;

-- 2022-09-16T11:10:56.413Z
UPDATE AD_PrintFormatItem pi SET PrintName='Summe Abgänge - offen 📆', Name='Summe Abgänge - offen 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579892)
;

-- 2022-09-16T11:10:56.414Z
UPDATE AD_Tab SET Name='Summe Abgänge - offen 📆', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='', CommitWarning = NULL WHERE AD_Element_ID = 579892
;

-- 2022-09-16T11:10:56.416Z
UPDATE AD_WINDOW SET Name='Summe Abgänge - offen 📆', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='' WHERE AD_Element_ID = 579892
;

-- 2022-09-16T11:10:56.416Z
UPDATE AD_Menu SET   Name = 'Summe Abgänge - offen 📆', Description = 'Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579892
;

-- 2022-09-16T11:10:58.993Z
UPDATE AD_Element_Trl SET Name='Summe Abgänge - offen 📆', PrintName='Summe Abgänge - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:10:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579892 AND AD_Language='de_CH'
;

-- 2022-09-16T11:10:58.995Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579892,'de_CH')
;

-- 2022-09-16T11:11:15.581Z
UPDATE AD_Element_Trl SET Name='Produktionsempfang - offen 📆', PrintName='Produktionsempfang - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:11:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579894 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:11:15.582Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579894,'nl_NL')
;

-- 2022-09-16T11:11:18.111Z
UPDATE AD_Element_Trl SET Name='Pending manufacturing receipt 📆', PrintName='Pending manufacturing receipt 📆',Updated=TO_TIMESTAMP('2022-09-16 14:11:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579894 AND AD_Language='en_US'
;

-- 2022-09-16T11:11:18.112Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579894,'en_US')
;

-- 2022-09-16T11:11:20.430Z
UPDATE AD_Element_Trl SET Name='Produktionsempfang - offen 📆', PrintName='Produktionsempfang - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:11:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579894 AND AD_Language='de_DE'
;

-- 2022-09-16T11:11:20.432Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579894,'de_DE')
;

-- 2022-09-16T11:11:20.442Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579894,'de_DE')
;

-- 2022-09-16T11:11:20.443Z
UPDATE AD_Column SET ColumnName='QtySupply_PP_Order_AtDate', Name='Produktionsempfang - offen 📆', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579894
;

-- 2022-09-16T11:11:20.445Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PP_Order_AtDate', Name='Produktionsempfang - offen 📆', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL, AD_Element_ID=579894 WHERE UPPER(ColumnName)='QTYSUPPLY_PP_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:11:20.447Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PP_Order_AtDate', Name='Produktionsempfang - offen 📆', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579894 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:11:20.447Z
UPDATE AD_Field SET Name='Produktionsempfang - offen 📆', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579894) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579894)
;

-- 2022-09-16T11:11:20.471Z
UPDATE AD_PrintFormatItem pi SET PrintName='Produktionsempfang - offen 📆', Name='Produktionsempfang - offen 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579894)
;

-- 2022-09-16T11:11:20.473Z
UPDATE AD_Tab SET Name='Produktionsempfang - offen 📆', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579894
;

-- 2022-09-16T11:11:20.474Z
UPDATE AD_WINDOW SET Name='Produktionsempfang - offen 📆', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID = 579894
;

-- 2022-09-16T11:11:20.476Z
UPDATE AD_Menu SET   Name = 'Produktionsempfang - offen 📆', Description = 'Noch zu empfangende Menge eines Produktionsauftrags.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579894
;

-- 2022-09-16T11:11:22.703Z
UPDATE AD_Element_Trl SET Name='Produktionsempfang - offen 📆', PrintName='Produktionsempfang - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579894 AND AD_Language='de_CH'
;

-- 2022-09-16T11:11:22.705Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579894,'de_CH')
;

-- 2022-09-16T11:11:40.033Z
UPDATE AD_Element_Trl SET Name='Bestellt - offen 📆', PrintName='Bestellt - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579895 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:11:40.035Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579895,'nl_NL')
;

-- 2022-09-16T11:11:44.025Z
UPDATE AD_Element_Trl SET Name='Purchased - pending 📆', PrintName='Purchased - pending 📆',Updated=TO_TIMESTAMP('2022-09-16 14:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579895 AND AD_Language='en_US'
;

-- 2022-09-16T11:11:44.027Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579895,'en_US')
;

-- 2022-09-16T11:11:46.463Z
UPDATE AD_Element_Trl SET Name='Bestellt - offen 📆', PrintName='Bestellt - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:11:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579895 AND AD_Language='de_DE'
;

-- 2022-09-16T11:11:46.465Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579895,'de_DE')
;

-- 2022-09-16T11:11:46.472Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579895,'de_DE')
;

-- 2022-09-16T11:11:46.474Z
UPDATE AD_Column SET ColumnName='QtySupply_PurchaseOrder_AtDate', Name='Bestellt - offen 📆', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579895
;

-- 2022-09-16T11:11:46.475Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PurchaseOrder_AtDate', Name='Bestellt - offen 📆', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL, AD_Element_ID=579895 WHERE UPPER(ColumnName)='QTYSUPPLY_PURCHASEORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:11:46.476Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PurchaseOrder_AtDate', Name='Bestellt - offen 📆', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579895 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:11:46.476Z
UPDATE AD_Field SET Name='Bestellt - offen 📆', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579895) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579895)
;

-- 2022-09-16T11:11:46.493Z
UPDATE AD_PrintFormatItem pi SET PrintName='Bestellt - offen 📆', Name='Bestellt - offen 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579895)
;

-- 2022-09-16T11:11:46.494Z
UPDATE AD_Tab SET Name='Bestellt - offen 📆', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579895
;

-- 2022-09-16T11:11:46.495Z
UPDATE AD_WINDOW SET Name='Bestellt - offen 📆', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID = 579895
;

-- 2022-09-16T11:11:46.496Z
UPDATE AD_Menu SET   Name = 'Bestellt - offen 📆', Description = 'Noch nicht empfangene Bestellmenge für das jeweilige Datum.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579895
;

-- 2022-09-16T11:11:49.025Z
UPDATE AD_Element_Trl SET Name='Bestellt - offen 📆', PrintName='Bestellt - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579895 AND AD_Language='de_CH'
;

-- 2022-09-16T11:11:49.026Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579895,'de_CH')
;

-- 2022-09-16T11:12:07.197Z
UPDATE AD_Element_Trl SET Name='Distribution an - offen 📆', PrintName='Distribution an - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579896 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:12:07.198Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579896,'nl_NL')
;

-- 2022-09-16T11:12:09.774Z
UPDATE AD_Element_Trl SET Name='Pending distribution target 📆', PrintName='Pending distribution target 📆',Updated=TO_TIMESTAMP('2022-09-16 14:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579896 AND AD_Language='en_US'
;

-- 2022-09-16T11:12:09.775Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579896,'en_US')
;

-- 2022-09-16T11:12:12.776Z
UPDATE AD_Element_Trl SET Name='Distribution an - offen 📆', PrintName='Distribution an - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:12:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579896 AND AD_Language='de_DE'
;

-- 2022-09-16T11:12:12.778Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579896,'de_DE')
;

-- 2022-09-16T11:12:12.786Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579896,'de_DE')
;

-- 2022-09-16T11:12:12.787Z
UPDATE AD_Column SET ColumnName='QtySupply_DD_Order_AtDate', Name='Distribution an - offen 📆', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579896
;

-- 2022-09-16T11:12:12.788Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_DD_Order_AtDate', Name='Distribution an - offen 📆', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL, AD_Element_ID=579896 WHERE UPPER(ColumnName)='QTYSUPPLY_DD_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:12:12.790Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_DD_Order_AtDate', Name='Distribution an - offen 📆', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579896 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:12:12.790Z
UPDATE AD_Field SET Name='Distribution an - offen 📆', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579896) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579896)
;

-- 2022-09-16T11:12:12.803Z
UPDATE AD_PrintFormatItem pi SET PrintName='Distribution an - offen 📆', Name='Distribution an - offen 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579896)
;

-- 2022-09-16T11:12:12.804Z
UPDATE AD_Tab SET Name='Distribution an - offen 📆', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579896
;

-- 2022-09-16T11:12:12.805Z
UPDATE AD_WINDOW SET Name='Distribution an - offen 📆', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID = 579896
;

-- 2022-09-16T11:12:12.806Z
UPDATE AD_Menu SET   Name = 'Distribution an - offen 📆', Description = 'Noch her zu bewegende Menge eines Distributionsauftrags.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579896
;

-- 2022-09-16T11:12:15.449Z
UPDATE AD_Element_Trl SET Name='Distribution an - offen 📆', PrintName='Distribution an - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579896 AND AD_Language='de_CH'
;

-- 2022-09-16T11:12:15.451Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579896,'de_CH')
;

-- 2022-09-16T11:12:30.133Z
UPDATE AD_Element_Trl SET Name='Summe Zugänge - offen 📆', PrintName='Summe Zugänge - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:12:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579897 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:12:30.135Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579897,'nl_NL')
;

-- 2022-09-16T11:12:32.493Z
UPDATE AD_Element_Trl SET Name='Pending supplies 📆', PrintName='Pending supplies 📆',Updated=TO_TIMESTAMP('2022-09-16 14:12:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579897 AND AD_Language='en_US'
;

-- 2022-09-16T11:12:32.494Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579897,'en_US')
;

-- 2022-09-16T11:12:35.341Z
UPDATE AD_Element_Trl SET Name='Summe Zugänge - offen 📆', PrintName='Summe Zugänge - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:12:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579897 AND AD_Language='de_DE'
;

-- 2022-09-16T11:12:35.342Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579897,'de_DE')
;

-- 2022-09-16T11:12:35.351Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579897,'de_DE')
;

-- 2022-09-16T11:12:35.352Z
UPDATE AD_Column SET ColumnName='QtySupplySum_AtDate', Name='Summe Zugänge - offen 📆', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL WHERE AD_Element_ID=579897
;

-- 2022-09-16T11:12:35.353Z
UPDATE AD_Process_Para SET ColumnName='QtySupplySum_AtDate', Name='Summe Zugänge - offen 📆', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL, AD_Element_ID=579897 WHERE UPPER(ColumnName)='QTYSUPPLYSUM_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:12:35.355Z
UPDATE AD_Process_Para SET ColumnName='QtySupplySum_AtDate', Name='Summe Zugänge - offen 📆', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL WHERE AD_Element_ID=579897 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:12:35.355Z
UPDATE AD_Field SET Name='Summe Zugänge - offen 📆', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579897) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579897)
;

-- 2022-09-16T11:12:35.369Z
UPDATE AD_PrintFormatItem pi SET PrintName='Summe Zugänge - offen 📆', Name='Summe Zugänge - offen 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579897)
;

-- 2022-09-16T11:12:35.370Z
UPDATE AD_Tab SET Name='Summe Zugänge - offen 📆', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579897
;

-- 2022-09-16T11:12:35.371Z
UPDATE AD_WINDOW SET Name='Summe Zugänge - offen 📆', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL WHERE AD_Element_ID = 579897
;

-- 2022-09-16T11:12:35.372Z
UPDATE AD_Menu SET   Name = 'Summe Zugänge - offen 📆', Description = 'Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579897
;

-- 2022-09-16T11:12:40.039Z
UPDATE AD_Element_Trl SET Name='Summe Zugänge - offen 📆', PrintName='Summe Zugänge - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579897 AND AD_Language='de_CH'
;

-- 2022-09-16T11:12:40.041Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579897,'de_CH')
;

-- 2022-09-16T11:12:56.325Z
UPDATE AD_Element_Trl SET Name='Required supplies 📆', PrintName='Required supplies 📆',Updated=TO_TIMESTAMP('2022-09-16 14:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:12:56.327Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'nl_NL')
;

-- 2022-09-16T11:12:59.193Z
UPDATE AD_Element_Trl SET Name='Required supplies 📆', PrintName='Required supplies 📆',Updated=TO_TIMESTAMP('2022-09-16 14:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='en_US'
;

-- 2022-09-16T11:12:59.195Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'en_US')
;

-- 2022-09-16T11:13:01.686Z
UPDATE AD_Element_Trl SET Name='Bedarfssumme 📆', PrintName='Bedarfssumme 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='de_DE'
;

-- 2022-09-16T11:13:01.687Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'de_DE')
;

-- 2022-09-16T11:13:01.691Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579898,'de_DE')
;

-- 2022-09-16T11:13:01.692Z
UPDATE AD_Column SET ColumnName='QtySupplyRequired_AtDate', Name='Bedarfssumme 📆', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID=579898
;

-- 2022-09-16T11:13:01.692Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyRequired_AtDate', Name='Bedarfssumme 📆', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL, AD_Element_ID=579898 WHERE UPPER(ColumnName)='QTYSUPPLYREQUIRED_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:13:01.693Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyRequired_AtDate', Name='Bedarfssumme 📆', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID=579898 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:13:01.693Z
UPDATE AD_Field SET Name='Bedarfssumme 📆', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579898) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579898)
;

-- 2022-09-16T11:13:01.705Z
UPDATE AD_PrintFormatItem pi SET PrintName='Bedarfssumme 📆', Name='Bedarfssumme 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579898)
;

-- 2022-09-16T11:13:01.706Z
UPDATE AD_Tab SET Name='Bedarfssumme 📆', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579898
;

-- 2022-09-16T11:13:01.707Z
UPDATE AD_WINDOW SET Name='Bedarfssumme 📆', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID = 579898
;

-- 2022-09-16T11:13:01.707Z
UPDATE AD_Menu SET   Name = 'Bedarfssumme 📆', Description = 'Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579898
;

-- 2022-09-16T11:13:04.173Z
UPDATE AD_Element_Trl SET Name='Bedarfssumme 📆', PrintName='Bedarfssumme 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='de_CH'
;

-- 2022-09-16T11:13:04.174Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'de_CH')
;

-- 2022-09-16T11:13:18.918Z
UPDATE AD_Element_Trl SET Name='To schedule supplies 📆', PrintName='To schedule supplies 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:13:18.919Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'nl_NL')
;

-- 2022-09-16T11:13:21.359Z
UPDATE AD_Element_Trl SET Name='Open requriements 📆', PrintName='Open requriements 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='en_US'
;

-- 2022-09-16T11:13:21.360Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'en_US')
;

-- 2022-09-16T11:13:23.737Z
UPDATE AD_Element_Trl SET Name='Zu disp. Bedarf 📆', PrintName='Zu disp. Bedarf 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='de_DE'
;

-- 2022-09-16T11:13:23.739Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'de_DE')
;

-- 2022-09-16T11:13:23.749Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579899,'de_DE')
;

-- 2022-09-16T11:13:23.750Z
UPDATE AD_Column SET ColumnName='QtySupplyToSchedule_AtDate', Name='Zu disp. Bedarf 📆', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID=579899
;

-- 2022-09-16T11:13:23.752Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule_AtDate', Name='Zu disp. Bedarf 📆', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL, AD_Element_ID=579899 WHERE UPPER(ColumnName)='QTYSUPPLYTOSCHEDULE_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:13:23.753Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule_AtDate', Name='Zu disp. Bedarf 📆', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID=579899 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:13:23.753Z
UPDATE AD_Field SET Name='Zu disp. Bedarf 📆', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579899) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579899)
;

-- 2022-09-16T11:13:23.763Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zu disp. Bedarf 📆', Name='Zu disp. Bedarf 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579899)
;

-- 2022-09-16T11:13:23.764Z
UPDATE AD_Tab SET Name='Zu disp. Bedarf 📆', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579899
;

-- 2022-09-16T11:13:23.765Z
UPDATE AD_WINDOW SET Name='Zu disp. Bedarf 📆', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID = 579899
;

-- 2022-09-16T11:13:23.766Z
UPDATE AD_Menu SET   Name = 'Zu disp. Bedarf 📆', Description = 'Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579899
;

-- 2022-09-16T11:13:26.177Z
UPDATE AD_Element_Trl SET Name='Zu disp. Bedarf 📆', PrintName='Zu disp. Bedarf 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='de_CH'
;

-- 2022-09-16T11:13:26.179Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'de_CH')
;

-- 2022-09-16T11:13:45.678Z
UPDATE AD_Element_Trl SET Name='Materialentnahme 📆', PrintName='Materialentnahme 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='de_CH'
;

-- 2022-09-16T11:13:45.679Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'de_CH')
;

-- 2022-09-16T11:13:48.061Z
UPDATE AD_Element_Trl SET Name='Internal Usage 📆', PrintName='Internal Usage 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='en_US'
;

-- 2022-09-16T11:13:48.063Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'en_US')
;

-- 2022-09-16T11:13:50.382Z
UPDATE AD_Element_Trl SET Name='Materialentnahme 📆', PrintName='Materialentnahme 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:13:50.384Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'nl_NL')
;

-- 2022-09-16T11:13:53.088Z
UPDATE AD_Element_Trl SET Name='Materialentnahme 📆', PrintName='Materialentnahme 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='de_DE'
;

-- 2022-09-16T11:13:53.090Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'de_DE')
;

-- 2022-09-16T11:13:53.097Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(542653,'de_DE')
;

-- 2022-09-16T11:13:53.098Z
UPDATE AD_Column SET ColumnName='QtyMaterialentnahme_AtDate', Name='Materialentnahme 📆', Description=NULL, Help=NULL WHERE AD_Element_ID=542653
;

-- 2022-09-16T11:13:53.099Z
UPDATE AD_Process_Para SET ColumnName='QtyMaterialentnahme_AtDate', Name='Materialentnahme 📆', Description=NULL, Help=NULL, AD_Element_ID=542653 WHERE UPPER(ColumnName)='QTYMATERIALENTNAHME_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:13:53.100Z
UPDATE AD_Process_Para SET ColumnName='QtyMaterialentnahme_AtDate', Name='Materialentnahme 📆', Description=NULL, Help=NULL WHERE AD_Element_ID=542653 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:13:53.100Z
UPDATE AD_Field SET Name='Materialentnahme 📆', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542653) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542653)
;

-- 2022-09-16T11:13:53.111Z
UPDATE AD_PrintFormatItem pi SET PrintName='Materialentnahme 📆', Name='Materialentnahme 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542653)
;

-- 2022-09-16T11:13:53.112Z
UPDATE AD_Tab SET Name='Materialentnahme 📆', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542653
;

-- 2022-09-16T11:13:53.113Z
UPDATE AD_WINDOW SET Name='Materialentnahme 📆', Description=NULL, Help=NULL WHERE AD_Element_ID = 542653
;

-- 2022-09-16T11:13:53.114Z
UPDATE AD_Menu SET   Name = 'Materialentnahme 📆', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542653
;

-- 2022-09-16T11:13:57.031Z
UPDATE AD_Element_Trl SET Name='Materialentnahme 📆', PrintName='Materialentnahme 📆',Updated=TO_TIMESTAMP('2022-09-16 14:13:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='en_GB'
;

-- 2022-09-16T11:13:57.032Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'en_GB')
;

-- 2022-09-16T11:14:00.151Z
UPDATE AD_Element_Trl SET Name='Materialentnahme 📆', PrintName='Materialentnahme 📆',Updated=TO_TIMESTAMP('2022-09-16 14:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='it_CH'
;

-- 2022-09-16T11:14:00.153Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'it_CH')
;

-- 2022-09-16T11:14:02.703Z
UPDATE AD_Element_Trl SET Name='Materialentnahme 📆', PrintName='Materialentnahme 📆',Updated=TO_TIMESTAMP('2022-09-16 14:14:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='fr_CH'
;

-- 2022-09-16T11:14:02.704Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'fr_CH')
;

-- 2022-09-16T11:14:18.237Z
UPDATE AD_Element_Trl SET Name='Produktionszuteilung - offen 📆', PrintName='Produktionszuteilung - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:14:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579890 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:14:18.238Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579890,'nl_NL')
;

-- 2022-09-16T11:14:20.564Z
UPDATE AD_Element_Trl SET Name='Manufacturing issue - pending 📆', PrintName='Manufacturing issue - pending 📆',Updated=TO_TIMESTAMP('2022-09-16 14:14:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579890 AND AD_Language='en_US'
;

-- 2022-09-16T11:14:20.565Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579890,'en_US')
;

-- 2022-09-16T11:14:22.782Z
UPDATE AD_Element_Trl SET Name='Produktionszuteilung - offen 📆', PrintName='Produktionszuteilung - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:14:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579890 AND AD_Language='de_DE'
;

-- 2022-09-16T11:14:22.783Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579890,'de_DE')
;

-- 2022-09-16T11:14:22.800Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579890,'de_DE')
;

-- 2022-09-16T11:14:22.801Z
UPDATE AD_Column SET ColumnName='QtyDemand_PP_Order_AtDate', Name='Produktionszuteilung - offen 📆', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579890
;

-- 2022-09-16T11:14:22.802Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_PP_Order_AtDate', Name='Produktionszuteilung - offen 📆', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL, AD_Element_ID=579890 WHERE UPPER(ColumnName)='QTYDEMAND_PP_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:14:22.804Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_PP_Order_AtDate', Name='Produktionszuteilung - offen 📆', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579890 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:14:22.804Z
UPDATE AD_Field SET Name='Produktionszuteilung - offen 📆', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579890) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579890)
;

-- 2022-09-16T11:14:22.834Z
UPDATE AD_PrintFormatItem pi SET PrintName='Produktionszuteilung - offen 📆', Name='Produktionszuteilung - offen 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579890)
;

-- 2022-09-16T11:14:22.835Z
UPDATE AD_Tab SET Name='Produktionszuteilung - offen 📆', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579890
;

-- 2022-09-16T11:14:22.837Z
UPDATE AD_WINDOW SET Name='Produktionszuteilung - offen 📆', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID = 579890
;

-- 2022-09-16T11:14:22.838Z
UPDATE AD_Menu SET   Name = 'Produktionszuteilung - offen 📆', Description = 'Noch zuzuteilende Menge eines Produktionsauftrags.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579890
;

-- 2022-09-16T11:14:25.302Z
UPDATE AD_Element_Trl SET Name='Produktionszuteilung - offen 📆', PrintName='Produktionszuteilung - offen 📆',Updated=TO_TIMESTAMP('2022-09-16 14:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579890 AND AD_Language='de_CH'
;

-- 2022-09-16T11:14:25.303Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579890,'de_CH')
;

-- 2022-09-16T11:14:43.376Z
UPDATE AD_Element_Trl SET Name='Bestand 📆', PrintName='Bestand 📆',Updated=TO_TIMESTAMP('2022-09-16 14:14:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579905 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:14:43.378Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579905,'nl_NL')
;

-- 2022-09-16T11:14:46.575Z
UPDATE AD_Element_Trl SET Name='Stock 📆', PrintName='Stock 📆',Updated=TO_TIMESTAMP('2022-09-16 14:14:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579905 AND AD_Language='en_US'
;

-- 2022-09-16T11:14:46.576Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579905,'en_US')
;

-- 2022-09-16T11:14:49.125Z
UPDATE AD_Element_Trl SET Name='Bestand 📆', PrintName='Bestand 📆',Updated=TO_TIMESTAMP('2022-09-16 14:14:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579905 AND AD_Language='de_DE'
;

-- 2022-09-16T11:14:49.126Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579905,'de_DE')
;

-- 2022-09-16T11:14:49.132Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579905,'de_DE')
;

-- 2022-09-16T11:14:49.132Z
UPDATE AD_Column SET ColumnName='QtyStockCurrent_AtDate', Name='Bestand 📆', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL WHERE AD_Element_ID=579905
;

-- 2022-09-16T11:14:49.133Z
UPDATE AD_Process_Para SET ColumnName='QtyStockCurrent_AtDate', Name='Bestand 📆', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL, AD_Element_ID=579905 WHERE UPPER(ColumnName)='QTYSTOCKCURRENT_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:14:49.134Z
UPDATE AD_Process_Para SET ColumnName='QtyStockCurrent_AtDate', Name='Bestand 📆', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL WHERE AD_Element_ID=579905 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:14:49.134Z
UPDATE AD_Field SET Name='Bestand 📆', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579905) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579905)
;

-- 2022-09-16T11:14:49.146Z
UPDATE AD_PrintFormatItem pi SET PrintName='Bestand 📆', Name='Bestand 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579905)
;

-- 2022-09-16T11:14:49.147Z
UPDATE AD_Tab SET Name='Bestand 📆', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579905
;

-- 2022-09-16T11:14:49.149Z
UPDATE AD_WINDOW SET Name='Bestand 📆', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL WHERE AD_Element_ID = 579905
;

-- 2022-09-16T11:14:49.150Z
UPDATE AD_Menu SET   Name = 'Bestand 📆', Description = 'Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579905
;

-- 2022-09-16T11:14:51.557Z
UPDATE AD_Element_Trl SET Name='Planbestand 📆', PrintName='Planbestand 📆',Updated=TO_TIMESTAMP('2022-09-16 14:14:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579905 AND AD_Language='de_CH'
;

-- 2022-09-16T11:14:51.558Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579905,'de_CH')
;

-- 2022-09-16T11:15:05.604Z
UPDATE AD_Element_Trl SET Name='Zählbestand 📆', PrintName='Zählbestand 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:15:05.605Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'nl_NL')
;

-- 2022-09-16T11:15:08.462Z
UPDATE AD_Element_Trl SET Name='Stock count 📆', PrintName='Stock count 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='en_US'
;

-- 2022-09-16T11:15:08.464Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'en_US')
;

-- 2022-09-16T11:15:11.224Z
UPDATE AD_Element_Trl SET Name='Zählbestand 📆', PrintName='Zählbestand 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='de_DE'
;

-- 2022-09-16T11:15:11.225Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'de_DE')
;

-- 2022-09-16T11:15:11.233Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579900,'de_DE')
;

-- 2022-09-16T11:15:11.234Z
UPDATE AD_Column SET ColumnName='QtyStockEstimateCount_AtDate', Name='Zählbestand 📆', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID=579900
;

-- 2022-09-16T11:15:11.235Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateCount_AtDate', Name='Zählbestand 📆', Description='Menge laut "grober" Zählung.', Help=NULL, AD_Element_ID=579900 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATECOUNT_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:15:11.236Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateCount_AtDate', Name='Zählbestand 📆', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID=579900 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:15:11.236Z
UPDATE AD_Field SET Name='Zählbestand 📆', Description='Menge laut "grober" Zählung.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579900) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579900)
;

-- 2022-09-16T11:15:11.246Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zählbestand 📆', Name='Zählbestand 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579900)
;

-- 2022-09-16T11:15:11.247Z
UPDATE AD_Tab SET Name='Zählbestand 📆', Description='Menge laut "grober" Zählung.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579900
;

-- 2022-09-16T11:15:11.248Z
UPDATE AD_WINDOW SET Name='Zählbestand 📆', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID = 579900
;

-- 2022-09-16T11:15:11.249Z
UPDATE AD_Menu SET   Name = 'Zählbestand 📆', Description = 'Menge laut "grober" Zählung.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579900
;

-- 2022-09-16T11:15:13.744Z
UPDATE AD_Element_Trl SET Name='Zählbestand 📆', PrintName='Zählbestand 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='de_CH'
;

-- 2022-09-16T11:15:13.745Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'de_CH')
;

-- 2022-09-16T11:15:31.086Z
UPDATE AD_Element_Trl SET Name='Zeitpunkt der Zählung 📆', PrintName='Zeitpunkt der Zählung 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902 AND AD_Language='de_CH'
;

-- 2022-09-16T11:15:31.087Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579902,'de_CH')
;

-- 2022-09-16T11:15:33.488Z
UPDATE AD_Element_Trl SET Name='Zeitpunkt der Zählung 📆', PrintName='Zeitpunkt der Zählung 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902 AND AD_Language='de_DE'
;

-- 2022-09-16T11:15:33.489Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579902,'de_DE')
;

-- 2022-09-16T11:15:33.499Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579902,'de_DE')
;

-- 2022-09-16T11:15:33.500Z
UPDATE AD_Column SET ColumnName='QtyStockEstimateTime_AtDate', Name='Zeitpunkt der Zählung 📆', Description=NULL, Help=NULL WHERE AD_Element_ID=579902
;

-- 2022-09-16T11:15:33.501Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateTime_AtDate', Name='Zeitpunkt der Zählung 📆', Description=NULL, Help=NULL, AD_Element_ID=579902 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATETIME_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:15:33.502Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateTime_AtDate', Name='Zeitpunkt der Zählung 📆', Description=NULL, Help=NULL WHERE AD_Element_ID=579902 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:15:33.503Z
UPDATE AD_Field SET Name='Zeitpunkt der Zählung 📆', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579902) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579902)
;

-- 2022-09-16T11:15:33.535Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zeitpunkt der Zählung 📆', Name='Zeitpunkt der Zählung 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579902)
;

-- 2022-09-16T11:15:33.536Z
UPDATE AD_Tab SET Name='Zeitpunkt der Zählung 📆', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579902
;

-- 2022-09-16T11:15:33.538Z
UPDATE AD_WINDOW SET Name='Zeitpunkt der Zählung 📆', Description=NULL, Help=NULL WHERE AD_Element_ID = 579902
;

-- 2022-09-16T11:15:33.540Z
UPDATE AD_Menu SET   Name = 'Zeitpunkt der Zählung 📆', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579902
;

-- 2022-09-16T11:15:36.543Z
UPDATE AD_Element_Trl SET Name='Stock estimate time 📆', PrintName='Stock estimate time 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902 AND AD_Language='en_US'
;

-- 2022-09-16T11:15:36.544Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579902,'en_US')
;

-- 2022-09-16T11:15:38.848Z
UPDATE AD_Element_Trl SET Name='Stock estimate time 📆', PrintName='Stock estimate time 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:15:38.849Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579902,'nl_NL')
;

-- 2022-09-16T11:15:53.928Z
UPDATE AD_Element_Trl SET Name='Inventurbestand 📆', PrintName='Inventurbestand 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='de_CH'
;

-- 2022-09-16T11:15:53.930Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'de_CH')
;

-- 2022-09-16T11:15:56.485Z
UPDATE AD_Element_Trl SET Name='Inventurbestand 📆', PrintName='Inventurbestand 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='de_DE'
;

-- 2022-09-16T11:15:56.486Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'de_DE')
;

-- 2022-09-16T11:15:56.490Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579903,'de_DE')
;

-- 2022-09-16T11:15:56.490Z
UPDATE AD_Column SET ColumnName='QtyInventoryCount_AtDate', Name='Inventurbestand 📆', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID=579903
;

-- 2022-09-16T11:15:56.491Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryCount_AtDate', Name='Inventurbestand 📆', Description='Bestand laut der letzten Inventur', Help=NULL, AD_Element_ID=579903 WHERE UPPER(ColumnName)='QTYINVENTORYCOUNT_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:15:56.492Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryCount_AtDate', Name='Inventurbestand 📆', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID=579903 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:15:56.492Z
UPDATE AD_Field SET Name='Inventurbestand 📆', Description='Bestand laut der letzten Inventur', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579903) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579903)
;

-- 2022-09-16T11:15:56.502Z
UPDATE AD_PrintFormatItem pi SET PrintName='Inventurbestand 📆', Name='Inventurbestand 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579903)
;

-- 2022-09-16T11:15:56.503Z
UPDATE AD_Tab SET Name='Inventurbestand 📆', Description='Bestand laut der letzten Inventur', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579903
;

-- 2022-09-16T11:15:56.504Z
UPDATE AD_WINDOW SET Name='Inventurbestand 📆', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID = 579903
;

-- 2022-09-16T11:15:56.505Z
UPDATE AD_Menu SET   Name = 'Inventurbestand 📆', Description = 'Bestand laut der letzten Inventur', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579903
;

-- 2022-09-16T11:15:59.068Z
UPDATE AD_Element_Trl SET Name='Inventory count 📆', PrintName='Inventory count 📆',Updated=TO_TIMESTAMP('2022-09-16 14:15:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='en_US'
;

-- 2022-09-16T11:15:59.069Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'en_US')
;

-- 2022-09-16T11:16:01.220Z
UPDATE AD_Element_Trl SET Name='Inventory count 📆', PrintName='Inventory count 📆',Updated=TO_TIMESTAMP('2022-09-16 14:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:16:01.221Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'nl_NL')
;

-- 2022-09-16T11:16:14.478Z
UPDATE AD_Element_Trl SET Name='Inventur-Zeit 📆', PrintName='Inventur-Zeit 📆',Updated=TO_TIMESTAMP('2022-09-16 14:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='de_CH'
;

-- 2022-09-16T11:16:14.479Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'de_CH')
;

-- 2022-09-16T11:16:16.718Z
UPDATE AD_Element_Trl SET Name='Inventur-Zeit 📆', PrintName='Inventur-Zeit 📆',Updated=TO_TIMESTAMP('2022-09-16 14:16:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='de_DE'
;

-- 2022-09-16T11:16:16.719Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'de_DE')
;

-- 2022-09-16T11:16:16.725Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579904,'de_DE')
;

-- 2022-09-16T11:16:16.725Z
UPDATE AD_Column SET ColumnName='QtyInventoryTime_AtDate', Name='Inventur-Zeit 📆', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID=579904
;

-- 2022-09-16T11:16:16.726Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryTime_AtDate', Name='Inventur-Zeit 📆', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL, AD_Element_ID=579904 WHERE UPPER(ColumnName)='QTYINVENTORYTIME_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:16:16.727Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryTime_AtDate', Name='Inventur-Zeit 📆', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID=579904 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:16:16.727Z
UPDATE AD_Field SET Name='Inventur-Zeit 📆', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579904) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579904)
;

-- 2022-09-16T11:16:16.738Z
UPDATE AD_PrintFormatItem pi SET PrintName='Inventur-Zeit 📆', Name='Inventur-Zeit 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579904)
;

-- 2022-09-16T11:16:16.739Z
UPDATE AD_Tab SET Name='Inventur-Zeit 📆', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579904
;

-- 2022-09-16T11:16:16.740Z
UPDATE AD_WINDOW SET Name='Inventur-Zeit 📆', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID = 579904
;

-- 2022-09-16T11:16:16.741Z
UPDATE AD_Menu SET   Name = 'Inventur-Zeit 📆', Description = 'Zeipunkt, an dem die Inventur fertig gestellt wurde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579904
;

-- 2022-09-16T11:16:19.336Z
UPDATE AD_Element_Trl SET Name='Inventory time 📆', PrintName='Inventory time 📆',Updated=TO_TIMESTAMP('2022-09-16 14:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='en_US'
;

-- 2022-09-16T11:16:19.338Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'en_US')
;

-- 2022-09-16T11:16:22.182Z
UPDATE AD_Element_Trl SET Name='Inventory time 📆', PrintName='Inventory time 📆',Updated=TO_TIMESTAMP('2022-09-16 14:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:16:22.183Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'nl_NL')
;

-- 2022-09-16T11:16:38.380Z
UPDATE AD_Element_Trl SET Name='Erw. Überschuss 📆', PrintName='Erw. Überschuss 📆',Updated=TO_TIMESTAMP('2022-09-16 14:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579906 AND AD_Language='de_CH'
;

-- 2022-09-16T11:16:38.381Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579906,'de_CH')
;

-- 2022-09-16T11:16:40.933Z
UPDATE AD_Element_Trl SET Name='Erw. Überschuss 📆', PrintName='Erw. Überschuss 📆',Updated=TO_TIMESTAMP('2022-09-16 14:16:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579906 AND AD_Language='de_DE'
;

-- 2022-09-16T11:16:40.933Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579906,'de_DE')
;

-- 2022-09-16T11:16:40.939Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579906,'de_DE')
;

-- 2022-09-16T11:16:40.940Z
UPDATE AD_Column SET ColumnName='QtyExpectedSurplus_AtDate', Name='Erw. Überschuss 📆', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL WHERE AD_Element_ID=579906
;

-- 2022-09-16T11:16:40.941Z
UPDATE AD_Process_Para SET ColumnName='QtyExpectedSurplus_AtDate', Name='Erw. Überschuss 📆', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL, AD_Element_ID=579906 WHERE UPPER(ColumnName)='QTYEXPECTEDSURPLUS_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T11:16:40.942Z
UPDATE AD_Process_Para SET ColumnName='QtyExpectedSurplus_AtDate', Name='Erw. Überschuss 📆', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL WHERE AD_Element_ID=579906 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T11:16:40.943Z
UPDATE AD_Field SET Name='Erw. Überschuss 📆', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579906) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579906)
;

-- 2022-09-16T11:16:40.959Z
UPDATE AD_PrintFormatItem pi SET PrintName='Erw. Überschuss 📆', Name='Erw. Überschuss 📆' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579906)
;

-- 2022-09-16T11:16:40.960Z
UPDATE AD_Tab SET Name='Erw. Überschuss 📆', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579906
;

-- 2022-09-16T11:16:40.961Z
UPDATE AD_WINDOW SET Name='Erw. Überschuss 📆', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL WHERE AD_Element_ID = 579906
;

-- 2022-09-16T11:16:40.962Z
UPDATE AD_Menu SET   Name = 'Erw. Überschuss 📆', Description = 'Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579906
;

-- 2022-09-16T11:16:43.141Z
UPDATE AD_Element_Trl SET Name='Expected surplus 📆', PrintName='Expected surplus 📆',Updated=TO_TIMESTAMP('2022-09-16 14:16:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579906 AND AD_Language='en_US'
;

-- 2022-09-16T11:16:43.142Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579906,'en_US')
;

-- 2022-09-16T11:16:45.560Z
UPDATE AD_Element_Trl SET Name='Erw. Überschuss 📆', PrintName='Erw. Überschuss 📆',Updated=TO_TIMESTAMP('2022-09-16 14:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579906 AND AD_Language='nl_NL'
;

-- 2022-09-16T11:16:45.561Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579906,'nl_NL')
;

-- 2022-09-16T15:24:31.754Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyStockEstimateSeqNo_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:24:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541425
;

-- 2022-09-16T15:25:17.269Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyDemand_SalesOrder_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541400
;

-- 2022-09-16T15:25:30.708Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyDemand_DD_Order_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:25:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541402
;

-- 2022-09-16T15:25:43.492Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyDemandSum_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541403
;

-- 2022-09-16T15:25:54.463Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtySupply_PP_Order_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:25:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541404
;

-- 2022-09-16T15:26:04.694Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtySupply_PurchaseOrder_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:26:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541405
;

-- 2022-09-16T15:26:18.346Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtySupply_DD_Order_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:26:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541406
;

-- 2022-09-16T15:26:28.806Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtySupplySum_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:26:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541407
;

-- 2022-09-16T15:26:40.027Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtySupplyRequired_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541408
;

-- 2022-09-16T15:26:50.061Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtySupplyToSchedule_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:26:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541409
;

-- 2022-09-16T15:26:59.291Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyMaterialentnahme_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541221
;

-- 2022-09-16T15:27:08.957Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyDemand_PP_Order_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541401
;

-- 2022-09-16T15:27:18.826Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyStockCurrent_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:27:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541416
;

-- 2022-09-16T15:27:28.385Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyStockEstimateCount_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541412
;

-- 2022-09-16T15:27:38.206Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyStockEstimateTime_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:27:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541413
;

-- 2022-09-16T15:27:54.282Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyInventoryCount_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:27:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541414
;

-- 2022-09-16T15:28:03.549Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyInventoryTime_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541415
;

-- 2022-09-16T15:28:12.777Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.ui.web.material.cockpit.field.QtyExpectedSurplus_AtDate.IsDisplayed',Updated=TO_TIMESTAMP('2022-09-16 18:28:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541417
;

-- 2022-09-16T17:35:52.855Z
UPDATE AD_Element_Trl SET Name='📆 Zählbestand Reihenfolge', PrintName='📆 Zählbestand Reihenfolge',Updated=TO_TIMESTAMP('2022-09-16 20:35:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580208 AND AD_Language='de_CH'
;

-- 2022-09-16T17:35:52.895Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580208,'de_CH')
;

-- 2022-09-16T17:36:01.219Z
UPDATE AD_Element_Trl SET Name='📆 Zählbestand Reihenfolge', PrintName='📆 Zählbestand Reihenfolge',Updated=TO_TIMESTAMP('2022-09-16 20:36:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580208 AND AD_Language='de_DE'
;

-- 2022-09-16T17:36:01.221Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580208,'de_DE')
;

-- 2022-09-16T17:36:01.242Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580208,'de_DE')
;

-- 2022-09-16T17:36:01.244Z
UPDATE AD_Column SET ColumnName='QtyStockEstimateSeqNo_AtDate', Name='📆 Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE AD_Element_ID=580208
;

-- 2022-09-16T17:36:01.245Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateSeqNo_AtDate', Name='📆 Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL, AD_Element_ID=580208 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATESEQNO_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:36:01.247Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateSeqNo_AtDate', Name='📆 Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE AD_Element_ID=580208 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:36:01.247Z
UPDATE AD_Field SET Name='📆 Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580208) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580208)
;

-- 2022-09-16T17:36:01.265Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Zählbestand Reihenfolge', Name='📆 Zählbestand Reihenfolge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580208)
;

-- 2022-09-16T17:36:01.266Z
UPDATE AD_Tab SET Name='📆 Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580208
;

-- 2022-09-16T17:36:01.268Z
UPDATE AD_WINDOW SET Name='📆 Zählbestand Reihenfolge', Description='"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', Help=NULL WHERE AD_Element_ID = 580208
;

-- 2022-09-16T17:36:01.269Z
UPDATE AD_Menu SET   Name = '📆 Zählbestand Reihenfolge', Description = '"Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580208
;

-- 2022-09-16T17:36:08.069Z
UPDATE AD_Element_Trl SET Name='📆 Zählbestand Reihenfolge', PrintName='📆 Zählbestand Reihenfolge',Updated=TO_TIMESTAMP('2022-09-16 20:36:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580208 AND AD_Language='en_US'
;

-- 2022-09-16T17:36:08.070Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580208,'en_US')
;

-- 2022-09-16T17:36:16.126Z
UPDATE AD_Element_Trl SET Name='📆 Zählbestand Reihenfolge', PrintName='📆 Zählbestand Reihenfolge',Updated=TO_TIMESTAMP('2022-09-16 20:36:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580208 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:36:16.128Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580208,'nl_NL')
;

-- 2022-09-16T17:36:40.115Z
UPDATE AD_Element_Trl SET Name='📆 Zusage Lieferant', PrintName='📆 Zusage Lieferant',Updated=TO_TIMESTAMP('2022-09-16 20:36:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070 AND AD_Language='de_DE'
;

-- 2022-09-16T17:36:40.116Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543070,'de_DE')
;

-- 2022-09-16T17:36:40.122Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543070,'de_DE')
;

-- 2022-09-16T17:36:40.123Z
UPDATE AD_Column SET ColumnName='PMM_QtyPromised_OnDate_AtDate', Name='📆 Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID=543070
;

-- 2022-09-16T17:36:40.124Z
UPDATE AD_Process_Para SET ColumnName='PMM_QtyPromised_OnDate_AtDate', Name='📆 Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL, AD_Element_ID=543070 WHERE UPPER(ColumnName)='PMM_QTYPROMISED_ONDATE_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:36:40.124Z
UPDATE AD_Process_Para SET ColumnName='PMM_QtyPromised_OnDate_AtDate', Name='📆 Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID=543070 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:36:40.125Z
UPDATE AD_Field SET Name='📆 Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543070) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543070)
;

-- 2022-09-16T17:36:40.137Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Zusage Lieferant', Name='📆 Zusage Lieferant' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543070)
;

-- 2022-09-16T17:36:40.137Z
UPDATE AD_Tab SET Name='📆 Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543070
;

-- 2022-09-16T17:36:40.139Z
UPDATE AD_WINDOW SET Name='📆 Zusage Lieferant', Description='Vom Lieferanten per Webapplikation zugesagte Menge', Help=NULL WHERE AD_Element_ID = 543070
;

-- 2022-09-16T17:36:40.140Z
UPDATE AD_Menu SET   Name = '📆 Zusage Lieferant', Description = 'Vom Lieferanten per Webapplikation zugesagte Menge', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543070
;

-- 2022-09-16T17:36:48.174Z
UPDATE AD_Element_Trl SET Name='📆 Zusage Lieferant', PrintName='📆 Zusage Lieferant',Updated=TO_TIMESTAMP('2022-09-16 20:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:36:48.177Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543070,'nl_NL')
;

-- 2022-09-16T17:36:56.183Z
UPDATE AD_Element_Trl SET Name='📆 Vendor Promised', PrintName='📆 Vendor Promised',Updated=TO_TIMESTAMP('2022-09-16 20:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070 AND AD_Language='en_US'
;

-- 2022-09-16T17:36:56.185Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543070,'en_US')
;

-- 2022-09-16T17:37:02.631Z
UPDATE AD_Element_Trl SET Name='📆 Zusage Lieferant', PrintName='📆 Zusage Lieferant',Updated=TO_TIMESTAMP('2022-09-16 20:37:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543070 AND AD_Language='de_CH'
;

-- 2022-09-16T17:37:02.633Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543070,'de_CH')
;

-- 2022-09-16T17:37:26.375Z
UPDATE AD_Element_Trl SET Name='📆 Beauftragt - offen', PrintName='📆 Beauftragt - offen',Updated=TO_TIMESTAMP('2022-09-16 20:37:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579889 AND AD_Language='de_CH'
;

-- 2022-09-16T17:37:26.377Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579889,'de_CH')
;

-- 2022-09-16T17:37:32.967Z
UPDATE AD_Element_Trl SET Name='📆 Beauftragt - offen', PrintName='📆 Beauftragt - offen',Updated=TO_TIMESTAMP('2022-09-16 20:37:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579889 AND AD_Language='de_DE'
;

-- 2022-09-16T17:37:32.970Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579889,'de_DE')
;

-- 2022-09-16T17:37:32.978Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579889,'de_DE')
;

-- 2022-09-16T17:37:32.979Z
UPDATE AD_Column SET ColumnName='QtyDemand_SalesOrder_AtDate', Name='📆 Beauftragt - offen', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579889
;

-- 2022-09-16T17:37:32.979Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_SalesOrder_AtDate', Name='📆 Beauftragt - offen', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL, AD_Element_ID=579889 WHERE UPPER(ColumnName)='QTYDEMAND_SALESORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:37:32.980Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_SalesOrder_AtDate', Name='📆 Beauftragt - offen', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579889 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:37:32.980Z
UPDATE AD_Field SET Name='📆 Beauftragt - offen', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579889) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579889)
;

-- 2022-09-16T17:37:32.991Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Beauftragt - offen', Name='📆 Beauftragt - offen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579889)
;

-- 2022-09-16T17:37:32.991Z
UPDATE AD_Tab SET Name='📆 Beauftragt - offen', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579889
;

-- 2022-09-16T17:37:32.993Z
UPDATE AD_WINDOW SET Name='📆 Beauftragt - offen', Description='Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID = 579889
;

-- 2022-09-16T17:37:32.993Z
UPDATE AD_Menu SET   Name = '📆 Beauftragt - offen', Description = 'Noch nicht gelieferte Auftragsmenge für das jeweilige Datum.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579889
;

-- 2022-09-16T17:37:40.411Z
UPDATE AD_Element_Trl SET Name='📆 Sold - pending', PrintName='📆 Sold - pending',Updated=TO_TIMESTAMP('2022-09-16 20:37:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579889 AND AD_Language='en_US'
;

-- 2022-09-16T17:37:40.413Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579889,'en_US')
;

-- 2022-09-16T17:37:48.858Z
UPDATE AD_Element_Trl SET Name='📆 Beauftragt - offen', PrintName='📆 Beauftragt - offen',Updated=TO_TIMESTAMP('2022-09-16 20:37:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579889 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:37:48.860Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579889,'nl_NL')
;

-- 2022-09-16T17:38:09.043Z
UPDATE AD_Element_Trl SET Name='📆 Distribution ab - offen', PrintName='📆 Distribution ab - offen',Updated=TO_TIMESTAMP('2022-09-16 20:38:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579891 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:38:09.044Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579891,'nl_NL')
;

-- 2022-09-16T17:38:16.267Z
UPDATE AD_Element_Trl SET Name='📆 Pending distribution source', PrintName='📆 Pending distribution source',Updated=TO_TIMESTAMP('2022-09-16 20:38:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579891 AND AD_Language='en_US'
;

-- 2022-09-16T17:38:16.268Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579891,'en_US')
;

-- 2022-09-16T17:38:23.922Z
UPDATE AD_Element_Trl SET Name='📆 Distribution ab - offen', PrintName='📆 Distribution ab - offen',Updated=TO_TIMESTAMP('2022-09-16 20:38:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579891 AND AD_Language='de_DE'
;

-- 2022-09-16T17:38:23.923Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579891,'de_DE')
;

-- 2022-09-16T17:38:23.929Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579891,'de_DE')
;

-- 2022-09-16T17:38:23.930Z
UPDATE AD_Column SET ColumnName='QtyDemand_DD_Order_AtDate', Name='📆 Distribution ab - offen', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579891
;

-- 2022-09-16T17:38:23.931Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_DD_Order_AtDate', Name='📆 Distribution ab - offen', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL, AD_Element_ID=579891 WHERE UPPER(ColumnName)='QTYDEMAND_DD_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:38:23.932Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_DD_Order_AtDate', Name='📆 Distribution ab - offen', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579891 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:38:23.933Z
UPDATE AD_Field SET Name='📆 Distribution ab - offen', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579891) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579891)
;

-- 2022-09-16T17:38:23.949Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Distribution ab - offen', Name='📆 Distribution ab - offen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579891)
;

-- 2022-09-16T17:38:23.950Z
UPDATE AD_Tab SET Name='📆 Distribution ab - offen', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579891
;

-- 2022-09-16T17:38:23.952Z
UPDATE AD_WINDOW SET Name='📆 Distribution ab - offen', Description='Noch weg zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID = 579891
;

-- 2022-09-16T17:38:23.953Z
UPDATE AD_Menu SET   Name = '📆 Distribution ab - offen', Description = 'Noch weg zu bewegende Menge eines Distributionsauftrags.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579891
;

-- 2022-09-16T17:38:31.834Z
UPDATE AD_Element_Trl SET Name='📆 Distribution ab - offen', PrintName='📆 Distribution ab - offen',Updated=TO_TIMESTAMP('2022-09-16 20:38:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579891 AND AD_Language='de_CH'
;

-- 2022-09-16T17:38:31.835Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579891,'de_CH')
;

-- 2022-09-16T17:38:52.371Z
UPDATE AD_Element_Trl SET Name='📆 Summe Abgänge - offen', PrintName='📆 Summe Abgänge - offen',Updated=TO_TIMESTAMP('2022-09-16 20:38:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579892 AND AD_Language='de_CH'
;

-- 2022-09-16T17:38:52.373Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579892,'de_CH')
;

-- 2022-09-16T17:38:59.342Z
UPDATE AD_Element_Trl SET Name='📆 Summe Abgänge - offen', PrintName='📆 Summe Abgänge - offen',Updated=TO_TIMESTAMP('2022-09-16 20:38:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579892 AND AD_Language='de_DE'
;

-- 2022-09-16T17:38:59.344Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579892,'de_DE')
;

-- 2022-09-16T17:38:59.352Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579892,'de_DE')
;

-- 2022-09-16T17:38:59.353Z
UPDATE AD_Column SET ColumnName='QtyDemandSum_AtDate', Name='📆 Summe Abgänge - offen', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='' WHERE AD_Element_ID=579892
;

-- 2022-09-16T17:38:59.354Z
UPDATE AD_Process_Para SET ColumnName='QtyDemandSum_AtDate', Name='📆 Summe Abgänge - offen', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='', AD_Element_ID=579892 WHERE UPPER(ColumnName)='QTYDEMANDSUM_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:38:59.355Z
UPDATE AD_Process_Para SET ColumnName='QtyDemandSum_AtDate', Name='📆 Summe Abgänge - offen', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='' WHERE AD_Element_ID=579892 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:38:59.355Z
UPDATE AD_Field SET Name='📆 Summe Abgänge - offen', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579892) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579892)
;

-- 2022-09-16T17:38:59.366Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Summe Abgänge - offen', Name='📆 Summe Abgänge - offen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579892)
;

-- 2022-09-16T17:38:59.367Z
UPDATE AD_Tab SET Name='📆 Summe Abgänge - offen', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='', CommitWarning = NULL WHERE AD_Element_ID = 579892
;

-- 2022-09-16T17:38:59.368Z
UPDATE AD_WINDOW SET Name='📆 Summe Abgänge - offen', Description='Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', Help='' WHERE AD_Element_ID = 579892
;

-- 2022-09-16T17:38:59.369Z
UPDATE AD_Menu SET   Name = '📆 Summe Abgänge - offen', Description = 'Summe der noch offenen Abgänge durch Aufträge, Produktionsaufträge und Distributionsaufträge', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579892
;

-- 2022-09-16T17:39:06.251Z
UPDATE AD_Element_Trl SET Name='📆 Pending demands', PrintName='📆 Pending demands',Updated=TO_TIMESTAMP('2022-09-16 20:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579892 AND AD_Language='en_US'
;

-- 2022-09-16T17:39:06.252Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579892,'en_US')
;

-- 2022-09-16T17:39:14.293Z
UPDATE AD_Element_Trl SET Name='📆 Summe Abgänge - offen', PrintName='📆 Summe Abgänge - offen',Updated=TO_TIMESTAMP('2022-09-16 20:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579892 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:39:14.295Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579892,'nl_NL')
;

-- 2022-09-16T17:39:37.531Z
UPDATE AD_Element_Trl SET Name='📆 Produktionsempfang - offen', PrintName='📆 Produktionsempfang - offen',Updated=TO_TIMESTAMP('2022-09-16 20:39:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579894 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:39:37.532Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579894,'nl_NL')
;

-- 2022-09-16T17:39:44.018Z
UPDATE AD_Element_Trl SET Name='📆 Pending manufacturing receipt', PrintName='📆 Pending manufacturing receipt',Updated=TO_TIMESTAMP('2022-09-16 20:39:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579894 AND AD_Language='en_US'
;

-- 2022-09-16T17:39:44.019Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579894,'en_US')
;

-- 2022-09-16T17:39:51.006Z
UPDATE AD_Element_Trl SET Name='📆 Produktionsempfang - offen', PrintName='📆 Produktionsempfang - offen',Updated=TO_TIMESTAMP('2022-09-16 20:39:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579894 AND AD_Language='de_DE'
;

-- 2022-09-16T17:39:51.007Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579894,'de_DE')
;

-- 2022-09-16T17:39:51.015Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579894,'de_DE')
;

-- 2022-09-16T17:39:51.016Z
UPDATE AD_Column SET ColumnName='QtySupply_PP_Order_AtDate', Name='📆 Produktionsempfang - offen', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579894
;

-- 2022-09-16T17:39:51.017Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PP_Order_AtDate', Name='📆 Produktionsempfang - offen', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL, AD_Element_ID=579894 WHERE UPPER(ColumnName)='QTYSUPPLY_PP_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:39:51.018Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PP_Order_AtDate', Name='📆 Produktionsempfang - offen', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579894 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:39:51.018Z
UPDATE AD_Field SET Name='📆 Produktionsempfang - offen', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579894) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579894)
;

-- 2022-09-16T17:39:51.029Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Produktionsempfang - offen', Name='📆 Produktionsempfang - offen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579894)
;

-- 2022-09-16T17:39:51.030Z
UPDATE AD_Tab SET Name='📆 Produktionsempfang - offen', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579894
;

-- 2022-09-16T17:39:51.031Z
UPDATE AD_WINDOW SET Name='📆 Produktionsempfang - offen', Description='Noch zu empfangende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID = 579894
;

-- 2022-09-16T17:39:51.032Z
UPDATE AD_Menu SET   Name = '📆 Produktionsempfang - offen', Description = 'Noch zu empfangende Menge eines Produktionsauftrags.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579894
;

-- 2022-09-16T17:39:58.482Z
UPDATE AD_Element_Trl SET Name='📆 Produktionsempfang - offen', PrintName='📆 Produktionsempfang - offen',Updated=TO_TIMESTAMP('2022-09-16 20:39:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579894 AND AD_Language='de_CH'
;

-- 2022-09-16T17:39:58.483Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579894,'de_CH')
;

-- 2022-09-16T17:40:16.402Z
UPDATE AD_Element_Trl SET Name='📆 Bestellt - offen', PrintName='📆 Bestellt - offen',Updated=TO_TIMESTAMP('2022-09-16 20:40:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579895 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:40:16.403Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579895,'nl_NL')
;

-- 2022-09-16T17:40:23.036Z
UPDATE AD_Element_Trl SET Name='📆 Purchased - pending', PrintName='📆 Purchased - pending',Updated=TO_TIMESTAMP('2022-09-16 20:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579895 AND AD_Language='en_US'
;

-- 2022-09-16T17:40:23.037Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579895,'en_US')
;

-- 2022-09-16T17:40:30.461Z
UPDATE AD_Element_Trl SET Name='📆 Bestellt - offen', PrintName='📆 Bestellt - offen',Updated=TO_TIMESTAMP('2022-09-16 20:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579895 AND AD_Language='de_DE'
;

-- 2022-09-16T17:40:30.463Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579895,'de_DE')
;

-- 2022-09-16T17:40:30.473Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579895,'de_DE')
;

-- 2022-09-16T17:40:30.474Z
UPDATE AD_Column SET ColumnName='QtySupply_PurchaseOrder_AtDate', Name='📆 Bestellt - offen', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579895
;

-- 2022-09-16T17:40:30.476Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PurchaseOrder_AtDate', Name='📆 Bestellt - offen', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL, AD_Element_ID=579895 WHERE UPPER(ColumnName)='QTYSUPPLY_PURCHASEORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:40:30.477Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_PurchaseOrder_AtDate', Name='📆 Bestellt - offen', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID=579895 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:40:30.478Z
UPDATE AD_Field SET Name='📆 Bestellt - offen', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579895) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579895)
;

-- 2022-09-16T17:40:30.498Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Bestellt - offen', Name='📆 Bestellt - offen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579895)
;

-- 2022-09-16T17:40:30.499Z
UPDATE AD_Tab SET Name='📆 Bestellt - offen', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579895
;

-- 2022-09-16T17:40:30.501Z
UPDATE AD_WINDOW SET Name='📆 Bestellt - offen', Description='Noch nicht empfangene Bestellmenge für das jeweilige Datum.', Help=NULL WHERE AD_Element_ID = 579895
;

-- 2022-09-16T17:40:30.501Z
UPDATE AD_Menu SET   Name = '📆 Bestellt - offen', Description = 'Noch nicht empfangene Bestellmenge für das jeweilige Datum.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579895
;

-- 2022-09-16T17:40:36.706Z
UPDATE AD_Element_Trl SET Name='📆 Bestellt - offen', PrintName='📆 Bestellt - offen',Updated=TO_TIMESTAMP('2022-09-16 20:40:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579895 AND AD_Language='de_CH'
;

-- 2022-09-16T17:40:36.707Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579895,'de_CH')
;

-- 2022-09-16T17:40:54.901Z
UPDATE AD_Element_Trl SET Name='📆 Distribution an - offen', PrintName='📆 Distribution an - offen',Updated=TO_TIMESTAMP('2022-09-16 20:40:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579896 AND AD_Language='de_CH'
;

-- 2022-09-16T17:40:54.903Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579896,'de_CH')
;

-- 2022-09-16T17:41:01.692Z
UPDATE AD_Element_Trl SET Name='📆 Distribution an - offen', PrintName='📆 Distribution an - offen',Updated=TO_TIMESTAMP('2022-09-16 20:41:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579896 AND AD_Language='de_DE'
;

-- 2022-09-16T17:41:01.693Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579896,'de_DE')
;

-- 2022-09-16T17:41:01.697Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579896,'de_DE')
;

-- 2022-09-16T17:41:01.698Z
UPDATE AD_Column SET ColumnName='QtySupply_DD_Order_AtDate', Name='📆 Distribution an - offen', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579896
;

-- 2022-09-16T17:41:01.699Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_DD_Order_AtDate', Name='📆 Distribution an - offen', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL, AD_Element_ID=579896 WHERE UPPER(ColumnName)='QTYSUPPLY_DD_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:41:01.700Z
UPDATE AD_Process_Para SET ColumnName='QtySupply_DD_Order_AtDate', Name='📆 Distribution an - offen', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID=579896 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:41:01.700Z
UPDATE AD_Field SET Name='📆 Distribution an - offen', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579896) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579896)
;

-- 2022-09-16T17:41:01.712Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Distribution an - offen', Name='📆 Distribution an - offen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579896)
;

-- 2022-09-16T17:41:01.713Z
UPDATE AD_Tab SET Name='📆 Distribution an - offen', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579896
;

-- 2022-09-16T17:41:01.714Z
UPDATE AD_WINDOW SET Name='📆 Distribution an - offen', Description='Noch her zu bewegende Menge eines Distributionsauftrags.', Help=NULL WHERE AD_Element_ID = 579896
;

-- 2022-09-16T17:41:01.714Z
UPDATE AD_Menu SET   Name = '📆 Distribution an - offen', Description = 'Noch her zu bewegende Menge eines Distributionsauftrags.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579896
;

-- 2022-09-16T17:41:08.209Z
UPDATE AD_Element_Trl SET Name='📆 Pending distribution target', PrintName='📆 Pending distribution target',Updated=TO_TIMESTAMP('2022-09-16 20:41:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579896 AND AD_Language='en_US'
;

-- 2022-09-16T17:41:08.210Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579896,'en_US')
;

-- 2022-09-16T17:41:15.041Z
UPDATE AD_Element_Trl SET Name='📆 Distribution an - offen', PrintName='📆 Distribution an - offen',Updated=TO_TIMESTAMP('2022-09-16 20:41:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579896 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:41:15.043Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579896,'nl_NL')
;

-- 2022-09-16T17:41:36.770Z
UPDATE AD_Element_Trl SET Name='📆 Summe Zugänge - offen', PrintName='📆 Summe Zugänge - offen',Updated=TO_TIMESTAMP('2022-09-16 20:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579897 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:41:36.771Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579897,'nl_NL')
;

-- 2022-09-16T17:41:42.924Z
UPDATE AD_Element_Trl SET Name='📆 Pending supplies', PrintName='📆 Pending supplies',Updated=TO_TIMESTAMP('2022-09-16 20:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579897 AND AD_Language='en_US'
;

-- 2022-09-16T17:41:42.925Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579897,'en_US')
;

-- 2022-09-16T17:41:49.578Z
UPDATE AD_Element_Trl SET Name='📆 Summe Zugänge - offen', PrintName='📆 Summe Zugänge - offen',Updated=TO_TIMESTAMP('2022-09-16 20:41:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579897 AND AD_Language='de_DE'
;

-- 2022-09-16T17:41:49.579Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579897,'de_DE')
;

-- 2022-09-16T17:41:49.584Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579897,'de_DE')
;

-- 2022-09-16T17:41:49.585Z
UPDATE AD_Column SET ColumnName='QtySupplySum_AtDate', Name='📆 Summe Zugänge - offen', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL WHERE AD_Element_ID=579897
;

-- 2022-09-16T17:41:49.585Z
UPDATE AD_Process_Para SET ColumnName='QtySupplySum_AtDate', Name='📆 Summe Zugänge - offen', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL, AD_Element_ID=579897 WHERE UPPER(ColumnName)='QTYSUPPLYSUM_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:41:49.586Z
UPDATE AD_Process_Para SET ColumnName='QtySupplySum_AtDate', Name='📆 Summe Zugänge - offen', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL WHERE AD_Element_ID=579897 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:41:49.587Z
UPDATE AD_Field SET Name='📆 Summe Zugänge - offen', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579897) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579897)
;

-- 2022-09-16T17:41:49.601Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Summe Zugänge - offen', Name='📆 Summe Zugänge - offen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579897)
;

-- 2022-09-16T17:41:49.602Z
UPDATE AD_Tab SET Name='📆 Summe Zugänge - offen', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579897
;

-- 2022-09-16T17:41:49.603Z
UPDATE AD_WINDOW SET Name='📆 Summe Zugänge - offen', Description='Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', Help=NULL WHERE AD_Element_ID = 579897
;

-- 2022-09-16T17:41:49.604Z
UPDATE AD_Menu SET   Name = '📆 Summe Zugänge - offen', Description = 'Summe der noch offenen Zugänge durch Bestellungen, Produktionsaufträge und Distributionsaufträge', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579897
;

-- 2022-09-16T17:41:58.517Z
UPDATE AD_Element_Trl SET Name='📆 Summe Zugänge - offen', PrintName='📆 Summe Zugänge - offen',Updated=TO_TIMESTAMP('2022-09-16 20:41:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579897 AND AD_Language='de_CH'
;

-- 2022-09-16T17:41:58.519Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579897,'de_CH')
;

-- 2022-09-16T17:42:16.485Z
UPDATE AD_Element_Trl SET Name='📆 Bedarfssumme', PrintName='📆 Bedarfssumme',Updated=TO_TIMESTAMP('2022-09-16 20:42:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='de_CH'
;

-- 2022-09-16T17:42:16.486Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'de_CH')
;

-- 2022-09-16T17:42:22.875Z
UPDATE AD_Element_Trl SET Name='📆 Bedarfssumme', PrintName='📆 Bedarfssumme',Updated=TO_TIMESTAMP('2022-09-16 20:42:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='de_DE'
;

-- 2022-09-16T17:42:22.876Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'de_DE')
;

-- 2022-09-16T17:42:22.888Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579898,'de_DE')
;

-- 2022-09-16T17:42:22.889Z
UPDATE AD_Column SET ColumnName='QtySupplyRequired_AtDate', Name='📆 Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID=579898
;

-- 2022-09-16T17:42:22.891Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyRequired_AtDate', Name='📆 Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL, AD_Element_ID=579898 WHERE UPPER(ColumnName)='QTYSUPPLYREQUIRED_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:42:22.892Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyRequired_AtDate', Name='📆 Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID=579898 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:42:22.892Z
UPDATE AD_Field SET Name='📆 Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579898) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579898)
;

-- 2022-09-16T17:42:22.922Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Bedarfssumme', Name='📆 Bedarfssumme' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579898)
;

-- 2022-09-16T17:42:22.923Z
UPDATE AD_Tab SET Name='📆 Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579898
;

-- 2022-09-16T17:42:22.926Z
UPDATE AD_WINDOW SET Name='📆 Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID = 579898
;

-- 2022-09-16T17:42:22.927Z
UPDATE AD_Menu SET   Name = '📆 Bedarfssumme', Description = 'Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579898
;

-- 2022-09-16T17:42:29.267Z
UPDATE AD_Element_Trl SET Name='📆 Required supplies', PrintName='📆 Required supplies',Updated=TO_TIMESTAMP('2022-09-16 20:42:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='en_US'
;

-- 2022-09-16T17:42:29.268Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'en_US')
;

-- 2022-09-16T17:42:36.138Z
UPDATE AD_Element_Trl SET Name='📆 Required supplies', PrintName='📆 Required supplies',Updated=TO_TIMESTAMP('2022-09-16 20:42:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:42:36.138Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'nl_NL')
;

-- 2022-09-16T17:42:53.273Z
UPDATE AD_Element_Trl SET Name='📆 To schedule supplies', PrintName='📆 To schedule supplies',Updated=TO_TIMESTAMP('2022-09-16 20:42:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:42:53.274Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'nl_NL')
;

-- 2022-09-16T17:43:00.377Z
UPDATE AD_Element_Trl SET Name='📆 Open requriements', PrintName='📆 Open requriements',Updated=TO_TIMESTAMP('2022-09-16 20:43:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='en_US'
;

-- 2022-09-16T17:43:00.378Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'en_US')
;

-- 2022-09-16T17:43:07.779Z
UPDATE AD_Element_Trl SET Name='📆 Zu disp. Bedarf', PrintName='📆 Zu disp. Bedarf',Updated=TO_TIMESTAMP('2022-09-16 20:43:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='de_DE'
;

-- 2022-09-16T17:43:07.780Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'de_DE')
;

-- 2022-09-16T17:43:07.785Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579899,'de_DE')
;

-- 2022-09-16T17:43:07.786Z
UPDATE AD_Column SET ColumnName='QtySupplyToSchedule_AtDate', Name='📆 Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID=579899
;

-- 2022-09-16T17:43:07.786Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule_AtDate', Name='📆 Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL, AD_Element_ID=579899 WHERE UPPER(ColumnName)='QTYSUPPLYTOSCHEDULE_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:43:07.787Z
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule_AtDate', Name='📆 Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID=579899 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:43:07.788Z
UPDATE AD_Field SET Name='📆 Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579899) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579899)
;

-- 2022-09-16T17:43:07.800Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Zu disp. Bedarf', Name='📆 Zu disp. Bedarf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579899)
;

-- 2022-09-16T17:43:07.801Z
UPDATE AD_Tab SET Name='📆 Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579899
;

-- 2022-09-16T17:43:07.802Z
UPDATE AD_WINDOW SET Name='📆 Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID = 579899
;

-- 2022-09-16T17:43:07.802Z
UPDATE AD_Menu SET   Name = '📆 Zu disp. Bedarf', Description = 'Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579899
;

-- 2022-09-16T17:43:14.628Z
UPDATE AD_Element_Trl SET Name='📆 Zu disp. Bedarf', PrintName='📆 Zu disp. Bedarf',Updated=TO_TIMESTAMP('2022-09-16 20:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='de_CH'
;

-- 2022-09-16T17:43:14.630Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'de_CH')
;

-- 2022-09-16T17:43:30.393Z
UPDATE AD_Element_Trl SET Name='📆 Materialentnahme', PrintName='📆 Materialentnahme',Updated=TO_TIMESTAMP('2022-09-16 20:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='de_CH'
;

-- 2022-09-16T17:43:30.394Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'de_CH')
;

-- 2022-09-16T17:43:37.417Z
UPDATE AD_Element_Trl SET Name='📆 Internal Usage', PrintName='📆 Internal Usage',Updated=TO_TIMESTAMP('2022-09-16 20:43:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='en_US'
;

-- 2022-09-16T17:43:37.418Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'en_US')
;

-- 2022-09-16T17:43:43.965Z
UPDATE AD_Element_Trl SET Name='📆 Materialentnahme', PrintName='📆 Materialentnahme',Updated=TO_TIMESTAMP('2022-09-16 20:43:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:43:43.967Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'nl_NL')
;

-- 2022-09-16T17:43:51.164Z
UPDATE AD_Element_Trl SET Name='📆 Materialentnahme', PrintName='📆 Materialentnahme',Updated=TO_TIMESTAMP('2022-09-16 20:43:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='de_DE'
;

-- 2022-09-16T17:43:51.165Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'de_DE')
;

-- 2022-09-16T17:43:51.172Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(542653,'de_DE')
;

-- 2022-09-16T17:43:51.173Z
UPDATE AD_Column SET ColumnName='QtyMaterialentnahme_AtDate', Name='📆 Materialentnahme', Description=NULL, Help=NULL WHERE AD_Element_ID=542653
;

-- 2022-09-16T17:43:51.174Z
UPDATE AD_Process_Para SET ColumnName='QtyMaterialentnahme_AtDate', Name='📆 Materialentnahme', Description=NULL, Help=NULL, AD_Element_ID=542653 WHERE UPPER(ColumnName)='QTYMATERIALENTNAHME_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:43:51.174Z
UPDATE AD_Process_Para SET ColumnName='QtyMaterialentnahme_AtDate', Name='📆 Materialentnahme', Description=NULL, Help=NULL WHERE AD_Element_ID=542653 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:43:51.175Z
UPDATE AD_Field SET Name='📆 Materialentnahme', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542653) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542653)
;

-- 2022-09-16T17:43:51.185Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Materialentnahme', Name='📆 Materialentnahme' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542653)
;

-- 2022-09-16T17:43:51.187Z
UPDATE AD_Tab SET Name='📆 Materialentnahme', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542653
;

-- 2022-09-16T17:43:51.188Z
UPDATE AD_WINDOW SET Name='📆 Materialentnahme', Description=NULL, Help=NULL WHERE AD_Element_ID = 542653
;

-- 2022-09-16T17:43:51.188Z
UPDATE AD_Menu SET   Name = '📆 Materialentnahme', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542653
;

-- 2022-09-16T17:44:00.275Z
UPDATE AD_Element_Trl SET Name='📆 Materialentnahme', PrintName='📆 Materialentnahme',Updated=TO_TIMESTAMP('2022-09-16 20:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='en_GB'
;

-- 2022-09-16T17:44:00.276Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'en_GB')
;

-- 2022-09-16T17:44:06.529Z
UPDATE AD_Element_Trl SET Name='📆 Materialentnahme', PrintName='📆 Materialentnahme',Updated=TO_TIMESTAMP('2022-09-16 20:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='it_CH'
;

-- 2022-09-16T17:44:06.531Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'it_CH')
;

-- 2022-09-16T17:44:13.193Z
UPDATE AD_Element_Trl SET Name='📆 Materialentnahme', PrintName='📆 Materialentnahme',Updated=TO_TIMESTAMP('2022-09-16 20:44:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542653 AND AD_Language='fr_CH'
;

-- 2022-09-16T17:44:13.194Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542653,'fr_CH')
;

-- 2022-09-16T17:44:30.096Z
UPDATE AD_Element_Trl SET Name='📆 Produktionszuteilung - offen', PrintName='📆 Produktionszuteilung - offen',Updated=TO_TIMESTAMP('2022-09-16 20:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579890 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:44:30.097Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579890,'nl_NL')
;

-- 2022-09-16T17:44:36.481Z
UPDATE AD_Element_Trl SET Name='📆 Manufacturing issue - pending', PrintName='📆 Manufacturing issue - pending',Updated=TO_TIMESTAMP('2022-09-16 20:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579890 AND AD_Language='en_US'
;

-- 2022-09-16T17:44:36.482Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579890,'en_US')
;

-- 2022-09-16T17:44:43.857Z
UPDATE AD_Element_Trl SET Name='📆 Produktionszuteilung - offen', PrintName='📆 Produktionszuteilung - offen',Updated=TO_TIMESTAMP('2022-09-16 20:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579890 AND AD_Language='de_DE'
;

-- 2022-09-16T17:44:43.858Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579890,'de_DE')
;

-- 2022-09-16T17:44:43.864Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579890,'de_DE')
;

-- 2022-09-16T17:44:43.866Z
UPDATE AD_Column SET ColumnName='QtyDemand_PP_Order_AtDate', Name='📆 Produktionszuteilung - offen', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579890
;

-- 2022-09-16T17:44:43.867Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_PP_Order_AtDate', Name='📆 Produktionszuteilung - offen', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL, AD_Element_ID=579890 WHERE UPPER(ColumnName)='QTYDEMAND_PP_ORDER_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:44:43.868Z
UPDATE AD_Process_Para SET ColumnName='QtyDemand_PP_Order_AtDate', Name='📆 Produktionszuteilung - offen', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID=579890 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:44:43.869Z
UPDATE AD_Field SET Name='📆 Produktionszuteilung - offen', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579890) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579890)
;

-- 2022-09-16T17:44:43.885Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Produktionszuteilung - offen', Name='📆 Produktionszuteilung - offen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579890)
;

-- 2022-09-16T17:44:43.886Z
UPDATE AD_Tab SET Name='📆 Produktionszuteilung - offen', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579890
;

-- 2022-09-16T17:44:43.888Z
UPDATE AD_WINDOW SET Name='📆 Produktionszuteilung - offen', Description='Noch zuzuteilende Menge eines Produktionsauftrags.', Help=NULL WHERE AD_Element_ID = 579890
;

-- 2022-09-16T17:44:43.889Z
UPDATE AD_Menu SET   Name = '📆 Produktionszuteilung - offen', Description = 'Noch zuzuteilende Menge eines Produktionsauftrags.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579890
;

-- 2022-09-16T17:44:50.080Z
UPDATE AD_Element_Trl SET Name='📆 Produktionszuteilung - offen', PrintName='📆 Produktionszuteilung - offen',Updated=TO_TIMESTAMP('2022-09-16 20:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579890 AND AD_Language='de_CH'
;

-- 2022-09-16T17:44:50.081Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579890,'de_CH')
;

-- 2022-09-16T17:45:06.905Z
UPDATE AD_Element_Trl SET Name='📆 Planbestand', PrintName='📆 Planbestand',Updated=TO_TIMESTAMP('2022-09-16 20:45:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579905 AND AD_Language='de_CH'
;

-- 2022-09-16T17:45:06.906Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579905,'de_CH')
;

-- 2022-09-16T17:45:13.688Z
UPDATE AD_Element_Trl SET Name='📆 Bestand', PrintName='📆 Bestand',Updated=TO_TIMESTAMP('2022-09-16 20:45:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579905 AND AD_Language='de_DE'
;

-- 2022-09-16T17:45:13.689Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579905,'de_DE')
;

-- 2022-09-16T17:45:13.695Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579905,'de_DE')
;

-- 2022-09-16T17:45:13.696Z
UPDATE AD_Column SET ColumnName='QtyStockCurrent_AtDate', Name='📆 Bestand', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL WHERE AD_Element_ID=579905
;

-- 2022-09-16T17:45:13.697Z
UPDATE AD_Process_Para SET ColumnName='QtyStockCurrent_AtDate', Name='📆 Bestand', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL, AD_Element_ID=579905 WHERE UPPER(ColumnName)='QTYSTOCKCURRENT_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:45:13.698Z
UPDATE AD_Process_Para SET ColumnName='QtyStockCurrent_AtDate', Name='📆 Bestand', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL WHERE AD_Element_ID=579905 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:45:13.699Z
UPDATE AD_Field SET Name='📆 Bestand', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579905) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579905)
;

-- 2022-09-16T17:45:13.714Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Bestand', Name='📆 Bestand' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579905)
;

-- 2022-09-16T17:45:13.715Z
UPDATE AD_Tab SET Name='📆 Bestand', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579905
;

-- 2022-09-16T17:45:13.717Z
UPDATE AD_WINDOW SET Name='📆 Bestand', Description='Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', Help=NULL WHERE AD_Element_ID = 579905
;

-- 2022-09-16T17:45:13.718Z
UPDATE AD_Menu SET   Name = '📆 Bestand', Description = 'Kombination aus der Zählmenge oder Inventor des jeweiligen Tages mit nachfolgenden Warenein- und Ausgängen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579905
;

-- 2022-09-16T17:45:21.122Z
UPDATE AD_Element_Trl SET Name='📆 Stock', PrintName='📆 Stock',Updated=TO_TIMESTAMP('2022-09-16 20:45:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579905 AND AD_Language='en_US'
;

-- 2022-09-16T17:45:21.124Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579905,'en_US')
;

-- 2022-09-16T17:45:27.688Z
UPDATE AD_Element_Trl SET Name='📆 Bestand', PrintName='📆 Bestand',Updated=TO_TIMESTAMP('2022-09-16 20:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579905 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:45:27.689Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579905,'nl_NL')
;

-- 2022-09-16T17:45:49.497Z
UPDATE AD_Element_Trl SET Name='📆 Zählbestand', PrintName='📆 Zählbestand',Updated=TO_TIMESTAMP('2022-09-16 20:45:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='de_CH'
;

-- 2022-09-16T17:45:49.498Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'de_CH')
;

-- 2022-09-16T17:45:56.065Z
UPDATE AD_Element_Trl SET Name='📆 Zählbestand', PrintName='📆 Zählbestand',Updated=TO_TIMESTAMP('2022-09-16 20:45:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='de_DE'
;

-- 2022-09-16T17:45:56.066Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'de_DE')
;

-- 2022-09-16T17:45:56.073Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579900,'de_DE')
;

-- 2022-09-16T17:45:56.073Z
UPDATE AD_Column SET ColumnName='QtyStockEstimateCount_AtDate', Name='📆 Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID=579900
;

-- 2022-09-16T17:45:56.074Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateCount_AtDate', Name='📆 Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL, AD_Element_ID=579900 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATECOUNT_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:45:56.075Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateCount_AtDate', Name='📆 Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID=579900 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:45:56.075Z
UPDATE AD_Field SET Name='📆 Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579900) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579900)
;

-- 2022-09-16T17:45:56.088Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Zählbestand', Name='📆 Zählbestand' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579900)
;

-- 2022-09-16T17:45:56.089Z
UPDATE AD_Tab SET Name='📆 Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579900
;

-- 2022-09-16T17:45:56.091Z
UPDATE AD_WINDOW SET Name='📆 Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID = 579900
;

-- 2022-09-16T17:45:56.092Z
UPDATE AD_Menu SET   Name = '📆 Zählbestand', Description = 'Menge laut "grober" Zählung.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579900
;

-- 2022-09-16T17:46:02.291Z
UPDATE AD_Element_Trl SET Name='📆 Stock count', PrintName='📆 Stock count',Updated=TO_TIMESTAMP('2022-09-16 20:46:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='en_US'
;

-- 2022-09-16T17:46:02.292Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'en_US')
;

-- 2022-09-16T17:46:08.378Z
UPDATE AD_Element_Trl SET Name='📆 Zählbestand', PrintName='📆 Zählbestand',Updated=TO_TIMESTAMP('2022-09-16 20:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:46:08.379Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'nl_NL')
;

-- 2022-09-16T17:46:29.017Z
UPDATE AD_Element_Trl SET Name='📆 Stock estimate time', PrintName='📆 Stock estimate time',Updated=TO_TIMESTAMP('2022-09-16 20:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:46:29.018Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579902,'nl_NL')
;

-- 2022-09-16T17:46:35.488Z
UPDATE AD_Element_Trl SET Name='📆 Stock estimate time', PrintName='📆 Stock estimate time',Updated=TO_TIMESTAMP('2022-09-16 20:46:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902 AND AD_Language='en_US'
;

-- 2022-09-16T17:46:35.489Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579902,'en_US')
;

-- 2022-09-16T17:46:41.713Z
UPDATE AD_Element_Trl SET Name='📆 Zeitpunkt der Zählung', PrintName='📆 Zeitpunkt der Zählung',Updated=TO_TIMESTAMP('2022-09-16 20:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902 AND AD_Language='de_DE'
;

-- 2022-09-16T17:46:41.713Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579902,'de_DE')
;

-- 2022-09-16T17:46:41.717Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579902,'de_DE')
;

-- 2022-09-16T17:46:41.718Z
UPDATE AD_Column SET ColumnName='QtyStockEstimateTime_AtDate', Name='📆 Zeitpunkt der Zählung', Description=NULL, Help=NULL WHERE AD_Element_ID=579902
;

-- 2022-09-16T17:46:41.719Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateTime_AtDate', Name='📆 Zeitpunkt der Zählung', Description=NULL, Help=NULL, AD_Element_ID=579902 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATETIME_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:46:41.719Z
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateTime_AtDate', Name='📆 Zeitpunkt der Zählung', Description=NULL, Help=NULL WHERE AD_Element_ID=579902 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:46:41.720Z
UPDATE AD_Field SET Name='📆 Zeitpunkt der Zählung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579902) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579902)
;

-- 2022-09-16T17:46:41.731Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Zeitpunkt der Zählung', Name='📆 Zeitpunkt der Zählung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579902)
;

-- 2022-09-16T17:46:41.732Z
UPDATE AD_Tab SET Name='📆 Zeitpunkt der Zählung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579902
;

-- 2022-09-16T17:46:41.733Z
UPDATE AD_WINDOW SET Name='📆 Zeitpunkt der Zählung', Description=NULL, Help=NULL WHERE AD_Element_ID = 579902
;

-- 2022-09-16T17:46:41.733Z
UPDATE AD_Menu SET   Name = '📆 Zeitpunkt der Zählung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579902
;

-- 2022-09-16T17:46:47.986Z
UPDATE AD_Element_Trl SET Name='📆 Zeitpunkt der Zählung', PrintName='📆 Zeitpunkt der Zählung',Updated=TO_TIMESTAMP('2022-09-16 20:46:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902 AND AD_Language='de_CH'
;

-- 2022-09-16T17:46:47.987Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579902,'de_CH')
;

-- 2022-09-16T17:47:03.384Z
UPDATE AD_Element_Trl SET Name='📆 Inventory count', PrintName='📆 Inventory count',Updated=TO_TIMESTAMP('2022-09-16 20:47:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:47:03.385Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'nl_NL')
;

-- 2022-09-16T17:47:10.603Z
UPDATE AD_Element_Trl SET Name='📆 Inventory count', PrintName='📆 Inventory count',Updated=TO_TIMESTAMP('2022-09-16 20:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='en_US'
;

-- 2022-09-16T17:47:10.604Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'en_US')
;

-- 2022-09-16T17:47:19.323Z
UPDATE AD_Element_Trl SET Name='📆 Inventurbestand', PrintName='📆 Inventurbestand',Updated=TO_TIMESTAMP('2022-09-16 20:47:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='de_DE'
;

-- 2022-09-16T17:47:19.325Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'de_DE')
;

-- 2022-09-16T17:47:19.332Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579903,'de_DE')
;

-- 2022-09-16T17:47:19.333Z
UPDATE AD_Column SET ColumnName='QtyInventoryCount_AtDate', Name='📆 Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID=579903
;

-- 2022-09-16T17:47:19.333Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryCount_AtDate', Name='📆 Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL, AD_Element_ID=579903 WHERE UPPER(ColumnName)='QTYINVENTORYCOUNT_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:47:19.334Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryCount_AtDate', Name='📆 Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID=579903 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:47:19.335Z
UPDATE AD_Field SET Name='📆 Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579903) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579903)
;

-- 2022-09-16T17:47:19.345Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Inventurbestand', Name='📆 Inventurbestand' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579903)
;

-- 2022-09-16T17:47:19.347Z
UPDATE AD_Tab SET Name='📆 Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579903
;

-- 2022-09-16T17:47:19.348Z
UPDATE AD_WINDOW SET Name='📆 Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID = 579903
;

-- 2022-09-16T17:47:19.348Z
UPDATE AD_Menu SET   Name = '📆 Inventurbestand', Description = 'Bestand laut der letzten Inventur', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579903
;

-- 2022-09-16T17:47:25.324Z
UPDATE AD_Element_Trl SET Name='📆 Inventurbestand', PrintName='📆 Inventurbestand',Updated=TO_TIMESTAMP('2022-09-16 20:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='de_CH'
;

-- 2022-09-16T17:47:25.325Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'de_CH')
;

-- 2022-09-16T17:47:43.506Z
UPDATE AD_Element_Trl SET Name='📆 Inventory time', PrintName='📆 Inventory time',Updated=TO_TIMESTAMP('2022-09-16 20:47:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:47:43.507Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'nl_NL')
;

-- 2022-09-16T17:47:48.835Z
UPDATE AD_Element_Trl SET Name='📆 Inventory time', PrintName='📆 Inventory time',Updated=TO_TIMESTAMP('2022-09-16 20:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='en_US'
;

-- 2022-09-16T17:47:48.836Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'en_US')
;

-- 2022-09-16T17:47:54.913Z
UPDATE AD_Element_Trl SET Name='📆 Inventur-Zeit', PrintName='📆 Inventur-Zeit',Updated=TO_TIMESTAMP('2022-09-16 20:47:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='de_DE'
;

-- 2022-09-16T17:47:54.914Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'de_DE')
;

-- 2022-09-16T17:47:54.921Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579904,'de_DE')
;

-- 2022-09-16T17:47:54.921Z
UPDATE AD_Column SET ColumnName='QtyInventoryTime_AtDate', Name='📆 Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID=579904
;

-- 2022-09-16T17:47:54.921Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryTime_AtDate', Name='📆 Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL, AD_Element_ID=579904 WHERE UPPER(ColumnName)='QTYINVENTORYTIME_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:47:54.922Z
UPDATE AD_Process_Para SET ColumnName='QtyInventoryTime_AtDate', Name='📆 Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID=579904 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:47:54.922Z
UPDATE AD_Field SET Name='📆 Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579904) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579904)
;

-- 2022-09-16T17:47:54.933Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Inventur-Zeit', Name='📆 Inventur-Zeit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579904)
;

-- 2022-09-16T17:47:54.934Z
UPDATE AD_Tab SET Name='📆 Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579904
;

-- 2022-09-16T17:47:54.935Z
UPDATE AD_WINDOW SET Name='📆 Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID = 579904
;

-- 2022-09-16T17:47:54.936Z
UPDATE AD_Menu SET   Name = '📆 Inventur-Zeit', Description = 'Zeipunkt, an dem die Inventur fertig gestellt wurde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579904
;

-- 2022-09-16T17:48:01.804Z
UPDATE AD_Element_Trl SET Name='📆 Inventur-Zeit', PrintName='📆 Inventur-Zeit',Updated=TO_TIMESTAMP('2022-09-16 20:48:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='de_CH'
;

-- 2022-09-16T17:48:01.805Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'de_CH')
;

-- 2022-09-16T17:48:16.890Z
UPDATE AD_Element_Trl SET Name='📆 Erw. Überschuss', PrintName='📆 Erw. Überschuss',Updated=TO_TIMESTAMP('2022-09-16 20:48:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579906 AND AD_Language='nl_NL'
;

-- 2022-09-16T17:48:16.891Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579906,'nl_NL')
;

-- 2022-09-16T17:48:21.359Z
UPDATE AD_Element_Trl SET Name='📆 Expected surplus', PrintName='📆 Expected surplus',Updated=TO_TIMESTAMP('2022-09-16 20:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579906 AND AD_Language='en_US'
;

-- 2022-09-16T17:48:21.360Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579906,'en_US')
;

-- 2022-09-16T17:48:26.273Z
UPDATE AD_Element_Trl SET Name='📆 Erw. Überschuss', PrintName='📆 Erw. Überschuss',Updated=TO_TIMESTAMP('2022-09-16 20:48:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579906 AND AD_Language='de_DE'
;

-- 2022-09-16T17:48:26.273Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579906,'de_DE')
;

-- 2022-09-16T17:48:26.278Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579906,'de_DE')
;

-- 2022-09-16T17:48:26.278Z
UPDATE AD_Column SET ColumnName='QtyExpectedSurplus_AtDate', Name='📆 Erw. Überschuss', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL WHERE AD_Element_ID=579906
;

-- 2022-09-16T17:48:26.279Z
UPDATE AD_Process_Para SET ColumnName='QtyExpectedSurplus_AtDate', Name='📆 Erw. Überschuss', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL, AD_Element_ID=579906 WHERE UPPER(ColumnName)='QTYEXPECTEDSURPLUS_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-16T17:48:26.279Z
UPDATE AD_Process_Para SET ColumnName='QtyExpectedSurplus_AtDate', Name='📆 Erw. Überschuss', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL WHERE AD_Element_ID=579906 AND IsCentrallyMaintained='Y'
;

-- 2022-09-16T17:48:26.280Z
UPDATE AD_Field SET Name='📆 Erw. Überschuss', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579906) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579906)
;

-- 2022-09-16T17:48:26.291Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 Erw. Überschuss', Name='📆 Erw. Überschuss' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579906)
;

-- 2022-09-16T17:48:26.291Z
UPDATE AD_Tab SET Name='📆 Erw. Überschuss', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579906
;

-- 2022-09-16T17:48:26.292Z
UPDATE AD_WINDOW SET Name='📆 Erw. Überschuss', Description='Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', Help=NULL WHERE AD_Element_ID = 579906
;

-- 2022-09-16T17:48:26.293Z
UPDATE AD_Menu SET   Name = '📆 Erw. Überschuss', Description = 'Kombination aus dem Bestand und den noch offenen Zugängen und Abgängen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579906
;

-- 2022-09-16T17:48:30.787Z
UPDATE AD_Element_Trl SET Name='📆 Erw. Überschuss', PrintName='📆 Erw. Überschuss',Updated=TO_TIMESTAMP('2022-09-16 20:48:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579906 AND AD_Language='de_CH'
;

-- 2022-09-16T17:48:30.788Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579906,'de_CH')
;
