UPDATE C_Order
SET IsEdiEnabled = 'Y'
WHERE AD_InputDataSource_ID = 150 -- "Importiert aus EDI-ORDERS-Datei"
;