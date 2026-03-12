SELECT COUNT(*) -- show how many business partners dont have the aggregation type "invoicing-agg-per-order"
FROM c_bpartner
WHERE so_invoice_aggregation_id IS DISTINCT FROM (
    SELECT c_aggregation_id
    FROM c_aggregation
    WHERE name = 'invoicing-agg-per-order'
    LIMIT 1
); -- prints 0


-- update those business partners that currently do not have the aggregation type "invoicing-agg-per-order" and set the aggregation type to "invoicing-agg-per-order"
WITH target_aggregation AS (
    SELECT c_aggregation_id
    FROM c_aggregation
    WHERE name = 'invoicing-agg-per-order'
    LIMIT 1
)
UPDATE c_bpartner
SET so_invoice_aggregation_id = (SELECT c_aggregation_id FROM target_aggregation)
WHERE so_invoice_aggregation_id IS DISTINCT FROM (SELECT c_aggregation_id FROM target_aggregation);

-- show how many aggregation types have the flag "isdefault" set to "Y" but are not "invoicing-agg-per-order" or have the flag "isdefault" set to "N" but are "invoicing-agg-per-order"
SELECT COUNT(*) from c_aggregation
WHERE   (name = 'invoicing-agg-per-order' AND isdefault = 'N')
OR      (name != 'invoicing-agg-per-order' AND isdefault = 'Y'); -- prints 0

--set the default flag "isdefault" to "Y" for the aggregation type "invoicing-agg-per-order" and "N" for all other aggregation types
UPDATE c_aggregation
SET isdefault = CASE WHEN name = 'invoicing-agg-per-order' THEN 'Y' ELSE 'N'END
WHERE   (name = 'invoicing-agg-per-order' AND isdefault = 'N')
OR      (name != 'invoicing-agg-per-order' AND isdefault = 'Y');