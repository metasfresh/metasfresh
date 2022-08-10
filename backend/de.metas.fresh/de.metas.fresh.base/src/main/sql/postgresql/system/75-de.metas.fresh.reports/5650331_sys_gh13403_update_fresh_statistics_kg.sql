CREATE OR REPLACE FUNCTION report.update_fresh_statistics_kg
	)
    RETURNS boolean
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE 
AS $BODY$
BEGIN
 
DROP TABLE IF EXISTS  fresh_statistics_kg_MV;

DROP VIEW IF EXISTS fresh_statistics_kg_view;
;

CREATE OR REPLACE VIEW fresh_statistics_kg_view AS
SELECT fa.M_Product_ID,
       p1.isActive                  AS p1_isActive,
       p1.EndDate                   AS p1_EndDate,
       p1.StartDate                 AS p1_StartDate,
       p2.StartDate                 AS p2_StartDate,
       p3.StartDate                 AS p3_StartDate,
       p4.StartDate                 AS p4_StartDate,
       p5.StartDate                 AS p5_StartDate,
       p6.StartDate                 AS p6_StartDate,
       p7.StartDate                 AS p7_StartDate,
       p8.StartDate                 AS p8_StartDate,
       p9.StartDate                 AS p9_StartDate,
       p10.StartDate                AS p10_StartDate,
       p11.StartDate                AS p11_StartDate,
       p12.StartDate                AS p12_StartDate,
       fa.C_Period_ID               AS fa_C_Period_ID,
       p1.C_Period_ID               AS p1_C_Period_ID,
       p2.C_Period_ID               AS p2_C_Period_ID,
       p3.C_Period_ID               AS p3_C_Period_ID,
       p4.C_Period_ID               AS p4_C_Period_ID,
       p5.C_Period_ID               AS p5_C_Period_ID,
       p6.C_Period_ID               AS p6_C_Period_ID,
       p7.C_Period_ID               AS p7_C_Period_ID,
       p8.C_Period_ID               AS p8_C_Period_ID,
       p9.C_Period_ID               AS p9_C_Period_ID,
       p10.C_Period_ID              AS p10_C_Period_ID,
       p11.C_Period_ID              AS p11_C_Period_ID,
       p12.C_Period_ID              AS p12_C_Period_ID,
       fa.org_id,
       fa.iso_code,
       fa.C_UOM_ID,
       fa.C_Activity_ID,
       uomkg,
       convQty,
       AmtAcct,
       qty,
       il.M_AttributeSetInstance_ID AS il_M_AttributeSetInstance_ID,
       il.AD_Client_ID              AS il_AD_Client_ID,
       il.AD_Org_ID                 AS il_AD_Org_ID,
       p.M_Product_ID               AS p_M_Product_ID,
       p.M_Product_Category_ID      AS p_M_Product_Category_ID,
       i.IsSOtrx                    AS i_IsSOtrx

FROM C_Period p1
         LEFT OUTER JOIN C_Period p2 ON p2.C_Period_ID = report.fresh_Get_Predecessor_Period(p1.C_Period_ID) AND p2.isActive = 'Y'
         LEFT OUTER JOIN C_Period p3 ON p3.C_Period_ID = report.fresh_Get_Predecessor_Period(p2.C_Period_ID) AND p3.isActive = 'Y'
         LEFT OUTER JOIN C_Period p4 ON p4.C_Period_ID = report.fresh_Get_Predecessor_Period(p3.C_Period_ID) AND p4.isActive = 'Y'
         LEFT OUTER JOIN C_Period p5 ON p5.C_Period_ID = report.fresh_Get_Predecessor_Period(p4.C_Period_ID) AND p5.isActive = 'Y'
         LEFT OUTER JOIN C_Period p6 ON p6.C_Period_ID = report.fresh_Get_Predecessor_Period(p5.C_Period_ID) AND p6.isActive = 'Y'
         LEFT OUTER JOIN C_Period p7 ON p7.C_Period_ID = report.fresh_Get_Predecessor_Period(p6.C_Period_ID) AND p7.isActive = 'Y'
         LEFT OUTER JOIN C_Period p8 ON p8.C_Period_ID = report.fresh_Get_Predecessor_Period(p7.C_Period_ID) AND p8.isActive = 'Y'
         LEFT OUTER JOIN C_Period p9 ON p9.C_Period_ID = report.fresh_Get_Predecessor_Period(p8.C_Period_ID) AND p9.isActive = 'Y'
         LEFT OUTER JOIN C_Period p10 ON p10.C_Period_ID = report.fresh_Get_Predecessor_Period(p9.C_Period_ID) AND p10.isActive = 'Y'
         LEFT OUTER JOIN C_Period p11 ON p11.C_Period_ID = report.fresh_Get_Predecessor_Period(p10.C_Period_ID) AND p11.isActive = 'Y'
         LEFT OUTER JOIN C_Period p12 ON p12.C_Period_ID = report.fresh_Get_Predecessor_Period(p11.C_Period_ID) AND p12.isActive = 'Y'

         LEFT OUTER JOIN
     (
         SELECT *
         FROM (
                  SELECT fa.*, fa.ad_org_id AS org_id, CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct, uomconvert(fa.M_Product_ID, fa.C_UOM_ID, (SELECT C_UOM_ID AS uom_conv FROM C_UOM WHERE x12de355 = 'KGM' AND IsActive = 'Y'), qty) AS convQty, (SELECT C_UOM_ID AS uom_conv FROM C_UOM WHERE x12de355 = 'KGM' AND IsActive = 'Y') AS uomkg, c.iso_code
                  FROM Fact_Acct fa
                           JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
                           INNER JOIN AD_Org o ON fa.ad_org_id = o.ad_org_id
                           INNER JOIN AD_ClientInfo ci ON o.AD_Client_ID = ci.ad_client_id
                           LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID
                           LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID = c.C_Currency_ID
                  WHERE AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
                    AND fa.isActive = 'Y'
              ) fa
     ) fa ON TRUE

         INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
         INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
    /* Please note: This is an important implicit filter. Inner Joining the Product
     * filters Fact Acct records for e.g. Taxes
     */
         INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
WHERE fa.AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))

  -- Akontozahlung invoices are not included. See FRESH_609
  AND i.C_DocType_ID NOT IN (SELECT C_DocType_ID FROM C_DocType WHERE docbasetype = 'API' AND docsubtype = 'DP')
;


  
CREATE TABLE fresh_statistics_kg_MV
AS
SELECT *
FROM fresh_statistics_kg_view;



-- indices that shall improve filtering
CREATE INDEX fresh_statistics_kg_period_Index ON fresh_statistics_kg_MV (p1_C_Period_ID, i_issotrx);
CREATE INDEX fresh_statistics_kg_product_id ON fresh_statistics_kg_MV (M_Product_ID);



 return true;
END;
$BODY$;


COMMENT ON FUNCTION report.update_fresh_statistics_kg_week()
    IS 'This function drops the table fresh_statistics_kg_week_MV, recreates the view fresh_statistics_kg_week_view and recreates the table based on that view.
	It will be called once every night via a scheduler.';
	
	-- Select report.update_fresh_statistics_kg_week();