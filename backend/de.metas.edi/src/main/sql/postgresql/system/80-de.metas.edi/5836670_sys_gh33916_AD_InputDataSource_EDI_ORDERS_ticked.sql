-- EDI-ORDERS data sources are EDI channels; add the eddyson EDI-ORDERS data source (also an EDI channel).
-- AD_Org_ID is deliberately 0 for the new row: the data source lookup matches the candidate's org or 0.

INSERT INTO AD_InputDataSource (AD_Client_ID, AD_InputDataSource_ID /*From ID Server*/, AD_Org_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsDestination, IsEDIChannel, Name, Updated, UpdatedBy, Value)
SELECT 1000000, 540239 /*From ID Server*/, 0,
       TO_TIMESTAMP('2026-11-19 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'de.metas.esb.edi', 'DEST.de.metas.edi.eddyson', 'Y', 'Y', 'Y', 'Aus eddyson EDI-ORDERS',
       TO_TIMESTAMP('2026-11-19 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Aus eddyson EDI-ORDERS'
WHERE NOT EXISTS (SELECT 1 FROM AD_InputDataSource WHERE InternalName = 'DEST.de.metas.edi.eddyson')
;

UPDATE AD_InputDataSource
SET IsEDIChannel = 'Y', Updated = TO_TIMESTAMP('2026-11-19 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 99
WHERE InternalName IN ('DEST.de.metas.edi', 'DEST.de.metas.edi.ecosio')
;
