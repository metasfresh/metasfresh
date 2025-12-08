INSERT INTO public.c_queue_packageprocessor (ad_client_id, ad_org_id, c_queue_packageprocessor_id, classname, created, createdby, description, entitytype, isactive, updated, updatedby, internalname)
VALUES (0, 0, 540110, 'de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment', '2024-09-20 07:30:19.000000 +00:00', 100, 'Creates follow-up documents (Sales Order, Invoice, Shipment)', 'de.metas.pos', 'Y', '2024-09-20 07:30:27.000000 +00:00', 100, 'de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment')
;

INSERT INTO public.c_queue_processor (ad_client_id, ad_org_id, c_queue_processor_id, created, createdby, isactive, keepalivetimemillis, name, poolsize, priority, updated, updatedby, description)
VALUES  (0, 0, 540080, '2024-09-20 07:31:39.000000 +00:00', 100, 'Y', 1000, 'C_POSOrder_CreateInvoiceAndShipment', 1, NULL, '2024-09-20 07:31:39.000000 +00:00', 100, '');

INSERT INTO public.c_queue_processor_assign (ad_client_id, ad_org_id, c_queue_packageprocessor_id, c_queue_processor_assign_id, c_queue_processor_id, created, createdby, isactive, updated, updatedby)
VALUES  (0, 0, 540110, 540124, 540080, '2024-09-20 07:31:52.000000 +00:00', 100, 'Y', '2024-09-20 07:31:52.000000 +00:00', 100);

