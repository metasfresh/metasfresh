DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details(IN record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details(IN record_id numeric, IN ad_language Character Varying (6))

RETURNS TABLE
(
	line numeric(10,0), 
	Name character varying, 
	Attributes text, 
	HUQty numeric, 
	HUName character varying, 
	QtyEnteredInPriceUOM numeric, 
	PriceEntered numeric, 
	UOMSymbol character varying(10), 
	StdPrecision numeric(10,0), 
	linenetamt numeric,
	discount numeric, 
	isDiscountPrinted character(1), 
	rate character varying, 
	isPrintTax character(1),
	description character varying,
	documentnote character varying,
	productdescription character varying, 
	bp_product_no character varying(30),
	bp_product_name character varying(100),
	cursymbol character varying(10),
	p_value character varying(40),
	p_description character varying(255),
	order_description character varying(1024),
	c_order_compensationgroup_id numeric,
	isgroupcompensationline character(1),
	groupname  character varying(255),
	iso_code character(3),
	iscampaignprice   character(1)	
)
AS
$$
SELECT
	ol.line,
	COALESCE(pt.Name, p.name)		AS Name,
	CASE WHEN Length( att.Attributes ) > 15
		THEN att.Attributes || E'\n'
		ELSE att.Attributes
	END AS Attributes,
	ol.QtyEnteredTU			AS HUQty,
	CASE WHEN piit.M_HU_PI_Version_ID = 101 OR ol.QtyEnteredTU IS NULL
	THEN NULL ELSE ip.name END 		AS HUName,
	ol.QtyEnteredInPriceUOM		AS QtyEnteredInPriceUOM,
	ol.PriceEntered 			AS PriceEntered,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) 	AS UOMSymbol,
	uom.StdPrecision,
	ol.linenetamt			AS linenetamt,
	CASE
		WHEN round(discount, 0) = discount THEN round(discount, 0)
		WHEN round(discount, 1) = discount THEN round(discount, 1)
		ELSE round(discount, 2)
	END as discount,
	bp.isDiscountPrinted,
	CASE
		WHEN round(rate, 0) = rate THEN round(rate, 0)
		WHEN round(rate, 1) = rate THEN round(rate, 1)
		ELSE round(rate, 2)
	END::character varying AS rate,
	isPrintTax,
	ol.description,
	ol.M_Product_DocumentNote as documentnote,
	ol.productdescription,
	-- in case there is no C_BPartner_Product, fallback to the default ones
	COALESCE(NULLIF(bpp.ProductNo, ''), p.value) as bp_product_no,
	COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) as bp_product_name,
	c.cursymbol, 
	p.value AS p_value,
	p.description AS p_description,
	o.description AS order_description,
	ol.c_order_compensationgroup_id,
	ol.isgroupcompensationline,
	cg.name,
	c.iso_code,
	ol.iscampaignprice
FROM
	C_OrderLine ol
	INNER JOIN C_Order o 			ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
	INNER JOIN C_BPartner bp			ON o.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_BP_Group bpg 		ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item_Product ip 		ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item piit ON ip.M_HU_PI_Item_ID = piit.M_HU_PI_Item_ID AND piit.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
		
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ol.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ol.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y' AND uomt.isActive = 'Y'
	-- Tax
	LEFT OUTER JOIN C_Tax t			ON ol.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'

	-- Get Attributes
	LEFT OUTER JOIN	(
		SELECT 	String_agg ( att.ai_value, ', ' ORDER BY length(att.ai_value)) AS Attributes, att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID
		FROM 	Report.fresh_Attributes att
		JOIN C_OrderLine ol ON att.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
		WHERE	att.at_Value IN ('1000002', '1000001', '1000030', '1000015') AND ol.C_Order_ID = $1
		GROUP BY	att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID
	) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID AND ol.C_OrderLine_ID = att.C_OrderLine_ID

	LEFT OUTER JOIN 
			de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(p.M_Product_ID, bp.C_BPartner_ID, att.M_AttributeSetInstance_ID) as bpp on 1=1
			
	-- compensation group
	LEFT JOIN c_order_compensationgroup cg ON ol.c_order_compensationgroup_id = cg.c_order_compensationgroup_id 
	
	LEFT JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID and c.isActive = 'Y'

