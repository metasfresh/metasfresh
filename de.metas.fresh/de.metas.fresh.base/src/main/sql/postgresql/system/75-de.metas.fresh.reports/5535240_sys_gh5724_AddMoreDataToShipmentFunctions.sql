-- fix order no 

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description 
(
	Description Character Varying (255),
	DocumentNo Character Varying (30),
	MovementDate Timestamp Without Time Zone,
	Reference Character Varying (40),
	BP_Value Character Varying (40),
	Cont_Name Character Varying (40),
	Cont_Phone Character Varying (40),
	Cont_Fax Character Varying (40),
	Cont_Email Character Varying (60),
	SR_Name Text,
	SR_Phone Character Varying (40),
	SR_Fax Character Varying (40),
	SR_Email Character Varying (60),
	PrintName Character Varying (60),
	order_docno Character Varying (30)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description AS
$$
SELECT
	io.description 	as description,
	io.documentno 	as documentno,
	io.movementdate,
	io.poreference	as reference,
	bp.value	as bp_value,
	Coalesce(cogr.name, '')||
	Coalesce(' ' || cont.title, '') ||
	Coalesce(' ' || cont.firstName, '') ||
	Coalesce(' ' || cont.lastName, '') as cont_name,
	cont.phone	as cont_phone,
	cont.fax	as cont_fax,
	cont.email	as cont_email,
	Coalesce(srgr.name, '')||
	Coalesce(' ' || srep.title, '') ||
	Coalesce(' ' || srep.firstName, '') ||
	Coalesce(' ' || srep.lastName, '') as sr_name,
	srep.phone	as sr_phone,
	srep.fax	as sr_fax,
	srep.email	as sr_email,
	COALESCE ( dtt.printname, dt.printname ) AS printname,
	o.docno AS order_docno
FROM
	m_inout io
	INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
	INNER JOIN c_bpartner bp ON io.c_bpartner_id	= bp.c_bpartner_id AND bp.isActive = 'Y'
	LEFT OUTER JOIN AD_User srep ON io.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID<>100 AND srep.isActive = 'Y'
	LEFT OUTER JOIN AD_User cont ON io.AD_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
	LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
	LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID AND srgr.isActive = 'Y'
	
	LEFT JOIN LATERAL 
	(
		SELECT 
		First_Agg ( o.DocumentNo ORDER BY o.DocumentNo ) ||
				CASE WHEN Count( distinct o.documentNo ) > 1 THEN ' ff.' ELSE '' END AS DocNo
		FROM M_InOutLine iol
		JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
		JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
		
		WHERE iol.M_InOut_ID = $1
	) o ON TRUE
WHERE
	io.m_inout_id = $1 AND io.isActive = 'Y'
$$
LANGUAGE sql STABLE
;



------------------------

--- add qtyordered , orderUOMSymbol and qtydeliveredcatch
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details ( IN Record_ID numeric, IN AD_Language Character Varying(6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details
(
  Line              Numeric(10, 0),
  Name              Character Varying,
  Attributes        Text,
  HUQty             Numeric,
  HUName            Text,
  qtyEntered        Numeric,
  PriceEntered      Numeric,
  UOMSymbol         Character Varying(10),
  StdPrecision      Numeric(10, 0),
  LineNetAmt        Numeric,
  Discount          Numeric,
  IsDiscountPrinted Character(1),
  QtyPattern        text,
  Description       Character Varying,
  bp_product_no     character varying(30),
  bp_product_name   character varying(100),
  best_before_date  text,
  lotno             character varying,
  p_value           character varying(30),
  p_description     character varying(255),
  inout_description character varying(255),
  iscampaignprice   character(1),
  qtyordered        Numeric,
  orderUOMSymbol    Character Varying(10)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details(IN Record_ID   numeric,
                                                                            IN AD_Language Character Varying(6))
  RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details AS
$$

SELECT
  iol.line,
  COALESCE(pt.Name, p.name)                                                                       AS Name,
  CASE WHEN Length(att.Attributes) > 15
    THEN att.Attributes || E'\n'
  ELSE att.Attributes
  END                                                                                             AS Attributes,
  iol.QtyEnteredTU                                                                                AS HUQty,
  pi.name                                                                                         AS HUName,
  (case when qtydeliveredcatch is not null
    then qtydeliveredcatch
   else iol.QtyEntered * COALESCE(multiplyrate, 1) end)                                           AS QtyEntered,
  COALESCE(ic.PriceEntered_Override, ic.PriceEntered)                                             AS PriceEntered,
  (case when qtydeliveredcatch is not null
    then COALESCE(uomct.UOMSymbol, uomc.UOMSymbol)
   else COALESCE(uomt.UOMSymbol, uom.UOMSymbol) end)                                              AS UOMSymbol,
  uom.stdPrecision,
  COALESCE(ic.PriceActual_Override, ic.PriceActual) * iol.MovementQty * COALESCE(multiplyrate, 1) AS linenetamt,
  COALESCE(ic.Discount_Override, ic.Discount)                                                     AS Discount,
  bp.isDiscountPrinted,
  CASE WHEN uom.StdPrecision = 0
    THEN '#,##0'
  ELSE Substring('#,##0.000' FROM 0 FOR 7 + uom.StdPrecision :: integer) END                      AS QtyPattern,
  iol.Description,
  -- in case there is no C_BPartner_Product, fallback to the default ones
  COALESCE(NULLIF(bpp.ProductNo, ''), p.value)                                                    as bp_product_no,
  COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name)                                          as bp_product_name,
  to_char(att.best_before_date :: date, 'MM.YYYY')                                                AS best_before_date,
  att.lotno,
  p.value                                                                                         AS p_value,
  p.description                                                                                   AS p_description,
  io.description                                                                                  AS inout_description,
  ol.iscampaignprice,
  ol.qtyordered,
  COALESCE(uomt_ol.UOMSymbol, uom_ol.UOMSymbol)                                                   AS orderUOMSymbol
FROM
  M_InOutLine iol
  INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
  LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
  LEFT OUTER JOIN (
                    SELECT
                      AVG(ic.PriceEntered_Override) AS PriceEntered_Override,
                      AVG(ic.PriceEntered)          AS PriceEntered,
                      AVG(ic.PriceActual_Override)  AS PriceActual_Override,
                      AVG(ic.PriceActual)           AS PriceActual,
                      AVG(ic.Discount_Override)     AS Discount_Override,
                      AVG(ic.Discount)              AS Discount,
                      Price_UOM_ID,
                      iciol.M_InOutLine_ID
                    FROM C_InvoiceCandidate_InOutLine iciol
                      INNER JOIN C_Invoice_Candidate ic
                        ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
                      INNER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID AND iol.isActive = 'Y'
                    WHERE iol.M_InOut_ID = $1 AND iciol.isActive = 'Y'
                    GROUP BY Price_UOM_ID, iciol.M_InOutLine_ID
                  ) ic ON iol.M_InOutLine_ID = ic.M_InOutLine_ID

  -- get details from order line
  LEFT OUTER JOIN c_orderline ol ON ol.c_orderline_id = iol.c_orderline_id
  LEFT OUTER JOIN C_UOM uom_ol ON uom_ol.C_UOM_ID = ol.C_UOM_ID AND uom_ol.isActive = 'Y'
  LEFT OUTER JOIN C_UOM_Trl uomt_ol
    ON uomt_ol.C_UOM_ID = uom_ol.C_UOM_ID AND uomt_ol.AD_Language = $2 AND uomt_ol.isActive = 'Y'

  -- Get Packing instruction
  LEFT OUTER JOIN
  (
    SELECT
      String_Agg(DISTINCT name, E'\n'
      ORDER BY name) AS Name,
      M_InOutLine_ID
    FROM
      (
        SELECT DISTINCT
          -- 08604 - in IT1 only one PI was shown though 2 were expected. Only the fallback can do this, so we use it first
          COALESCE(pifb.name, pi.name) AS name,
          iol.M_InOutLine_ID
        FROM
          M_InOutLine iol
          -- Get PI directly from InOutLine (1 to 1)
          LEFT OUTER JOIN M_HU_PI_Item_Product pi
            ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID AND pi.isActive = 'Y'
          LEFT OUTER JOIN M_HU_PI_Item piit ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID AND piit.isActive = 'Y'
          -- Get PI from HU assignments (1 to n)
          -- if the HU was set manually don't check the assignments
          LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID('M_InOutLine')))
                                                  AND asgn.Record_ID = iol.M_InOutLine_ID AND asgn.isActive = 'Y' and
                                                  iol.ismanualpackingmaterial = 'N'
          LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
          LEFT OUTER JOIN M_HU_PI_Item_Product pifb
            ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
          LEFT OUTER JOIN M_HU_PI_Item pit ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID AND pit.isActive = 'Y'
          --
          LEFT OUTER JOIN M_HU_PI_Version piv
            ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID) AND piv.isActive = 'Y'
        WHERE
          piv.M_HU_PI_Version_ID != 101
          AND iol.M_InOut_ID = $1 AND iol.isActive = 'Y'
      ) x
    GROUP BY M_InOutLine_ID
  ) pi ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
  -- Product and its translation
  LEFT OUTER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
  LEFT OUTER JOIN M_Product_Trl pt ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
  LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'

  -- Unit of measurement and its translation
  LEFT OUTER JOIN C_UOM uom ON ic.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
  LEFT OUTER JOIN C_UOM_Trl uomt ON ic.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
  LEFT OUTER JOIN C_UOM_Conversion conv ON conv.C_UOM_ID = iol.C_UOM_ID
                                           AND conv.C_UOM_To_ID = ic.Price_UOM_ID
                                           AND iol.M_Product_ID = conv.M_Product_ID
                                           AND conv.isActive = 'Y'

  -- Unit of measurement and its translation for catch weight
  LEFT OUTER JOIN C_UOM uomc ON uomc.C_UOM_ID = iol.catch_uom_id
  LEFT OUTER JOIN C_UOM_Trl uomct on uomct.c_UOM_ID = uom.C_UOM_ID and uomct.AD_Language = $2
  -- Attributes
  LEFT OUTER JOIN (
                    SELECT
                      String_agg(at.ai_value, ', '
                      ORDER BY Length(at.ai_value), at.ai_value)
                        FILTER (WHERE at.at_value not in ('HU_BestBeforeDate', 'Lot-Nummer'))
                                                                     AS Attributes,

                      at.M_AttributeSetInstance_ID,
                      String_agg(replace(at.ai_value, 'MHD: ', ''), ', ')
                        FILTER (WHERE at.at_value like 'HU_BestBeforeDate')
                                                                     AS best_before_date,
                      String_agg(ai_value, ', ')
                        FILTER (WHERE at.at_value like 'Lot-Nummer') AS lotno

                    FROM Report.fresh_Attributes at
                      JOIN M_InOutLine iol
                        ON at.M_AttributeSetInstance_ID = iol.M_AttributeSetInstance_ID AND iol.isActive = 'Y'
                    WHERE at.at_value IN ('1000002', '1000001', '1000030', '1000015', 'HU_BestBeforeDate', 'Lot-Nummer')
                          -- Label, Herkunft, Aktionen, Marke (ADR)
                          AND iol.M_InOut_ID = $1
                    GROUP BY at.M_AttributeSetInstance_ID
                  ) att ON iol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID

  LEFT OUTER JOIN
      de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(p.M_Product_ID, bp.C_BPartner_ID,
                                                                       att.M_AttributeSetInstance_ID) as bpp on 1 = 1
