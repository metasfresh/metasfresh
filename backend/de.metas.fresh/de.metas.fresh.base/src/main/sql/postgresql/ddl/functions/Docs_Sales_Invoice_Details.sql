DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details (IN p_record_id numeric,
                                                                                       IN p_language  Character Varying(6))
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details (IN p_record_id numeric,
                                                                               IN p_language  Character Varying(6))
    RETURNS TABLE
            (
                InOuts                     text,
                docType                    character varying,
                reference                  character varying(40),
                shipLocation               character varying(60),
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
                PricePattern               text
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT io.DocType || ': ' || io.DocNo                         AS InOuts,
       io.docType,
       io.reference,
       io.shipLocation,
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
       report.getQtyPattern(puom.StdPrecision) AS QtyPattern,
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
       report.getPricePatternForJasper(i.m_pricelist_id)      AS PricePattern

FROM C_InvoiceLine il
         INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
         INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID

    -- get promotional price details from order line
         LEFT OUTER JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id

    -- Get Product and its translation
         LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_language
         LEFT OUTER JOIN LATERAL
    (
    SELECT M_Product_Category_ID =
           getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID) AS isHU,
           M_Product_Category_ID
    FROM M_Product_Category
    ) pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID

    -- Get Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON il.C_UOM_ID = uom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_language
         LEFT OUTER JOIN C_UOM puom ON il.Price_UOM_ID = puom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl puomt
                         ON il.Price_UOM_ID = puomt.C_UOM_ID AND puomt.AD_Language = p_language

    -- Tax rate
         LEFT OUTER JOIN C_Tax t ON il.C_Tax_ID = t.C_Tax_ID

         LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID

    -- Get shipment details
         LEFT OUTER JOIN (SELECT DISTINCT x.C_InvoiceLine_ID,
                                          First_Agg(x.DocType)         AS DocType,
                                          STRING_AGG(x.DocNo, ', '
                                                     ORDER BY x.DocNo) AS DocNo,
                                          MIN(x.DateFrom)              AS DateFrom,
                                          MAX(x.DateTo)                AS DateTo,
                                          STRING_AGG(x.reference, ', '
                                                     ORDER BY x.DocNo) AS reference,
                                          x.shipLocation
                          FROM (SELECT DISTINCT iliol.C_InvoiceLine_ID,
                                                First_Agg(COALESCE(dtt.Printname, dt.Printname)
                                                          ORDER BY io.DocumentNo)  AS DocType,
                                                STRING_AGG(io.DocumentNo, ', '
                                                           ORDER BY io.DocumentNo) AS DocNo,
                                                MIN(io.MovementDate)               AS DateFrom,
                                                MAX(io.MovementDate)               AS DateTo,
                                                io.poreference                     AS reference,
                                                bpl.name                           AS shipLocation
                                FROM (SELECT DISTINCT M_InOut_ID,
                                                      C_InvoiceLine_ID
                                      FROM Report.fresh_IL_to_IOL_V
                                      WHERE C_Invoice_ID = p_record_id) iliol
                                         LEFT OUTER JOIN M_InOut io ON iliol.M_InOut_ID = io.M_InOut_ID
                                    AND io.DocStatus IN ('CO', 'CL')
                                    /* task 09290 */
                                         INNER JOIN C_BPartner_Location bpl
                                                    ON io.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID
                                         LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
                                         LEFT OUTER JOIN C_DocType_Trl dtt
                                                         ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language
                                GROUP BY C_InvoiceLine_ID, io.poreference, bpl.C_BPartner_Location_ID) x

                          GROUP BY x.C_InvoiceLine_ID, x.shipLocation) io ON il.C_InvoiceLine_ID = io.C_InvoiceLine_ID


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
                                  AND iliol.C_Invoice_ID = p_record_id) pi
                          GROUP BY C_InvoiceLine_ID) piip ON il.C_InvoiceLine_ID = piip.C_InvoiceLine_ID

    -- Get Attributes
         LEFT OUTER JOIN
     (SELECT STRING_AGG(ai_value, ', '
                        ORDER BY LENGTH(ai_value)) AS Attributes,
             att.M_AttributeSetInstance_ID,
             il.C_InvoiceLine_ID
      FROM Report.fresh_Attributes att
               JOIN C_InvoiceLine il ON il.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
      WHERE att.IsPrintedInDocument = 'Y'
        AND il.C_Invoice_ID = p_record_id
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
                 AND bpdls.C_BPartner_ID = mio.C_BPartner_ID
                 AND bpdls.isActive = 'Y')
         LEFT OUTER JOIN C_DocLine_Sort_Item dlsi
                         ON dls.C_DocLine_Sort_ID = dlsi.C_DocLine_Sort_ID AND dlsi.M_Product_ID = il.M_Product_ID

WHERE il.C_Invoice_ID = p_record_id

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
;