WHERE
	ol.C_Order_ID = $1 AND ol.isActive = 'Y'
	AND (COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID))
ORDER BY
	ol.line

$$
LANGUAGE sql STABLE	
;


------------

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details ( IN C_Invoice_ID numeric, IN AD_Language Character Varying(6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details(IN C_Invoice_ID numeric,
                                                                                         IN AD_Language  Character Varying(6))
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
    iscampaignprice       character(1)
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
  ol.iscampaignprice

FROM
  C_InvoiceLine il
  INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
  INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
  LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'

  -- get promotional price details from order line
  LEFT OUTER JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id

  -- Get Product and its translation
  LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
  LEFT OUTER JOIN M_Product_Trl pt ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
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
  LEFT OUTER JOIN C_UOM_Trl uomt ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
  LEFT OUTER JOIN C_UOM puom ON il.Price_UOM_ID = puom.C_UOM_ID AND puom.isActive = 'Y'
  LEFT OUTER JOIN C_UOM_Trl puomt
    ON il.Price_UOM_ID = puomt.C_UOM_ID AND puomt.AD_Language = $2 AND puomt.isActive = 'Y'

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
                              WHERE C_Invoice_ID = $1) iliol
                             LEFT OUTER JOIN M_InOut io ON iliol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
                                                           AND io.DocStatus IN ('CO', 'CL')
                             /* task 09290 */
                             INNER JOIN C_BPartner_Location bpl
                               on io.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID AND bpl.isActive = 'Y'
                             LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
                             LEFT OUTER JOIN C_DocType_Trl dtt
                               ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
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
                             AND iliol.C_Invoice_ID = $1
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
    WHERE att.at_Value IN ('1000002', '1000001', '1000030', '1000015') AND il.C_Invoice_ID = $1
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
  il.C_Invoice_ID = $1 AND il.isActive = 'Y'

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



----------------



DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details
(
	Line Numeric (10,0),
	Name Character Varying,
	Attributes Text,
	HUQty Numeric,
	HUName Text,
	qtyEntered Numeric,
	PriceEntered Numeric,
	UOMSymbol Character Varying (10),
	StdPrecision Numeric (10,0),
	LineNetAmt Numeric,
	Discount Numeric,
	IsDiscountPrinted Character (1),
	QtyPattern text,
	Description Character Varying,
	bp_product_no character varying(30),
	bp_product_name character varying(100),
	best_before_date text,
	lotno character varying,
	p_value character varying(30),
	p_description character varying(255), 
	inout_description character varying(255),
	iscampaignprice       character(1)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details AS
$$

SELECT
	iol.line,
	COALESCE(pt.Name, p.name) AS Name,
	CASE WHEN Length( att.Attributes ) > 15
		THEN att.Attributes || E'\n'
		ELSE att.Attributes
	END AS Attributes,
	iol.QtyEnteredTU AS HUQty,
	pi.name AS HUName,
	iol.QtyEntered * COALESCE (multiplyrate, 1) AS QtyEntered,
	COALESCE(ic.PriceEntered_Override, ic.PriceEntered) AS PriceEntered,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	uom.stdPrecision,
	COALESCE(ic.PriceActual_Override, ic.PriceActual) * iol.MovementQty * COALESCE (multiplyrate, 1) AS linenetamt,
	COALESCE(ic.Discount_Override, ic.Discount) AS Discount,
	bp.isDiscountPrinted,
	CASE WHEN StdPrecision = 0 THEN '#,##0' ELSE Substring( '#,##0.000' FROM 0 FOR 7+StdPrecision::integer) END AS QtyPattern,
	iol.Description,
	-- in case there is no C_BPartner_Product, fallback to the default ones
	COALESCE(NULLIF(bpp.ProductNo, ''), p.value) as bp_product_no,
	COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) as bp_product_name,
	to_char(att.best_before_date::date, 'MM.YYYY') AS best_before_date,
	att.lotno,
	p.value AS p_value,
	p.description AS p_description,
	io.description AS inout_description,
	ol.iscampaignprice
FROM
	M_InOutLine iol
	INNER JOIN M_InOut io 			ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp			ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN (
		SELECT 	AVG(ic.PriceEntered_Override) AS PriceEntered_Override, AVG(ic.PriceEntered) AS PriceEntered,
			AVG(ic.PriceActual_Override) AS PriceActual_Override, AVG(ic.PriceActual) AS PriceActual,
			AVG(ic.Discount_Override) AS Discount_Override, AVG(ic.Discount) AS Discount, Price_UOM_ID, iciol.M_InOutLine_ID
		FROM 	C_InvoiceCandidate_InOutLine iciol
			INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
			INNER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID AND iol.isActive = 'Y'
		WHERE iol.M_InOut_ID = $1 AND iciol.isActive = 'Y'
		GROUP BY 	Price_UOM_ID, iciol.M_InOutLine_ID
	) ic ON iol.M_InOutLine_ID = ic.M_InOutLine_ID
	
	-- get promotional price details from order line
    LEFT OUTER JOIN c_orderline ol ON ol.c_orderline_id = iol.c_orderline_id
	
	-- Get Packing instruction
	LEFT OUTER JOIN
	(
		SELECT String_Agg( DISTINCT name, E'\n' ORDER BY name ) AS Name, M_InOutLine_ID
		FROM
			(
				SELECT DISTINCT
					-- 08604 - in IT1 only one PI was shown though 2 were expected. Only the fallback can do this, so we use it first
					COALESCE ( pifb.name, pi.name ) AS name,
					iol.M_InOutLine_ID
				FROM
					M_InOutLine iol
					-- Get PI directly from InOutLine (1 to 1) 
					LEFT OUTER JOIN M_HU_PI_Item_Product pi ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID AND pi.isActive = 'Y'
					LEFT OUTER JOIN M_HU_PI_Item piit ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID AND piit.isActive = 'Y'
					-- Get PI from HU assignments (1 to n)
					-- if the HU was set manually don't check the assignments
					LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) ))
						AND asgn.Record_ID = iol.M_InOutLine_ID AND asgn.isActive = 'Y' and iol.ismanualpackingmaterial = 'N'
					LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
					LEFT OUTER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
					LEFT OUTER JOIN M_HU_PI_Item pit ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID AND pit.isActive = 'Y'
					--
					LEFT OUTER JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID) AND piv.isActive = 'Y'
				WHERE
					piv.M_HU_PI_Version_ID != 101
					AND iol.M_InOut_ID = $1 AND iol.isActive = 'Y'
			) x
		GROUP BY M_InOutLine_ID
	) pi ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ic.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ic.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Conversion conv		ON conv.C_UOM_ID = iol.C_UOM_ID 
		AND conv.C_UOM_To_ID = ic.Price_UOM_ID
		AND iol.M_Product_ID = conv.M_Product_ID
		AND conv.isActive = 'Y'
	-- Attributes
	LEFT OUTER JOIN	(
		SELECT 	String_agg ( at.ai_value, ', ' ORDER BY Length(at.ai_value), at.ai_value )
					FILTER (WHERE at.at_value not in ('HU_BestBeforeDate', 'Lot-Nummer'))
				AS Attributes, 

				at.M_AttributeSetInstance_ID ,
				String_agg (replace(at.ai_value, 'MHD: ', ''), ', ') 
					FILTER (WHERE at.at_value like 'HU_BestBeforeDate') 
				AS best_before_date,
				String_agg(ai_value, ', ') FILTER (WHERE at.at_value like 'Lot-Nummer') AS lotno
				
		FROM Report.fresh_Attributes at
		JOIN M_InOutLine iol ON at.M_AttributeSetInstance_ID = iol.M_AttributeSetInstance_ID AND iol.isActive = 'Y'
		WHERE	at.at_value IN ('1000002', '1000001', '1000030', '1000015', 'HU_BestBeforeDate', 'Lot-Nummer') -- Label, Herkunft, Aktionen, Marke (ADR)
			AND iol.M_InOut_ID = $1
		GROUP BY	at.M_AttributeSetInstance_ID
	) att ON iol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
	
	LEFT OUTER JOIN 
			de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(p.M_Product_ID, bp.C_BPartner_ID, att.M_AttributeSetInstance_ID) as bpp on 1=1
WHERE
	iol.M_InOut_ID = $1 AND iol.isActive = 'Y'
	AND (COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID))
	AND iol.QtyEntered != 0 -- Don't display lines without a Qty. See 08293
ORDER BY
	line

$$
LANGUAGE sql STABLE
;