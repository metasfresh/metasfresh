CREATE OR REPLACE FUNCTION report.update_fresh_statistics_kg()
    RETURNS boolean
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE 
AS $BODY$

DECLARE v_Periods RECORD;
BEGIN
 
        DROP TABLE IF EXISTS fresh_statistics_kg_MV;

        CREATE TABLE fresh_statistics_kg_MV
        AS
        SELECT fa.M_Product_ID,
               fa.C_Period_ID,
               fa.org_id,
               fa.iso_code,
               fa.C_UOM_ID,
               fa.C_Activity_ID,
               il_M_AttributeSetInstance_ID,
               il_AD_Client_ID,
               il_AD_Org_ID,
               M_Product_Category_ID,
               i_IsSOtrx,
               SUM(ABS(qty) * SIGN(AmtAcct)) AS QtySum,
               SUM(fa.AmtAcct)               AS TotalAmt


        FROM (
                 SELECT fa.ad_org_id                                                                      AS org_id,
                        fa.C_Period_ID,
                        fa.qty,
                        fa.C_UOM_ID,
                        fa.C_Activity_ID,
                        fa.M_Product_ID,
                        CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct,
                        c.iso_code,
                        i.IsSOtrx                                                                         AS i_IsSOtrx,
                        il.M_AttributeSetInstance_ID                                                      AS il_M_AttributeSetInstance_ID,
                        il.AD_Client_ID                                                                   AS il_AD_Client_ID,
                        il.AD_Org_ID                                                                      AS il_AD_Org_ID,
                        p.M_Product_Category_ID                                                           AS M_Product_Category_ID
                 FROM   Fact_Acct fa
                          INNER JOIN C_Invoice i
                               ON fa.Record_ID = i.C_Invoice_ID 
                          INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID
                          /* Please note: This is an important implicit filter. Inner Joining the Product
                            * filters Fact Acct records for e.g. Taxes
                            */
                          INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
                          INNER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = fa.C_AcctSchema_ID
                          INNER JOIN C_Currency C ON acs.C_Currency_ID = C.C_Currency_ID
                 WHERE AD_Table_ID = Get_Table_ID('C_Invoice')
                   
                   -- Akontozahlung invoices are not included. See FRESH_609
                   AND i.C_DocType_ID NOT IN (SELECT C_DocType_ID
                                              FROM C_DocType
                                              WHERE docbasetype = 'API'
                                                AND docsubtype = 'DP')
             ) fa
        GROUP BY fa.M_Product_ID,
                 fa.C_Period_ID,
                 fa.org_id,
                 fa.iso_code,
                 fa.C_UOM_ID,
                 fa.C_Activity_ID,
                 il_M_AttributeSetInstance_ID,
                 il_AD_Client_ID,
                 il_AD_Org_ID,
                 M_Product_Category_ID,
                 i_IsSOtrx;


        -- indices that shall improve filtering
        CREATE INDEX fresh_statistics_kg_period_Index ON fresh_statistics_kg_MV (C_Period_ID, i_issotrx);

        CREATE INDEX fresh_statistics_kg_product_id ON fresh_statistics_kg_MV (M_Product_ID);

 return true;
END;
$BODY$;


COMMENT ON FUNCTION report.update_fresh_statistics_kg_week()
    IS 'This function drops the table fresh_statistics_kg_week_MV, recreates the view fresh_statistics_kg_week_view and recreates the table based on that view.
	It will be called once every night via a scheduler.';
	
	-- Select report.update_fresh_statistics_kg_week();