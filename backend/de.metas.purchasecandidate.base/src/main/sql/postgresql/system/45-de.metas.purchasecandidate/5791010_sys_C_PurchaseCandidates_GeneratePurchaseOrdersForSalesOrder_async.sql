-- Register C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder async processor
-- IDs allocated from ID server (idserver.metas.de):
--   C_Queue_PackageProcessor_ID=540114
--   C_Queue_Processor_ID=540084
--   C_Queue_Processor_Assign_ID=540128

-- 2026-03-04T16:10:00.000Z
INSERT INTO C_Queue_PackageProcessor (Created, CreatedBy, IsActive, Updated, C_Queue_PackageProcessor_ID, AD_Client_ID, Description, Classname, InternalName, AD_Org_ID, EntityType, UpdatedBy)
VALUES (TO_TIMESTAMP('2026-03-04 16:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', TO_TIMESTAMP('2026-03-04 16:10:00', 'YYYY-MM-DD HH24:MI:SS'),
        540114, 0,
        'Debouncer: collects purchase candidates from a sales order and creates aggregated purchase orders (one per vendor)',
        'de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder',
        'C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder',
        0, 'de.metas.purchasecandidate', 100)
;

-- 2026-03-04T16:10:01.000Z
INSERT INTO C_Queue_Processor (Created, CreatedBy, IsActive, UpdatedBy, KeepAliveTimeMillis, PoolSize, C_Queue_Processor_ID, AD_Client_ID, AD_Org_ID, Name, Updated)
VALUES (TO_TIMESTAMP('2026-03-04 16:10:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 100, 1000, 1,
        540084, 0, 0,
        'C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder',
        TO_TIMESTAMP('2026-03-04 16:10:01', 'YYYY-MM-DD HH24:MI:SS'))
;

-- 2026-03-04T16:10:02.000Z
INSERT INTO C_Queue_Processor_Assign (Created, CreatedBy, IsActive, Updated, UpdatedBy, C_Queue_Processor_ID, C_Queue_PackageProcessor_ID, C_Queue_Processor_Assign_ID, AD_Client_ID, AD_Org_ID)
VALUES (TO_TIMESTAMP('2026-03-04 16:10:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', TO_TIMESTAMP('2026-03-04 16:10:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        540084, 540114, 540128, 0, 0)
;
