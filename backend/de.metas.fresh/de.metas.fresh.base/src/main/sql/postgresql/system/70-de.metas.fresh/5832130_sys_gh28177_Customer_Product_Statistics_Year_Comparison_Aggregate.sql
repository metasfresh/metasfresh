SELECT db_drop_functions('report.customer_product_statistics_year_comparison')
;

SELECT db_drop_functions('report.customer_product_statistics_year_comparison_aggregate')
;

CREATE FUNCTION report.Customer_Product_Statistics_Year_Comparison_Aggregate(
    p_AD_Org_ID numeric,
    p_C_Year_ID numeric
)
    RETURNS TABLE
            (
                C_BPartner_ID numeric,
                M_Product_ID  numeric,
                PeriodNo      integer,
                PeriodStart   date,
                Qty           numeric,
                Amt           numeric
            )
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_AD_Client_ID    numeric;
    v_C_AcctSchema_ID numeric;
    v_InvoiceTable_ID numeric;
    v_PackingCat      numeric;
BEGIN
    SELECT o.AD_Client_ID INTO v_AD_Client_ID FROM AD_Org o WHERE o.AD_Org_ID = p_AD_Org_ID;
    v_C_AcctSchema_ID := getC_AcctSchema_ID(v_AD_Client_ID, p_AD_Org_ID);
    IF (v_C_AcctSchema_ID IS NULL OR v_C_AcctSchema_ID <= 0)
    THEN
        RAISE EXCEPTION 'No accounting schema found for AD_Org_ID=% (getC_AcctSchema_ID); cannot read the posted amounts', p_AD_Org_ID;
    END IF;

    v_InvoiceTable_ID := get_Table_ID('C_Invoice');
    v_PackingCat := getSysConfigAsNumeric('PackingMaterialProductCategoryID', v_AD_Client_ID, p_AD_Org_ID);

    RETURN QUERY
        WITH periods AS (SELECT p.C_Period_ID, p.PeriodNo::integer AS PeriodNo, p.StartDate::date AS StartDate, p.EndDate::date AS EndDate
                         FROM C_Period p
                         WHERE p.C_Year_ID = p_C_Year_ID
                           AND p.PeriodType = 'S'),
             inv_lines AS (SELECT i.C_BPartner_ID, -- Debitor = the invoice (bill-to) partner
                                  il.M_Product_ID,
                                  il.C_InvoiceLine_ID,
                                  i.C_Invoice_ID
                           FROM C_InvoiceLine il
                                    JOIN C_Invoice i ON i.C_Invoice_ID = il.C_Invoice_ID
                                    JOIN M_Product pr ON pr.M_Product_ID = il.M_Product_ID
                           WHERE i.IsSOTrx = 'Y'
                             AND i.DocStatus IN ('CO', 'CL')
                             AND i.AD_Org_ID = p_AD_Org_ID
                             AND i.DateAcct::date BETWEEN (SELECT MIN(per.StartDate) FROM periods per) AND (SELECT MAX(per.EndDate) FROM periods per)
                             AND il.M_Product_ID IS NOT NULL
                             AND pr.M_Product_Category_ID IS DISTINCT FROM v_PackingCat),
             amt AS (SELECT l.C_BPartner_ID,
                            l.M_Product_ID,
                            per.PeriodNo,
                            per.StartDate,
                            SUM(fa.AmtAcctCr - fa.AmtAcctDr) AS Amt
                     FROM inv_lines l
                              JOIN Fact_Acct fa ON fa.AD_Table_ID = v_InvoiceTable_ID
                         AND fa.Record_ID = l.C_Invoice_ID
                         AND fa.Line_ID = l.C_InvoiceLine_ID
                         AND fa.C_AcctSchema_ID = v_C_AcctSchema_ID
                         AND fa.PostingType = 'A'
                         AND fa.IsActive = 'Y'
                              JOIN periods per ON fa.DateAcct::date BETWEEN per.StartDate AND per.EndDate
                     GROUP BY l.C_BPartner_ID, l.M_Product_ID, per.PeriodNo, per.StartDate),
             qty AS (SELECT COALESCE(ord.Bill_BPartner_ID, ord.C_BPartner_ID, io.C_BPartner_ID)                  AS C_BPartner_ID, -- resolve shipment to the SAME Debitor (bill-to)
                            iol.M_Product_ID,
                            per.PeriodNo,
                            per.StartDate,
                            SUM(CASE WHEN io.MovementType = 'C-' THEN iol.MovementQty ELSE -iol.MovementQty END) AS Qty
                     FROM M_InOutLine iol
                              JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID
                              LEFT JOIN C_Order ord ON ord.C_Order_ID = io.C_Order_ID
                              LEFT JOIN C_DocType dt ON dt.C_DocType_ID = io.C_DocType_ID
                              JOIN M_Product pr ON pr.M_Product_ID = iol.M_Product_ID
                              JOIN periods per ON io.MovementDate::date BETWEEN per.StartDate AND per.EndDate
                     WHERE io.MovementType IN ('C-', 'C+')
                       AND (io.MovementType = 'C-' OR COALESCE(dt.DocSubType, '') NOT IN ('SR', 'EC'))
                       AND io.DocStatus IN ('CO', 'CL')
                       AND io.AD_Org_ID = p_AD_Org_ID
                       AND iol.IsPackagingMaterial = 'N'
                       AND iol.M_Product_ID IS NOT NULL
                       AND pr.M_Product_Category_ID IS DISTINCT FROM v_PackingCat
                     GROUP BY COALESCE(ord.Bill_BPartner_ID, ord.C_BPartner_ID, io.C_BPartner_ID), iol.M_Product_ID, per.PeriodNo, per.StartDate)
        SELECT COALESCE(q.C_BPartner_ID, a.C_BPartner_ID),
               COALESCE(q.M_Product_ID, a.M_Product_ID),
               COALESCE(q.PeriodNo, a.PeriodNo),
               COALESCE(q.StartDate, a.StartDate),
               q.Qty,
               a.Amt
        FROM qty q
                 FULL OUTER JOIN amt a
                                 ON a.C_BPartner_ID = q.C_BPartner_ID
                                     AND a.M_Product_ID = q.M_Product_ID
                                     AND a.PeriodNo = q.PeriodNo;
END
$$
;
