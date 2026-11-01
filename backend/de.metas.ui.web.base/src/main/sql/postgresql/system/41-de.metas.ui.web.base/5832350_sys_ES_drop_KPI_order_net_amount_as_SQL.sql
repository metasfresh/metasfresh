-- Re-author the dashboard KPIs "Purchase order net amount" (1000006) and "Sales order net amount" (1000007)
-- from Elasticsearch to SQL (KPI_DataSource_Type='S'), in place: ids and dashboard items stay unchanged.
-- Value = sum(C_OrderLine.LineNetAmt) over C_OrderLine join C_Order, split by C_Order.IsSOTrx, restricted to
-- C_Order.DateOrdered within the KPI time range (es_timerange P-7D / compare offset are kept as they are).
-- The Elasticsearch columns (ES_Index, ES_Query, ES_FieldPath) are intentionally left untouched here.

UPDATE WEBUI_KPI
SET KPI_DataSource_Type = 'S',
    Source_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_OrderLine'),
    SQL_From = 'FROM C_OrderLine ol INNER JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID',
    SQL_WhereClause = 'o.IsSOTrx = ''N'' AND o.DateOrdered >= @FromMillis@ AND o.DateOrdered <= @ToMillis@',
    SQL_GroupAndOrderBy = NULL,
    Updated = TO_TIMESTAMP('2026-11-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE WEBUI_KPI_ID = 1000006;

UPDATE WEBUI_KPI
SET KPI_DataSource_Type = 'S',
    Source_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_OrderLine'),
    SQL_From = 'FROM C_OrderLine ol INNER JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID',
    SQL_WhereClause = 'o.IsSOTrx = ''Y'' AND o.DateOrdered >= @FromMillis@ AND o.DateOrdered <= @ToMillis@',
    SQL_GroupAndOrderBy = NULL,
    Updated = TO_TIMESTAMP('2026-11-01 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE WEBUI_KPI_ID = 1000007;

UPDATE WEBUI_KPI_Field
SET SQL_Select = 'COALESCE(SUM(ol.LineNetAmt), 0)',
    Updated = TO_TIMESTAMP('2026-11-01 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE WEBUI_KPI_Field_ID IN (1000073, 1000074);
