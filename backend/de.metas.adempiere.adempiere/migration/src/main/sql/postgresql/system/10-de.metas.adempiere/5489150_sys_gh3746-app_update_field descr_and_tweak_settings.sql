-- 2018-03-22T08:57:03.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Legt fest, ob zu der betreffenden Spalte Referenzen (d.h. externe Datensätze, die die Spalte referenzieren) angezeigt werden sollen', Help='',Updated=TO_TIMESTAMP('2018-03-22 08:57:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541640
;

-- 2018-03-22T08:57:03.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsGenericZoomOrigin', Name='GenericZoom Quellspalte', Description='Legt fest, ob zu der betreffenden Spalte Referenzen (d.h. externe Datensätze, die die Spalte referenzieren) angezeigt werden sollen', Help='' WHERE AD_Element_ID=541640
;

-- 2018-03-22T08:57:03.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsGenericZoomOrigin', Name='GenericZoom Quellspalte', Description='Legt fest, ob zu der betreffenden Spalte Referenzen (d.h. externe Datensätze, die die Spalte referenzieren) angezeigt werden sollen', Help='', AD_Element_ID=541640 WHERE UPPER(ColumnName)='ISGENERICZOOMORIGIN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-03-22T08:57:03.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsGenericZoomOrigin', Name='GenericZoom Quellspalte', Description='Legt fest, ob zu der betreffenden Spalte Referenzen (d.h. externe Datensätze, die die Spalte referenzieren) angezeigt werden sollen', Help='' WHERE AD_Element_ID=541640 AND IsCentrallyMaintained='Y'
;

-- 2018-03-22T08:57:03.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='GenericZoom Quellspalte', Description='Legt fest, ob zu der betreffenden Spalte Referenzen (d.h. externe Datensätze, die die Spalte referenzieren) angezeigt werden sollen', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541640) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541640)
;

--
-- set the flag for M_Material_Tracking_ID which triggered this issue
--
-- 2018-03-22T09:01:40.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2018-03-22 09:01:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551110
;


/*
here is a number of hand-curated tables where i set the flag.
I used the following SQL to get ID-columns of toplevel tabs that didn't have the flag set at the time:

	select c.AD_Column_ID, t.TableName, c.ColumnName, tab.Name, w.Name,  tab.SeqNo
	from AD_Column c 
		join AD_Table t ON t.AD_Table_ID=c.AD_Table_ID
		join AD_Tab tab ON tab.AD_Table_ID=c.AD_Table_ID
		join AD_Window w ON w.AD_Window_ID=tab.AD_Window_ID
	where true 
		AND c.IsKey='Y' AND c.IsGenericZoomOrigin='N' AND c.IsActive='Y'
		AND t.IsActive='Y'
		AND tab.SeqNo=10
	ORDER BY w.Name, tab.Name;
*/
UPDATE AD_Column SET IsGenericZoomOrigin='Y', Updated=now(), UpdatedBy=99
WHERE AD_Column_ID IN (
-- c.AD_Column_ID, t.TableName, c.ColumnName, tab.Name, w.Name,  tab.SeqNo
	551302, --;"C_Async_Batch";"C_Async_Batch_ID";"Batch";"Async Batch";10
	54395, --;"C_BankStatementLine_Ref";"C_BankStatementLine_Ref_ID";"Bankauszug Referenz";"Bankauszug Referenz";10
	554109, --;"PMM_PurchaseCandidate";"PMM_PurchaseCandidate_ID";"Bestellkandidat";"Beschaffungsplanung";10
	557857, --;"C_PurchaseCandidate";"C_PurchaseCandidate_ID";"Bestelldisposition";"Bestelldisposition";10
	552733, --;"C_Order_MFGWarehouse_Report";"C_Order_MFGWarehouse_Report_ID";"Bestellkontrolle";"Bestellkontrolle";10
	559132, --;"DATEV_Export";"DATEV_Export_ID";"Buchungen Export";"Buchungen Export";10
	559197, --;"DATEV_ExportFormat";"DATEV_ExportFormat_ID";"Format";"Buchungen Export Format";10
	551724, --;"EDI_Desadv";"EDI_Desadv_ID";"DESADV";"EDI Lieferavis (DESADV)";10
	550953, --;"M_HU_LUTU_Configuration";"M_HU_LUTU_Configuration_ID";"Gebindekonfiguration";"Gebindekonfiguration";10
	556990, --;"M_HU_Trace";"M_HU_Trace_ID";"Rückverfolgung";"HU-Rückverfolgung";10
	554137, --;"PMM_QtyReport_Event";"PMM_QtyReport_Event_ID";"Datensatz";"Lieferplanungsdatensatz";10
	549487 --;"M_ReceiptSchedule";"M_ReceiptSchedule_ID";"Wareneingangsdisposition";"Wareneingangsdisposition";10
);

