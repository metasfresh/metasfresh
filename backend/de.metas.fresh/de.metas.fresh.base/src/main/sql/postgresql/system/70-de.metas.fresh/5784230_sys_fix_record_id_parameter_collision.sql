-- Title: Fix record_id parameter naming collision in report functions
-- Description: Rename 'record_id' parameter to 'p_record_id' to prevent collision
--              with table columns named 'record_id' (e.g., M_ReceiptSchedule.record_id,
--              C_Invoice_Candidate.record_id). This bug caused reports to return wrong data.
-- Issue: https://github.com/metasfresh/mf15/issues/XXXX
-- 2026-01-17T22:00:00
-- Task: Fix SQL function parameter/column name collision bug

-- ===========================================================================
-- 1. Docs_Manufacturing_Order_Details
-- ===========================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Details(IN numeric, IN numeric, IN Character Varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Details(IN p_record_id      numeric,
                                                                                               IN p_m_attribute_id numeric,
                                                                                               IN p_ad_language    character Varying)

    RETURNS TABLE
            (
                line            numeric,
                qtyrequiered    numeric,
                uomsymbol       character varying,
                value           character varying,
                vendorProductNo character varying,
                description     character varying,
                productName     character varying,
                attributes      character varying
            )
AS
$$

SELECT line,
       qtyrequiered,
       COALESCE(uom.UOMSymbol, uomt.UOMSymbol) AS UOMSymbol,
       p.value,
       coalesce(bpp.productno, p.value)        AS vendorProductNo,
       coalesce(pt.description, p.description) AS description,
       coalesce(pt.Name, p.Name)               AS productName,
       Attributes.attributes_value
FROM PP_Order_BOMLine bomLine

         -- Product and its translation
         JOIN M_Product p ON bomLine.m_product_id = p.m_product_id
         LEFT OUTER JOIN M_Product_Trl pt ON bomLine.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language AND pt.isActive = 'Y'
    -- Unit of measurement and its translation
         JOIN c_uom uom ON bomLine.c_uom_id = uom.c_uom_id
         LEFT OUTER JOIN C_UOM_Trl uomt ON bomLine.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_ad_language
         LEFT JOIN getc_bpartner_product_vendor(p.m_product_id) bpp ON 1 = 1
         LEFT JOIN de_metas_endcustomer_fresh_reports.get_hu_attribute_value_for_pp_order_and_pp_order_bomline(p_m_attribute_id, bomLine.pp_order_id, bomLine.pp_order_bomline_id) AS Attributes ON 1 = 1
WHERE bomLine.PP_Order_ID = p_record_id
  AND bomLine.isActive = 'Y'
  AND bomLine.componenttype != 'BY'
ORDER BY line
$$
    LANGUAGE sql
    STABLE
;

-- ===========================================================================
-- 2. Docs_Manufacturing_Order_Description
-- ===========================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Description(IN NUMERIC, IN NUMERIC, IN CHARACTER VARYING(6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Description(IN p_record_id      NUMERIC,
                                                                                                   IN p_m_attribute_id NUMERIC,
                                                                                                   IN p_ad_language    CHARACTER VARYING(6))

    RETURNS TABLE
            (
                documentno    CHARACTER VARYING(30),
                dateordered   TIMESTAMP WITH TIME ZONE,
                datepromised  TIMESTAMP WITH TIME ZONE,
                VALUE         CHARACTER VARYING(60),
                NAME          CHARACTER VARYING(255),
                PrintName     CHARACTER VARYING(60),
                attributes    CHARACTER VARYING,
                co_documentno CHARACTER VARYING,
                qty           NUMERIC,
                bpName        CHARACTER VARYING(255)
            )
AS
$$

SELECT pp.DocumentNo,
       pp.DateOrdered,
       pp.datepromised,
       pbom.value,
       COALESCE(pt.name, pbom.name)          AS name,
       COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
       Attributes.attributes_value,
       o.documentno                          AS co_documentno,
       pp.qtyentered,
       bp.name                               AS bpName
FROM PP_Order pp
         JOIN PP_Product_BOM bom ON pp.PP_Product_BOM_ID = bom.PP_Product_BOM_ID
         LEFT JOIN c_bpartner bp ON pp.c_bpartner_id = bp.c_bpartner_id
         LEFT JOIN c_orderline ol ON pp.c_orderline_id = ol.c_orderline_id
         LEFT JOIN c_order o ON ol.c_order_id = o.c_order_id
         LEFT JOIN de_metas_endcustomer_fresh_reports.get_hu_attribute_value_for_pp_order_and_pp_order_bomline(p_m_attribute_id, pp.pp_order_id, NULL) AS Attributes ON 1 = 1

    -- Product and its translation
         JOIN M_product pbom ON bom.m_product_id = pbom.m_product_id
         LEFT JOIN M_Product_Trl pt ON bom.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language

         LEFT JOIN C_DocType dt ON pp.C_DocTypeTarget_ID = dt.C_DocType_ID
         LEFT JOIN C_DocType_Trl dtt
                   ON pp.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_ad_language
WHERE pp.PP_Order_ID = p_record_id
$$
    LANGUAGE SQL
    STABLE
;

-- ===========================================================================
-- 3. Docs_Purchase_Order_Description
-- ===========================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Description(IN numeric, IN Character Varying(6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Description(p_record_id numeric,
                                                                                              p_language  character varying)
    RETURNS TABLE
            (
                description        character varying,
                documentno         character varying,
                reference          text,
                dateordered        timestamp WITHOUT TIME ZONE,
                datepromised       timestamp WITH TIME ZONE,
                deliverto          character varying,
                bp_value           character varying,
                eori               character varying,
                customernoatvendor character varying,
                cont_name          text,
                cont_phone         character varying,
                cont_fax           character varying,
                cont_email         character varying,
                sr_name            text,
                sr_phone           character varying,
                sr_fax             character varying,
                sr_email           character varying,
                printname          character varying,
                billtoaddress      character varying,
                incoterms          character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT o.description                         AS description,
       o.documentno                          AS documentno,
       TRIM(o.poreference)                   AS reference,
       o.dateordered                         AS dateordered,
       o.datepromised                        AS datepromised,
       CASE
           WHEN report.IsHiddenReportElement(o.C_DocType_ID, 'Delivery_To_Address') = 'N' THEN
               REPLACE(
                       REPLACE(o.DeliveryToAddress, E'\r\n', ' | '),
                       E'\n', ' | '
               )
       END                                                                              AS deliverto,
       bp.value                              AS bp_value,
       bp.eori                               AS eori,
       bp.customernoatvendor                 AS customernoatvendor,
       COALESCE(cogr.name, '') ||
       COALESCE(' ' || cont.title, '') ||
       COALESCE(' ' || cont.firstName, '') ||
       COALESCE(' ' || cont.lastName, '')    AS cont_name,
       cont.phone                            AS cont_phone,
       cont.fax                              AS cont_fax,
       cont.email                            AS cont_email,
       TRIM(
               COALESCE(srgr.name, '') ||
               COALESCE(' ' || srep.title, '') ||
               COALESCE(' ' || srep.firstName, '') ||
               COALESCE(' ' || srep.lastName, '')
       )                                     AS sr_name,
       srep.phone                            AS sr_phone,
       srep.fax                              AS sr_fax,
       srep.email                            AS sr_email,
       COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
       CASE
           WHEN report.IsHiddenReportElement(o.C_DocType_ID, 'Bill_To_Address') = 'N' THEN
               REPLACE(
                       REPLACE(o.billtoaddress, E'\r\n', ' | '),
                       E'\n', ' | '
               )
       END                                                                              AS billtoaddress,
       inc.value                             AS incoterms
FROM C_Order o
         INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN AD_User srep ON o.SalesRep_ID = srep.AD_User_ID
         LEFT OUTER JOIN AD_User cont ON o.AD_User_ID = cont.AD_User_ID
         LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID
         LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID
         LEFT OUTER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON o.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language
         LEFT OUTER JOIN C_Incoterms inc ON o.c_incoterms_id = inc.c_incoterms_id

WHERE o.c_order_id = p_record_id
$$
;

-- ===========================================================================
-- 4. Docs_Sales_InOut_Description
-- ===========================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description(IN numeric, IN Character Varying(6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description(p_record_id numeric,
                                                                                           p_language  character varying)
    RETURNS TABLE
            (
                description        character varying,
                documentno         character varying,
                movementdate       timestamp WITHOUT TIME ZONE,
                reference          character varying,
                bp_value           character varying,
                vataxid            character varying,
                eori               character varying,
                customernoatvendor character varying,
                cont_name          text,
                cont_phone         character varying,
                cont_fax           character varying,
                cont_email         character varying,
                sr_name            text,
                sr_phone           character varying,
                sr_fax             character varying,
                sr_email           character varying,
                printname          character varying,
                order_docno        text,
                order_date         text,
                offer_documentno   character varying,
                offer_date         text,
                billtoaddress      character varying,
                PreparationDate    text,
                docstatus          char(2),
                delivery_week_year character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT io.description                        AS description,
       io.documentno                         AS documentno,
       io.movementdate                       AS movementdate,
       io.poreference                        AS reference,
       bp.value                              AS bp_value,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'VATaxID') = 'N' THEN
               bp.VATaxID
       END                                   AS VATaxID,
       bp.eori                               AS eori,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Customer_No_At_Vendor') = 'N' THEN
               bp.customernoatvendor
       END                                   AS customernoatvendor,
       COALESCE(cogr.name, '') ||
       COALESCE(' ' || cont.title, '') ||
       COALESCE(' ' || cont.firstName, '') ||
       COALESCE(' ' || cont.lastName, '')    AS cont_name,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Contact_Phone') = 'N' THEN
               cont.phone
       END                                   AS cont_phone,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Contact_Fax') = 'N' THEN
               cont.fax
       END                                   AS cont_fax,
       cont.email                            AS cont_email,
       COALESCE(srgr.name, '') ||
       COALESCE(' ' || srep.title, '') ||
       COALESCE(' ' || srep.firstName, '') ||
       COALESCE(' ' || srep.lastName, '')    AS sr_name,
       srep.phone                            AS sr_phone,
       srep.fax                              AS sr_fax,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'SalesRep_Email') = 'N' THEN
               srep.email
       END                                   AS sr_email,
       COALESCE(dtt.printname, dt.printname) AS printname,
       o.docno                               AS order_docno,
       o.dateordered                         AS order_date,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Offer_DocumentNo') = 'N' THEN
               o.offer_documentno
       END                                   AS offer_documentno,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Offer_Date') = 'N' THEN
               o.offer_date
       END                                   AS offer_date,
       o.billtoaddress,
       o.PreparationDate,
       io.docstatus,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Delivery_Week_Year') = 'N' THEN
               TO_CHAR(io.MovementDate, 'WW') || '.' || TO_CHAR(io.MovementDate, 'YY')
       END                                   AS delivery_week_year
FROM m_inout io
         INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language
         INNER JOIN c_bpartner bp ON io.c_bpartner_id = bp.c_bpartner_id
         LEFT OUTER JOIN AD_User srep ON io.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100
         LEFT OUTER JOIN AD_User cont ON io.AD_User_ID = cont.AD_User_ID
         LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID
         LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID

         LEFT JOIN LATERAL
    (
    SELECT First_Agg(o.DocumentNo ORDER BY o.DocumentNo) ||
           CASE WHEN COUNT(DISTINCT o.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo,


           First_Agg(o.dateordered::text ORDER BY o.dateordered) ||
           CASE WHEN COUNT(o.dateordered) > 1 THEN ' ff.' ELSE '' END         AS dateordered,

           First_Agg(offer.DocumentNo ORDER BY offer.DocumentNo) ||
           CASE WHEN COUNT(offer.DocumentNo) > 1 THEN ' ff.' ELSE '' END      AS offer_documentno,

           First_Agg(offer.dateordered::text ORDER BY offer.dateordered) ||
           CASE WHEN COUNT(offer.dateordered) > 1 THEN ' ff.' ELSE '' END     AS offer_date,

           First_Agg(o.dateordered::text ORDER BY o.PreparationDate) ||
           CASE WHEN COUNT(o.dateordered) > 1 THEN ' ff.' ELSE '' END         AS PreparationDate,


           REPLACE(
                   REPLACE(o.billtoaddress, E'\r\n', ' | '),
                   E'\n', ' | '
           )                                                                  AS billtoaddress
    FROM M_InOutLine iol
             JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
             JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
             LEFT JOIN C_Order offer ON offer.C_Order_ID = o.ref_proposal_id

    WHERE iol.M_InOut_ID = p_record_id
    GROUP BY o.billtoaddress
    ) o ON TRUE
WHERE io.m_inout_id = p_record_id
$$
;

-- ===========================================================================
-- 5. Docs_Sales_InOut_Sum_Weight
-- Note: This function has a dependent view (de_metas_endcustomer_fresh_reports.m_inout_v)
--       We need to drop the view first, then drop/recreate the function, then recreate the view.
-- ===========================================================================

-- Step 5a: Drop the dependent view
DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.m_inout_v;

-- Step 5b: Drop and recreate the function with new parameter name
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Sum_Weight(IN numeric, IN Character Varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Sum_Weight(IN p_Record_ID   numeric,
                                                                               IN p_AD_Language Character Varying)
    RETURNS TABLE
            (
                catchweight numeric,
                weight_uom  character varying
            )
AS
$$
SELECT SUM(
               COALESCE((COALESCE(qtydeliveredcatch,
                                  uomConvert(iol.M_Product_ID,
                                             iol.C_UOM_ID,
                                             (SELECT c_uom_Id FROM C_uom WHERE isactive = 'Y' AND x12de355 = 'KGM'),
                                             qtyentered))),
                        iol.qtyentered * p.weight)) AS catchweight,
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol)      AS weight_uom
FROM M_InOutline iol
         LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = COALESCE(iol.catch_uom_id, (SELECT c_uom_Id FROM C_uom WHERE isactive = 'Y' AND x12de355 = 'KGM'))
         LEFT OUTER JOIN C_UOM_Trl uomt ON uomt.c_UOM_ID = uom.C_UOM_ID AND uomt.AD_Language = p_AD_Language
         INNER JOIN M_Product p ON p.M_Product_ID = iol.M_Product_ID
WHERE iol.m_inout_id = p_Record_ID
GROUP BY uomt.UOMSymbol, uom.UOMSymbol, iol.m_inout_id

$$
    LANGUAGE sql
    STABLE
;

-- Step 5c: Recreate the dependent view using db_alter_view
-- Source: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/views/M_InOut_V.sql
SELECT db_alter_view('de_metas_endcustomer_fresh_reports.M_InOut_V',
$view_def$
select commoditynumber,
       productName,
       productDescription,
       deliveredFromCountry,
       deliveryCountry,
       OriginCountry,
       sum(movementqty) as movementqty,
       UOMSymbol,
       sum(grandtotal)  as grandtotal,
       cursymbol,
       C_Period_ID,
       Period,
       AD_Org_ID,
       OrgName,
       CustomsTariff,
       weight,
       vataxid,
       c_year_id
from (
         select cn.value        as commoditynumber,
                p.Name          as productName,
                p.description   as productDescription,
                wlc.countrycode as deliveredFromCountry,
                co.countrycode  as deliveryCountry,
                pco.countrycode as OriginCountry,

                coalesce((case
                              when qtydeliveredcatch is not null
                                  then qtydeliveredcatch
                              when uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (select C_UOM_ID
                                                                               from C_UOM
                                                                               where x12de355 = 'KGM' and isactive = 'Y'
                                                                               order by isdefault desc
                                                                               limit 1), iol.qtyentered) is not null
                                  then uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (select C_UOM_ID
                                                                                   from C_UOM
                                                                                   where x12de355 = 'KGM'
                                                                                     and isactive = 'Y'
                                                                                   order by isdefault desc
                                                                                   limit 1),
                                                  iol.qtyentered) -- hard coded UOM for KG
                              else iol.qtyentered
                    end), 0)    as movementqty,

                (case
                     when uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (select C_UOM_ID
                                                                      from C_UOM
                                                                      where x12de355 = 'KGM' and isactive = 'Y'
                                                                      order by isdefault desc
                                                                      limit 1), iol.qtyentered) is not null
                         then uom.UOMSymbol
                     else uom_iol.UOMSymbol
                    end)
                                as UOMSymbol,

                il.linenetamt   as grandtotal,
                c.cursymbol,
                C_Period_ID,
                per.name        as Period,
                io.AD_Org_ID,
                o.Name          as OrgName,
                ct.value           AS CustomsTariff,
                iow.catchweight    AS weight,
                bp.vataxid,
                per.c_year_id
         from M_InOut io
                  join AD_Org o on io.ad_org_id = o.ad_org_id
                  left join m_warehouse w on w.m_warehouse_id = io.m_warehouse_id
                  left join c_location wl on wl.c_location_id = w.c_location_id
                  left join C_country wlc on wlc.c_country_id = wl.c_country_id
                  join m_inoutline iol on iol.m_inout_id = io.m_inout_id
                  left join c_invoiceline il on il.m_inoutline_id = iol.m_inoutline_id
                  join C_invoice i on i.C_invoice_id = il.C_invoice_id and i.DocStatus IN ('CO', 'CL')
                  join m_product p on p.m_product_id = iol.m_product_id
                  left join m_commoditynumber cn on cn.m_commoditynumber_id = p.m_commoditynumber_id
                  left join C_country pco on pco.c_country_id = p.rawmaterialorigin_id
                  JOIN c_bpartner bp ON bp.c_bpartner_id = io.c_bpartner_id
                  join c_bpartner_location bpl on bpl.c_bpartner_location_id = io.c_bpartner_location_id
                  join c_location l on l.c_location_id = bpl.c_location_id
                  join C_country co on co.c_country_id = l.c_country_id
                  join c_currency c on i.c_currency_id = c.C_Currency_id

                  LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = coalesce(iol.catch_uom_id, (select C_UOM_ID
                                                                                          from C_UOM
                                                                                          where x12de355 = 'KGM'
                                                                                            and isactive = 'Y'
                                                                                          order by isdefault desc
                                                                                          limit 1)) -- fallback to KG
                  LEFT OUTER JOIN C_UOM uom_iol ON uom_iol.C_UOM_ID = iol.C_UOM_ID

                  JOIN C_Period per on i.dateinvoiced >= per.startdate and i.dateinvoiced <= per.enddate

                  LEFT OUTER JOIN M_CustomsTariff ct ON ct.M_CustomsTariff_ID = p.M_CustomsTariff_ID
                  LEFT OUTER JOIN de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Sum_Weight(io.m_inout_id, 'de_DE') AS iow ON TRUE

         where io.issotrx = 'Y'
           and io.isactive = 'Y'
           and iol.ispackagingmaterial = 'N'
           and wlc.countrycode = 'DE'
           and co.countrycode != 'DE'
     ) as v
group by commoditynumber,
         productName,
         productDescription,
         deliveredFromCountry,
         deliveryCountry,
         OriginCountry,
         UOMSymbol,
         cursymbol,
         C_Period_ID,
         Period,
         AD_Org_ID,
         OrgName,
         CustomsTariff,
         weight,
         vataxid,
         c_year_id
order by deliveryCountry, commoditynumber
$view_def$
);

-- ===========================================================================
-- 6. Docs_Sales_Invoice_Description
-- ===========================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_sales_invoice_description(IN numeric, IN Character Varying(6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_invoice_description(p_record_id numeric,
                                                                                             p_language  character varying)
    RETURNS TABLE
            (
                description        character varying,
                documentno         character varying,
                reference          character varying,
                dateinvoiced       timestamp WITHOUT TIME ZONE,
                duedate            timestamp WITH TIME ZONE,
                vataxid            character varying,
                bp_value           character varying,
                eori               character varying,
                customernoatvendor character varying,
                cont_name          text,
                cont_phone         character varying,
                cont_fax           character varying,
                cont_email         character varying,
                sr_name            text,
                sr_phone           character varying,
                sr_fax             character varying,
                sr_email           character varying,
                printname          character varying,
                order_docno        text,
                order_date         text,
                inout_docno        text,
                io_movementdate    date,
                iscreditmemo       character,
                creditmemo_docno   character varying,
                offer_documentno   character varying,
                offer_date         text,
                warehouse          character varying,
                projectno          character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT i.description                                                                    AS description,
       i.documentno                                                                     AS documentno,
       i.poreference                                                                    AS reference,
       i.dateinvoiced                                                                   AS dateinvoiced,
       paymenttermduedate(i.C_PaymentTerm_ID, i.DateInvoiced::timestamp WITH TIME ZONE) AS DueDate,
       CASE
           WHEN report.IsHiddenReportElement(i.C_DocType_ID, 'VATaxID') = 'N' THEN
               bp.VATaxID
       END                                                                              AS VATaxID,
       bp.value                                                                         AS bp_value,
       bp.eori                                                                          AS eori,
       CASE
           WHEN report.IsHiddenReportElement(i.C_DocType_ID, 'Customer_No_At_Vendor') = 'N' THEN
               bp.customernoatvendor
       END                                                                              AS customernoatvendor,
       COALESCE(cogr.name, '') ||
       COALESCE(' ' || cont.title, '') ||
       COALESCE(' ' || cont.firstName, '') ||
       COALESCE(' ' || cont.lastName, '')                                               AS cont_name,
       CASE
           WHEN report.IsHiddenReportElement(i.C_DocType_ID, 'Contact_Phone') = 'N' THEN
               cont.phone
       END                                                                              AS cont_phone,
       CASE
           WHEN report.IsHiddenReportElement(i.C_DocType_ID, 'Contact_Fax') = 'N' THEN
               cont.fax
       END                                                                              AS cont_fax,
       cont.email                                                                       AS cont_email,
       CASE
           WHEN report.IsHiddenReportElement(i.C_DocType_ID, 'SalesRep_Name') = 'N' THEN
               COALESCE(srgr.name, '') ||
               COALESCE(' ' || srep.title, '') ||
               COALESCE(' ' || srep.firstName, '') ||
               COALESCE(' ' || srep.lastName, '')
       END                                                                              AS sr_name,
       srep.phone                                                                       AS sr_phone,
       srep.fax                                                                         AS sr_fax,
       CASE
           WHEN report.IsHiddenReportElement(i.C_DocType_ID, 'SalesRep_Email') = 'N' THEN
               srep.email
       END                                                                              AS sr_email,
       COALESCE(dtt.PrintName, dt.PrintName)                                            AS PrintName,
       o.docno                                                                          AS order_docno,
       o.dateordered                                                                    AS order_date,
       io.docno                                                                         AS inout_docno,
       io.DateFrom                                                                      AS io_movementdate,
       CASE
           WHEN dt.docbasetype = 'ARC'
               THEN 'Y'
               ELSE 'N'
       END                                                                              AS isCreditMemo,
       cm.documentno                                                                    AS creditmemo_docNo,
       CASE
           WHEN report.IsHiddenReportElement(i.C_DocType_ID, 'Offer_DocumentNo') = 'N' THEN
               o.offer_documentno
       END                                                                              AS offer_documentno,
       CASE
           WHEN report.IsHiddenReportElement(i.C_DocType_ID, 'Offer_Date') = 'N' THEN
               o.offer_date
       END                                                                              AS offer_date,
       wh.name                                                                          AS warehouse,
       pr.value                                                                         AS projectno
FROM C_Invoice i
         JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
         LEFT JOIN AD_User srep ON i.SalesRep_ID = srep.AD_User_ID
         LEFT JOIN AD_User cont ON i.AD_User_ID = cont.AD_User_ID
         LEFT JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID
         LEFT JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID
         LEFT JOIN C_Invoice cm ON cm.C_Invoice_id = i.ref_invoice_id
         LEFT OUTER JOIN C_DocType dt ON i.C_DocTypeTarget_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON i.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language
         LEFT JOIN LATERAL
    (
    SELECT First_Agg(o.DocumentNo ORDER BY o.DocumentNo) ||
           CASE WHEN COUNT(o.documentNo) > 1 THEN ' ff.' ELSE '' END      AS DocNo,

           First_Agg((o.dateordered::date)::text ORDER BY o.dateordered) ||
           CASE WHEN COUNT(o.dateordered) > 1 THEN ' ff.' ELSE '' END     AS dateordered,

           First_Agg(offer.DocumentNo ORDER BY offer.DocumentNo) ||
           CASE WHEN COUNT(offer.DocumentNo) > 1 THEN ' ff.' ELSE '' END  AS offer_documentno,

           First_Agg(offer.dateordered::text ORDER BY offer.dateordered) ||
           CASE WHEN COUNT(offer.dateordered) > 1 THEN ' ff.' ELSE '' END AS offer_date
    FROM C_InvoiceLine il
             JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
             JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID

        -- proposal order
             LEFT JOIN C_Order offer ON offer.C_Order_ID = o.ref_proposal_id

    WHERE il.C_Invoice_ID = p_record_id
    ) o ON TRUE

         LEFT JOIN LATERAL
    (
    SELECT First_Agg(io.DocumentNo ORDER BY io.DocumentNo) ||
           CASE WHEN COUNT(io.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo,
           MIN(io.MovementDate)::date                                 AS DateFrom
    FROM C_InvoiceLine il
             JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID
             JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID

    WHERE il.C_Invoice_ID = p_record_id
    ) io ON TRUE

    -- warehouse
         LEFT JOIN m_warehouse wh ON i.m_warehouse_id = wh.m_warehouse_id
    -- project
         LEFT JOIN c_project pr ON i.c_project_id = pr.c_project_id
WHERE i.C_Invoice_ID = p_record_id

$$
;

-- ===========================================================================
-- 7. Docs_Sales_Order_Description
-- ===========================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Description(IN numeric, IN Character Varying(6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Description(p_record_id   numeric,
                                                                                           p_ad_language character varying)
    RETURNS TABLE
            (
                description       character varying,
                documentno        character varying,
                dateordered       timestamp WITHOUT TIME ZONE,
                reference         text,
                isoffer           character,
                isprepay          character,
                offervaliddate    timestamp WITHOUT TIME ZONE,
                offervaliddays    numeric,
                bp_value          character varying,
                eori              character varying,
                vataxid           character varying,
                cont_name         text,
                cont_phone        character varying,
                cont_fax          character varying,
                cont_email        character varying,
                sr_name           text,
                sr_phone          character varying,
                sr_fax            character varying,
                sr_email          character varying,
                printname         character varying,
                datepromised      timestamp WITH TIME ZONE,
                dt_description    text,
                offer_documentno  character varying,
                offer_date        timestamp WITHOUT TIME ZONE,
                deliverytoaddress character varying,
                validuntil        timestamp,
                versionno         character varying,
                warehouse         character varying,
                projectno         character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT o.description                             AS description,
       o.documentno                              AS documentno,
       o.dateordered                             AS dateordered,
       o.poreference                             AS reference,
       CASE
           WHEN dt.docbasetype = 'SOO' AND dt.docsubtype IN ('ON', 'OB')
               THEN 'Y'
               ELSE 'N'
       END                                       AS isoffer,
       CASE
           WHEN dt.docbasetype = 'SOO' AND dt.docsubtype = 'PR'
               THEN 'Y'
               ELSE 'N'
       END                                       AS isprepay,
       o.offervaliddate,
       o.offervaliddays,
       bp.value                                  AS bp_value,
       bp.eori                                   AS eori,
       bp.vataxid                                AS vataxid,
       LTRIM(COALESCE(cogrt.name, cogrt.name, '') ||
             COALESCE(' ' || cont.title, '') ||
             COALESCE(' ' || cont.firstName, '') ||
             COALESCE(' ' || cont.lastName, '')) AS cont_name,
       cont.phone                                AS cont_phone,
       cont.fax                                  AS cont_fax,
       cont.email                                AS cont_email,
       LTRIM(COALESCE(srgrt.name, srgr.name, '') ||
             COALESCE(' ' || srep.title, '') ||
             COALESCE(' ' || srep.firstName, '') ||
             COALESCE(' ' || srep.lastName, '')) AS sr_name,
       srep.phone                                AS sr_phone,
       srep.fax                                  AS sr_fax,
       srep.email                                AS sr_email,
       COALESCE(dtt.PrintName, dt.PrintName)     AS PrintName,
       o.datepromised,
       COALESCE(dtt.Description, dt.Description) AS dt_description,
       offer.documentno                          AS offer_documentno,
       offer.dateordered                         AS offer_date,
       CASE
           WHEN o.isdropship = 'Y'
               THEN REPLACE(
                   REPLACE(o.deliverytoaddress, E'\r\n', ' | '),
                   E'\n', ' | '
                    )
               ELSE REPLACE(
                       REPLACE(o.bpartneraddress, E'\r\n', ' | '),
                       E'\n', ' | '
                    )
       END                                       AS deliverytoaddress,
       CASE
           WHEN o.OrderType = 'ON'
               THEN o.validuntil
       END                                       AS validuntil,
       CASE
           WHEN o.OrderType = 'ON'
               THEN o.versionno
       END                                       AS versionno,
       wh.name                                   AS warehouse,
       pr.value                                  AS projectno
FROM C_Order o
         INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN AD_User srep ON o.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100
         LEFT OUTER JOIN AD_User cont ON o.Bill_User_ID = cont.AD_User_ID
         LEFT OUTER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON o.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_ad_language

    -- Translatables
         LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID
         LEFT OUTER JOIN C_Greeting_Trl cogrt ON cont.C_Greeting_ID = cogrt.C_Greeting_ID AND cogrt.ad_language = p_ad_language
         LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID
         LEFT OUTER JOIN C_Greeting_Trl srgrt ON srep.C_Greeting_ID = srgrt.C_Greeting_ID AND srgrt.ad_language = p_ad_language

    -- proposal order
         LEFT JOIN C_Order offer ON offer.C_Order_ID = o.ref_proposal_id

    -- warehouse
         LEFT JOIN m_warehouse wh ON o.m_warehouse_id = wh.m_warehouse_id

    -- project
         LEFT JOIN c_project pr ON o.c_project_id = pr.c_project_id

WHERE o.C_Order_ID = p_record_id
$$
;

-- ===========================================================================
-- 8. Docs_Sales_OrderCheckup_Root
-- ===========================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root(IN numeric, IN numeric, IN date);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root(IN p_record_id    numeric,
                                                                                           IN bPartnerId     numeric,
                                                                                           IN p_datePromised date)
  RETURNS TABLE
  (
    ad_org_id  numeric,
    docstatus  character(2),
    printname  character varying(60),
    c_order_id integer,
	C_Order_MFGWarehouse_Report_ID integer
  )
AS
$$
SELECT
  r.AD_Org_ID,
  r.DocStatus,
  r.PrintName,
  r.C_Order_ID :: int,
  r.C_Order_MFGWarehouse_Report_ID :: int
FROM report.RV_C_Order_MFGWarehouse_Report_Header r
WHERE
  CASE
  WHEN p_record_id IS NOT NULL
    THEN r.C_Order_MFGWarehouse_Report_ID = p_record_id
  WHEN bPartnerId IS NOT NULL AND DatePromised :: date IS NOT NULL
    THEN r.C_BPartner_ID = bPartnerId AND r.DatePromised :: date = p_datePromised :: date
  ELSE false -- shall never happen
  END
LIMIT 1

$$
LANGUAGE sql
STABLE;

