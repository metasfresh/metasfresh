CREATE OR REPLACE FUNCTION report.update_fresh_statistics_kg_week(
	)
    RETURNS boolean
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE 
AS $BODY$
BEGIN
 
DROP TABLE IF EXISTS  fresh_statistics_kg_week_MV;

DROP VIEW IF EXISTS fresh_statistics_kg_week_view
;

CREATE VIEW fresh_statistics_kg_week_view AS
SELECT fa.M_Product_ID,
       p.M_Product_Category_ID,
       Y.fiscalyear,
       y.C_Year_id,
       fa.dateacct,
       fa.AmtAcct,
       fa.qty,
       fa.C_Activity_ID,
       fa.ad_org_id,
       fa.iso_code,
       il.M_AttributeSetInstance_ID AS il_M_AttributeSetInstance_ID,
       fa.C_UOM_ID,
       fa.uomkg,
       fa.convQty,
       i.IsSOtrx,
       pc.value AS pc_value,
	   pc.name AS pc_name,
       p.Name AS P_name,
       p.value AS P_value
FROM C_Year Y
         LEFT OUTER JOIN
     (
         SELECT *
         FROM (
                  SELECT fa.*,
                         CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct,
                         uomconvert(fa.M_Product_ID, fa.C_UOM_ID, (SELECT C_UOM_ID AS uom_conv
                                                                   FROM C_UOM
                                                                   WHERE x12de355 = 'KGM'
                                                                     AND IsActive = 'Y'
                         ), qty
                             )                                                                             AS convQty,
                         (SELECT C_UOM_ID AS uom_conv
                          FROM C_UOM
                          WHERE x12de355 = 'KGM'
                            AND IsActive = 'Y'
                         )                                                                                 AS uomkg,
                         C.iso_code
                  FROM Fact_Acct fa
                           JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
                           INNER JOIN AD_Org o ON fa.ad_org_id = o.ad_org_id
                           INNER JOIN AD_ClientInfo ci ON o.AD_Client_ID = ci.ad_client_id
                           LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID
                           LEFT OUTER JOIN C_Currency C ON acs.C_Currency_ID = C.C_Currency_ID
                  WHERE AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'
                                                  )
                  )
                    AND fa.isActive = 'Y'
              ) fa
     ) fa ON TRUE
         INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
         INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
    /* Please note: This is an important implicit filter. Inner Joining the Product
     * filters Fact Acct records for e.g. Taxes
     */
         INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
         INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
WHERE fa.AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'
                                   )
)
  -- Akontozahlung invoices are not included. See FRESH_609
  AND i.C_DocType_ID NOT IN (SELECT C_DocType_ID
                             FROM C_DocType
                             WHERE docbasetype = 'API'
                               AND docsubtype = 'DP'
)
  -- It was a requirement to not have HU Packing material within the sums of the Statistics reports
  AND p.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
;

CREATE TABLE fresh_statistics_kg_week_MV
AS
SELECT *
FROM fresh_statistics_kg_week_view
ORDER BY pc_value, P_name
;

-- indices that shall improve ordering and filtering
CREATE INDEX fresh_statistics_kg_week_year_Index ON fresh_statistics_kg_week_MV (C_Year_ID, issotrx);
CREATE INDEX fresh_statistics_kg_week_p_name_pc_name ON fresh_statistics_kg_week_MV (pc_value, P_name, C_Year_ID, issotrx);


 return true;
END;
$BODY$;


COMMENT ON FUNCTION report.update_fresh_statistics_kg_week()
    IS 'This function drops the table fresh_statistics_kg_week_MV, recreates the view fresh_statistics_kg_week_view and recreates the table based on that view.
	It will be called once every night via a scheduler.';
	
	-- Select report.update_fresh_statistics_kg_week();