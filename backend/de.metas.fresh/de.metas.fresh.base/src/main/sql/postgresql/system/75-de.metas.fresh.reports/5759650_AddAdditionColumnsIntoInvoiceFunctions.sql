DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_sales_invoice_description(IN record_id  numeric,
                                                                                          IN p_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_invoice_description(record_id  numeric,
                                                                                             p_language character varying)
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
       bp.VATaxID,
       bp.value                                                                         AS bp_value,
       bp.eori                                                                          AS eori,
       bp.customernoatvendor                                                            AS customernoatvendor,
       COALESCE(cogr.name, '') ||
       COALESCE(' ' || cont.title, '') ||
       COALESCE(' ' || cont.firstName, '') ||
       COALESCE(' ' || cont.lastName, '')                                               AS cont_name,
       cont.phone                                                                       AS cont_phone,
       cont.fax                                                                         AS cont_fax,
       cont.email                                                                       AS cont_email,
       COALESCE(srgr.name, '') ||
       COALESCE(' ' || srep.title, '') ||
       COALESCE(' ' || srep.firstName, '') ||
       COALESCE(' ' || srep.lastName, '')                                               AS sr_name,
       srep.phone                                                                       AS sr_phone,
       srep.fax                                                                         AS sr_fax,
       srep.email                                                                       AS sr_email,
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
       o.offer_documentno                                                               AS offer_documentno,
       o.offer_date                                                                     AS offer_date,
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

           First_Agg(o.dateordered::text ORDER BY o.dateordered) ||
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

    WHERE il.C_Invoice_ID = record_id
    ) o ON TRUE

         LEFT JOIN LATERAL
    (
    SELECT First_Agg(io.DocumentNo ORDER BY io.DocumentNo) ||
           CASE WHEN COUNT(io.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo,
           MIN(io.MovementDate)::date                                 AS DateFrom
    FROM C_InvoiceLine il
             JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID
             JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID

    WHERE il.C_Invoice_ID = record_id
    ) io ON TRUE

    -- warehouse
         LEFT JOIN m_warehouse wh ON i.m_warehouse_id = wh.m_warehouse_id
    -- project
         LEFT JOIN c_project pr ON i.c_project_id = pr.c_project_id
WHERE i.C_Invoice_ID = record_id

$$
;



DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details (IN p_C_Invoice_ID numeric,
                                                                                       IN p_AD_Language  Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details(IN p_C_Invoice_ID numeric,
                                                                                         IN p_AD_Language  Character Varying(6))
    RETURNS TABLE
            (
                InOuts                     text,
                docType                    character varying,
                reference                  character varying(40),
                shipLocation               character varying(60),
                Tour                       text,
                week_year                  character varying,
                InOuts_DateFrom            text,
                InOuts_DateTo              text,
                InOuts_IsSameDate          boolean,
                InOuts_IsDataComplete      boolean,
                IsHU                       boolean,
                line                       numeric(10, 0),
                Name                       character varying,
                Attributes                 text,
                HUQty                      numeric,
                HUName                     text,
                QtyInvoicedInPriceUOM      numeric,
                shipped                    numeric,
                retour                     numeric,
                PriceActual                numeric,
                PriceEntered               numeric,
                Discount                   numeric,
                UOM                        character varying(10),
                PriceUOM                   character varying(10),
                StdPrecision               numeric(10, 1),
                QtyPattern                 character varying,
                linenetamt                 numeric,
                rate                       numeric,
                isdiscountprinted          character,
                isprinttax                 character,
                description                character varying,
                productdescription         character varying,
                bp_product_no              character varying,
                bp_product_name            character varying,
                p_value                    character varying,
                p_description              character varying,
                invoice_description        character varying,
                cursymbol                  character varying,
                iscampaignprice            character,
                isprintwhenpackingmaterial character,
                PricePattern               text,
                AmountPattern              text,
                catchweight                numeric,
                weight_uom                 character varying(10),
                customs_number             text
            )
AS
$$
SELECT io.DocType || ': ' || io.DocNo                         AS InOuts,
       io.docType,
       io.reference,
       io.shipLocation,
       io.Tour,
       io.week_year,
       TO_CHAR(io.DateFrom, 'DD.MM.YYYY')                     AS InOuts_DateFrom,
       TO_CHAR(io.DateTo, 'DD.MM.YYYY')                       AS InOuts_DateTo,
       DateFrom :: date = DateTo :: Date                      AS InOuts_IsSameDate,
       DocNo IS NOT NULL                                      AS InOuts_IsDataComplete,
       COALESCE(pc.IsHU, FALSE)                               AS IsHU,
       il.line,
       COALESCE(pt.name, p.name)                              AS Name,
       COALESCE(
               CASE
                   WHEN LENGTH(att.Attributes) > 15
                       THEN att.Attributes || E'\n'
                       ELSE att.Attributes
               END,
               ''
       )                                                      AS Attributes,
       il.QtyenteredTU                                        AS HUQty,
       piip.name                                              AS HUName,
       il.QtyInvoicedInPriceUOM,
       CASE
           WHEN il.QtyEntered > 0
               THEN il.QtyEntered
               ELSE 0
       END                                                    AS shipped,
       CASE
           WHEN il.QtyEntered < 0
               THEN il.QtyEntered * -1
               ELSE 0
       END                                                    AS retour,
       il.PriceActual,
       il.PriceEntered,
       il.Discount,
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                AS UOM,
       COALESCE(puomt.UOMSymbol, puom.UOMSymbol)              AS PriceUOM,
       puom.StdPrecision,
       report.getQtyPattern(puom.StdPrecision)                AS QtyPattern,
       il.linenetamt,
       t.rate,
       i.isDiscountPrinted,
       bpg.IsPrintTax,
       il.Description,
       il.ProductDescription,
       -- in case there is no C_BPartner_Product, fallback to the default ones
       COALESCE(NULLIF(bpp.ProductNo, ''), p.value)           AS bp_product_no,
       COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) AS bp_product_name,
       p.value                                                AS p_value,
       p.description                                          AS p_description,
       i.description                                          AS invoice_description,
       c.cursymbol,
       ol.iscampaignprice,
       p.IsPrintWhenPackingMaterial,
       report.getPricePatternForJasper(i.m_pricelist_id)      AS PricePattern,
       report.getAmountPatternForJasper(c.c_currency_id)      AS AmountPattern,
       w.catchweight,
       w.weight_uom,
       pcus.value || ' ' || COALESCE(pcus.name, '')           AS customs_number
FROM C_InvoiceLine il
         INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
         INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID

    -- get promotional price details from order line
         LEFT OUTER JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id

    -- Get Product and its translation
         LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language

    -- Get customs number
         LEFT OUTER JOIN m_customstariff pcus ON p.M_CustomsTariff_ID = pcus.M_CustomsTariff_ID

         LEFT OUTER JOIN LATERAL
    (
    SELECT M_Product_Category_ID =
           getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID) AS isHU,
           M_Product_Category_ID
    FROM M_Product_Category
    ) pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID

    -- Get Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON il.C_UOM_ID = uom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language
         LEFT OUTER JOIN C_UOM puom ON il.Price_UOM_ID = puom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl puomt
                         ON il.Price_UOM_ID = puomt.C_UOM_ID AND puomt.AD_Language = p_AD_Language
    -- Tax rate
         LEFT OUTER JOIN C_Tax t ON il.C_Tax_ID = t.C_Tax_ID

         LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID

    -- Get shipment details
         LEFT OUTER JOIN (SELECT DISTINCT ON (x.C_InvoiceLine_ID) x.C_InvoiceLine_ID,
                                                                  First_Agg(x.DocType)             AS DocType,
                                                                  STRING_AGG(x.DocNo, ', '
                                                                             ORDER BY x.DocNo)     AS DocNo,
                                                                  STRING_AGG(x.week_year, ', '
                                                                             ORDER BY x.week_year) AS week_year,
                                                                  MIN(x.DateFrom)                  AS DateFrom,
                                                                  MAX(x.DateTo)                    AS DateTo,
                                                                  STRING_AGG(x.reference, ', '
                                                                             ORDER BY x.DocNo)     AS reference,
                                                                  x.shipLocation,
                                                                  x.tour,
                                                                  x.M_InOut_ID
                          FROM (SELECT DISTINCT ON (iliol.C_InvoiceLine_ID) iliol.C_InvoiceLine_ID,
                                                                            First_Agg(COALESCE(dtt.Printname, dt.Printname)
                                                                                      ORDER BY io.DocumentNo)                                       AS DocType,
                                                                            STRING_AGG(io.DocumentNo, ', '
                                                                                       ORDER BY io.DocumentNo)                                      AS DocNo,
                                                                            TO_CHAR(io.MovementDate, 'WW') || '.' || TO_CHAR(io.MovementDate, 'YY') AS week_year,
                                                                            MIN(io.MovementDate)                                                    AS DateFrom,
                                                                            MAX(io.MovementDate)                                                    AS DateTo,
                                                                            io.poreference                                                          AS reference,
                                                                            bpl.name                                                                AS shipLocation,
                                                                            t.name                                                                  AS tour,
                                                                            io.M_InOut_ID
                                FROM (SELECT DISTINCT ON (C_InvoiceLine_ID) M_InOut_ID,
                                                                            C_InvoiceLine_ID,
                                                                            M_InOutLine_ID
                                      FROM Report.fresh_IL_to_IOL_V
                                      WHERE C_Invoice_ID = p_C_Invoice_ID) iliol
                                         LEFT OUTER JOIN M_InOut io ON iliol.M_InOut_ID = io.M_InOut_ID
                                    AND io.DocStatus IN ('CO', 'CL')
                                    /* task 09290 */
                                         INNER JOIN C_BPartner_Location bpl
                                                    ON io.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID
                                         LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
                                         LEFT OUTER JOIN C_DocType_Trl dtt
                                                         ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_AD_Language
                                         LEFT OUTER JOIN M_tour t ON io.m_tour_id = t.m_tour_id

                                GROUP BY C_InvoiceLine_ID, io.poreference, bpl.C_BPartner_Location_ID, t.name, io.M_InOut_ID) x

                          GROUP BY x.C_InvoiceLine_ID, x.shipLocation, x.tour, x.M_InOut_ID) io ON il.C_InvoiceLine_ID = io.C_InvoiceLine_ID
         LEFT OUTER JOIN
     de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Sum_Weight(io.m_inout_id, p_AD_Language) AS w ON TRUE

         -- Get Packing instruction
         LEFT OUTER JOIN (SELECT STRING_AGG(Name, E'\n'
                                            ORDER BY Name) AS Name,
                                 C_InvoiceLine_ID
                          FROM (SELECT DISTINCT COALESCE(pifb.name, pi.name) AS name,
                                                C_InvoiceLine_ID
                                FROM Report.fresh_IL_TO_IOL_V iliol
                                         INNER JOIN M_InOutLine iol
                                                    ON iliol.M_InOutLine_ID = iol.M_InOutLine_ID
                                         LEFT OUTER JOIN M_HU_PI_Item_Product pi
                                                         ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID
                                         LEFT OUTER JOIN M_HU_PI_Item piit
                                                         ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID

                                         LEFT OUTER JOIN M_HU_Assignment asgn
                                                         ON asgn.AD_Table_ID = ((SELECT get_Table_ID('M_InOutLine')))
                                                             AND asgn.Record_ID = iol.M_InOutLine_ID
                                         LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
                                         LEFT OUTER JOIN M_HU_PI_Item_Product pifb
                                                         ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID
                                         LEFT OUTER JOIN M_HU_PI_Item pit
                                                         ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID
                                    --
                                         LEFT OUTER JOIN M_HU_PI_Version piv
                                                         ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID)
                                WHERE piv.M_HU_PI_Version_ID != 101
                                  AND iliol.C_Invoice_ID = p_C_Invoice_ID) pi
                          GROUP BY C_InvoiceLine_ID) piip ON il.C_InvoiceLine_ID = piip.C_InvoiceLine_ID

    -- Get Attributes
         LEFT OUTER JOIN
     (SELECT STRING_AGG(ai_value, ', '
                        ORDER BY LENGTH(ai_value)) AS Attributes,
             att.M_AttributeSetInstance_ID,
             il.C_InvoiceLine_ID
      FROM report.fresh_Attributes att
               JOIN C_InvoiceLine il ON il.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
      WHERE att.IsPrintedInDocument = 'Y'
        AND il.C_Invoice_ID = p_C_Invoice_ID
      GROUP BY att.M_AttributeSetInstance_ID, il.C_InvoiceLine_ID) att ON il.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID AND il.C_InvoiceLine_ID = att.C_InvoiceLine_ID

         LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
    AND p.M_Product_ID = bpp.M_Product_ID

    -- get inoutline - to order by it. The main error i think is that the lines in invoice are not ordered anymore as they used to
         LEFT OUTER JOIN M_InOutLine miol ON il.M_InOutLine_ID = miol.M_InOutLine_ID
    --ordering gebinde if config exists
         LEFT OUTER JOIN M_InOut mio ON mio.M_Inout_ID = miol.M_Inout_ID
         LEFT OUTER JOIN C_DocType mdt ON mio.C_DocType_ID = mdt.C_DocType_ID
         LEFT OUTER JOIN C_DocLine_Sort dls ON mdt.DocBaseType = dls.DocBaseType
    AND EXISTS(SELECT 0
               FROM C_BP_DocLine_Sort bpdls
               WHERE bpdls.C_DocLine_Sort_ID = dls.C_DocLine_Sort_ID
                 AND bpdls.C_BPartner_ID = mio.C_BPartner_ID)
         LEFT OUTER JOIN C_DocLine_Sort_Item dlsi
                         ON dls.C_DocLine_Sort_ID = dlsi.C_DocLine_Sort_ID AND dlsi.M_Product_ID = il.M_Product_ID
WHERE il.C_Invoice_ID = p_C_Invoice_ID
ORDER BY io.DateFrom,
         io.DocNo,
         COALESCE(pc.IsHU, FALSE),
         CASE
             WHEN COALESCE(pc.IsHU, FALSE) = 't' AND dlsi.SeqNo IS NOT NULL
                 THEN dlsi.SeqNo
         END,
         CASE
             WHEN COALESCE(pc.IsHU, FALSE) = 't' AND dlsi.SeqNo IS NULL
                 THEN p.name
         END,
         miol.line,
         line

$$
    LANGUAGE sql
    STABLE
;