WHERE
  iol.M_InOut_ID = $1 AND iol.isActive = 'Y'
  AND (COALESCE(pc.M_Product_Category_ID, -1) !=
       getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID))
  AND iol.QtyEntered != 0 -- Don't display lines without a Qty. See 08293
ORDER BY
  line

$$
LANGUAGE sql
STABLE;



----------------

-- add phone2

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report( IN Email Character Varying(60), IN IsSOTrx Character Varying(1), IN AD_Org_ID Numeric );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report 
(
	Name Character Varying(60),
	Address Character Varying(100),
	VATaxID Character Varying(60),
	TaxID Character Varying(60),
	Phone Character Varying,
	Phone2 Character Varying,
	Fax Character Varying,
	Postal Character Varying(10),
	City Character Varying(60),
	Email Character Varying(60),
	URL Character Varying
);

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report( IN Email Character Varying(60), IN IsSOTrx Character Varying(1), IN AD_Org_ID Numeric ) 
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report
AS
$$
(
SELECT
	org_bp.name AS name,
	loc.address1 AS address,
	org_bp.vataxid,
	org_bp.taxid,
	COALESCE(us.phone, org_bpl.phone, '-') AS phone,
	us.phone2,
	COALESCE(us.fax, org_bpl.fax, '-') AS fax,
	loc.postal,
	loc.city,
	COALESCE($1, us.email) as email,
	org_bp.URL
FROM
	ad_org ad_org
	INNER JOIN c_bpartner org_bp ON ad_org.ad_org_id = org_bp.ad_orgbp_id AND org_bp.isActive = 'Y'
	INNER JOIN C_BPartner_Location org_bpl ON org_bpl.C_BPartner_Location_ID =
	(
		SELECT C_BPartner_Location_ID FROM C_BPartner_Location sub_bpl
		WHERE sub_bpl.c_bpartner_id = org_bp.c_bpartner_id AND sub_bpl.isActive = 'Y'
		ORDER BY IsBillToDefault DESC, IsBillTo DESC
		LIMIT 1
	)
	INNER JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id AND loc.isActive = 'Y'
	LEFT OUTER JOIN c_country cou ON loc.c_country_id = cou.c_country_id AND cou.isActive = 'Y'
	LEFT OUTER JOIN AD_User us ON us.AD_User_ID = 
	(
		SELECT AD_User_ID FROM AD_User sub_us
		WHERE org_bp.c_bpartner_id = sub_us.c_bpartner_id AND sub_us.isActive = 'Y'
		AND (($2 = 'Y' AND IsSalesContact = 'Y') OR ($2 = 'N' AND IsPurchaseContact = 'Y'))
		ORDER BY IsDefaultContact DESC
		LIMIT 1
	)
WHERE
	ad_org.ad_org_id = $3 AND ad_org.isActive = 'Y'
)
$$
LANGUAGE sql STABLE 
;
