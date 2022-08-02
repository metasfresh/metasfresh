DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details ( IN p_C_Invoice_ID numeric, IN p_AD_Language Character Varying(6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details(IN p_C_Invoice_ID numeric,
                                                                                         IN p_AD_Language  Character Varying(6))
  RETURNS TABLE
  (
    InOuts                text,
    docType               character varying,
    reference             character varying(40),
    shipLocation          character varying(60),
    InOuts_DateFrom       text,
    InOuts_DateTo         text,
    InOuts_IsSameDate     boolean,
    InOuts_IsDataComplete boolean,
    IsHU                  boolean,
    line                  numeric(10, 0),
    Name                  character varying,
    Attributes            text,
    HUQty                 numeric,
    HUName                text,
    QtyInvoicedInPriceUOM numeric,
    shipped               numeric,
    retour                numeric,
    PriceActual           numeric,
    PriceEntered          numeric,
    Discount              numeric,
    UOM                   character varying(10),
    PriceUOM              character varying(10),
    StdPrecision          numeric(10, 1),
    linenetamt            numeric,
    rate                  numeric,
    isDiscountPrinted     character(1),
    IsPrintTax            character(1),
    Description           character varying(255),
    productdescription    character varying(255),
    bp_product_no         character varying(30),
    bp_product_name       character varying(100),
    p_value               character varying(40),
    p_description         character varying(255),
    invoice_description   character varying(1024),
    cursymbol             character varying(10),
    iscampaignprice       character(1),
	IsPrintWhenPackingMaterial char(1)
  )
AS
$$
SELECT
  io.DocType || ': ' || io.DocNo                         AS InOuts,
  io.docType,
  io.reference,
  io.shipLocation,
  to_char(io.DateFrom, 'DD.MM.YYYY')                     AS InOuts_DateFrom,
  to_char(io.DateTo, 'DD.MM.YYYY')                       AS InOuts_DateTo,
  DateFrom :: date = DateTo :: Date                      AS InOuts_IsSameDate,
  DocNo IS NOT NULL                                      AS InOuts_IsDataComplete,
  COALESCE(pc.IsHU, false)                               AS IsHU,
  il.line,
  COALESCE(pt.name, p.name)                              AS Name,
  COALESCE(
      CASE WHEN Length(att.Attributes) > 15
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
  il.linenetamt,
  t.rate,
  i.isDiscountPrinted,
  bpg.IsPrintTax,
  il.Description,
  il.ProductDescription,
  -- in case there is no C_BPartner_Product, fallback to the default ones
  COALESCE(NULLIF(bpp.ProductNo, ''), p.value)           as bp_product_no,
  COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) as bp_product_name,
  p.value                                                AS p_value,
  p.description                                          AS p_description,
  i.description                                          AS invoice_description,
  c.cursymbol,
  ol.iscampaignprice,
  p.IsPrintWhenPackingMaterial

FROM
  C_InvoiceLine il
  INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
  INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
  LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'

  -- get promotional price details from order line
  LEFT OUTER JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id

  -- Get Product and its translation
  LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
  LEFT OUTER JOIN M_Product_Trl pt ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language AND pt.isActive = 'Y'
  LEFT OUTER JOIN LATERAL
                  (
                  SELECT
                    M_Product_Category_ID =
                    getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID) AS isHU,
                    M_Product_Category_ID
                  FROM M_Product_Category
                  WHERE isActive = 'Y'
                  ) pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID

  -- Get Unit of measurement and its translation
  LEFT OUTER JOIN C_UOM uom ON il.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
  LEFT OUTER JOIN C_UOM_Trl uomt ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'
  LEFT OUTER JOIN C_UOM puom ON il.Price_UOM_ID = puom.C_UOM_ID AND puom.isActive = 'Y'
  LEFT OUTER JOIN C_UOM_Trl puomt
    ON il.Price_UOM_ID = puomt.C_UOM_ID AND puomt.AD_Language = p_AD_Language AND puomt.isActive = 'Y'

  -- Tax rate
  LEFT OUTER JOIN C_Tax t ON il.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'

  LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

  -- Get shipment details
  LEFT OUTER JOIN (
                    SELECT DISTINCT
                      x.C_InvoiceLine_ID,
                      First_Agg(x.DocType) AS DocType,
                      String_agg(x.DocNo, ', '
                      ORDER BY x.DocNo)    AS DocNo,
                      Min(x.DateFrom)      AS DateFrom,
                      Max(x.DateTo)        AS DateTo,
                      String_agg(x.reference, ', '
                      ORDER BY x.DocNo)    AS reference,
                      x.shipLocation
                    FROM (
                           SELECT DISTINCT
                             iliol.C_InvoiceLine_ID,
                             First_Agg(COALESCE(dtt.Printname, dt.Printname)
                             ORDER BY io.DocumentNo) AS DocType,
                             String_agg(io.DocumentNo, ', '
                             ORDER BY io.DocumentNo) AS DocNo,
                             Min(io.MovementDate)    AS DateFrom,
                             Max(io.MovementDate)    AS DateTo,
                             io.poreference          AS reference,
                             bpl.name                AS shipLocation
                           FROM
                             (SELECT DISTINCT
                                M_InOut_ID,
                                C_InvoiceLine_ID
                              FROM Report.fresh_IL_to_IOL_V
                              WHERE C_Invoice_ID = p_C_Invoice_ID) iliol
                             LEFT OUTER JOIN M_InOut io ON iliol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
                                                           AND io.DocStatus IN ('CO', 'CL')
                             /* task 09290 */
                             INNER JOIN C_BPartner_Location bpl
                               on io.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID AND bpl.isActive = 'Y'
                             LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
                             LEFT OUTER JOIN C_DocType_Trl dtt
                               ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_AD_Language AND dtt.isActive = 'Y'
                           GROUP BY
                             C_InvoiceLine_ID, io.poreference, bpl.C_BPartner_Location_ID
                         ) x

                    GROUP BY x.C_InvoiceLine_ID, x.shipLocation
                  ) io ON il.C_InvoiceLine_ID = io.C_InvoiceLine_ID


  -- Get Packing instruction
  LEFT OUTER JOIN (
                    SELECT
                      String_Agg(Name, E'\n'
                      ORDER BY Name) AS Name,
                      C_InvoiceLine_ID
                    FROM (
                           SELECT DISTINCT
                             COALESCE(pifb.name, pi.name) AS name,
                             C_InvoiceLine_ID
                           FROM
                             Report.fresh_IL_TO_IOL_V iliol
                             INNER JOIN M_InOutLine iol
                               ON iliol.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
                             LEFT OUTER JOIN M_HU_PI_Item_Product pi
                               ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID AND pi.isActive = 'Y'
                             LEFT OUTER JOIN M_HU_PI_Item piit
                               ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID AND piit.isActive = 'Y'

                             LEFT OUTER JOIN M_HU_Assignment asgn
                               ON asgn.AD_Table_ID = ((SELECT get_Table_ID('M_InOutLine')))
                                  AND asgn.Record_ID = iol.M_InOutLine_ID AND asgn.isActive = 'Y'
                             LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
                             LEFT OUTER JOIN M_HU_PI_Item_Product pifb
                               ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
                             LEFT OUTER JOIN M_HU_PI_Item pit
                               ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID AND pit.isActive = 'Y'
                             --
                             LEFT OUTER JOIN M_HU_PI_Version piv
                               ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID) AND
                                  piv.isActive = 'Y'
                           WHERE
                             piv.M_HU_PI_Version_ID != 101
                             AND iliol.C_Invoice_ID = p_C_Invoice_ID
                         ) pi
                    GROUP BY C_InvoiceLine_ID
                  ) piip ON il.C_InvoiceLine_ID = piip.C_InvoiceLine_ID

  -- Get Attributes
  LEFT OUTER JOIN
  (
    SELECT
      String_agg(ai_value, ', '
      ORDER BY length(ai_value)) AS Attributes,
      att.M_AttributeSetInstance_ID,
      il.C_InvoiceLine_ID
    FROM Report.fresh_Attributes att
      JOIN C_InvoiceLine il ON il.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
    WHERE att.at_Value IN ('1000002', '1000001', '1000030', '1000015') AND il.C_Invoice_ID = p_C_Invoice_ID
    -- Label, Herkunft, Aktionen, Marke (ADR)
    GROUP BY att.M_AttributeSetInstance_ID, il.C_InvoiceLine_ID
  ) att ON il.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID AND il.C_InvoiceLine_ID = att.C_InvoiceLine_ID

  LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
                                            AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'

  -- get inoutline - to order by it. The main error i think is that the lines in invoice are not ordered anymore as they used to
  LEFT OUTER JOIN M_InOutLine miol ON il.M_InOutLine_ID = miol.M_InOutLine_ID AND miol.isActive = 'Y'
  --ordering gebinde if config exists
  LEFT OUTER JOIN M_InOut mio ON mio.M_Inout_ID = miol.M_Inout_ID AND mio.isActive = 'Y'
  LEFT OUTER JOIN C_DocType mdt ON mio.C_DocType_ID = mdt.C_DocType_ID AND mdt.isActive = 'Y'
  LEFT OUTER JOIN C_DocLine_Sort dls ON mdt.DocBaseType = dls.DocBaseType AND dls.isActive = 'Y'
                                        AND EXISTS(
                                            SELECT 0
                                            FROM C_BP_DocLine_Sort bpdls
                                            WHERE bpdls.C_DocLine_Sort_ID = dls.C_DocLine_Sort_ID AND
                                                  bpdls.C_BPartner_ID = mio.C_BPartner_ID AND bpdls.isActive = 'Y'
                                        )
  LEFT OUTER JOIN C_DocLine_Sort_Item dlsi
    ON dls.C_DocLine_Sort_ID = dlsi.C_DocLine_Sort_ID AND dlsi.M_Product_ID = il.M_Product_ID AND dlsi.isActive = 'Y'


WHERE
  il.C_Invoice_ID = p_C_Invoice_ID AND il.isActive = 'Y'

ORDER BY
  io.DateFrom,
  io.DocNo,
  COALESCE(pc.IsHU, false),
  CASE WHEN COALESCE(pc.IsHU, false) = 't' AND dlsi.SeqNo IS NOT NULL
    THEN dlsi.SeqNo END,
  CASE WHEN COALESCE(pc.IsHU, false) = 't' AND dlsi.SeqNo IS NULL
    THEN p.name END,
  miol.line,
  line

$$
LANGUAGE sql
STABLE;