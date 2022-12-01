-- insert Bill_User_ID into the "invoicing-agg-std" aggregation
INSERT INTO public.c_aggregationitem (ad_client_id, ad_column_id, ad_org_id, c_aggregation_id, c_aggregationitem_id, created, createdby, description, includelogic, isactive, updated, updatedby, entitytype, type, included_aggregation_id, c_aggregation_attribute_id)
SELECT 1000000,
       544922,
       1000000,
       1000000,
       1000015,
       '2021-12-10 07:23:17.000000 +01:00',
       100,
       NULL,
       NULL,
       'Y',
       '2021-12-10 07:23:17.000000 +01:00',
       100,
       'de.metas.invoicecandidate',
       'COL',
       NULL,
       NULL
WHERE NOT EXISTS(SELECT 1 FROM c_aggregationitem WHERE c_aggregationitem_id = 1000015)
;

