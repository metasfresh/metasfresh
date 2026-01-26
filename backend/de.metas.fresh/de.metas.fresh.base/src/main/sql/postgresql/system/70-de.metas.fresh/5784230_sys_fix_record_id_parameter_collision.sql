-- Migration script: Fix record_id parameter naming collision
-- Generated for: game_time_hotfix branch
-- Issue: Parameter name 'record_id' shadows built-in record identifier in PostgreSQL
-- Fix: Rename parameter from 'record_id' to 'p_record_id'
-- 2026-01-18

-- Function: Docs_Purchase_InOut_Customs_Root
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Customs_Root(IN record_id numeric);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Customs_Root(IN p_record_id numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Customs_Root(IN p_record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Customs_Root(IN p_record_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	c_orderline_id numeric,
	c_order_id numeric,
	HasWeightSnapshot boolean
	)
AS
$$	
SELECT
 ol.AD_Org_ID,
 ol.C_OrderLine_ID,
 ol.C_Order_ID
,EXISTS(
  SELECT 0
  FROM
   M_InOut io
   INNER JOIN M_HU_Assignment thuas ON iol.M_InOutLine_ID = thuas.Record_ID AND ad_table_id = (SELECT Get_Table_ID('M_InOutLine')) AND thuas.isActive = 'Y'
   -- Get snapshot weight
   INNER JOIN M_HU_Attribute_Snapshot thuwns ON thuas.M_HU_ID = thuwns.M_HU_ID
    AND thuwns.M_Attribute_ID = ((SELECT M_Attribute_ID FROM M_Attribute WHERE value = 'WeightNet'))
    AND thuwns.Snapshot_UUID = io.Snapshot_UUID
	AND thuwns.isActive = 'Y'
  WHERE iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
   
 ) AS HasWeightSnapshot

FROM C_OrderLine ol
INNER JOIN M_ReceiptSchedule rs ON rs.C_OrderLine_ID = ol.C_OrderLine_ID AND rs.AD_Table_ID = ( SELECT Get_Table_ID('C_OrderLine') ) AND rs.isActive = 'Y'
INNER JOIN M_ReceiptSchedule_Alloc rsa ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID AND rsa.isActive = 'Y'
INNER JOIN M_InOutLine iol ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
WHERE iol.M_InOut_ID = $1
LIMIT 1
$$
LANGUAGE sql STABLE;

-- Function: Docs_Purchase_InOut_Material_Disposal_Details_Footer
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Details_Footer(IN record_id numeric);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Details_Footer(IN p_record_id numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Details_Footer(IN p_record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Details_Footer(IN p_record_id numeric)
RETURNS TABLE 
	(
	iso_code character(3)
	)
AS
$$	
SELECT
	c.iso_code
FROM M_Inventory i

INNER JOIN M_InventoryLine il ON i.M_Inventory_ID = il.M_Inventory_ID AND il.isActive = 'Y'
INNER JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
INNER JOIN C_BPartner_Location bpl ON bp.C_BPartner_ID = bpl.C_BPartner_ID AND bpl.isActive = 'Y'
LEFT JOIN C_Location l ON bpl.C_Location_id = l.C_Location_ID AND l.isActive = 'Y'

LEFT OUTER JOIN M_Pricingsystem ps ON bp.PO_Pricingsystem_ID = ps.M_Pricingsystem_ID AND ps.isActive = 'Y'
LEFT OUTER JOIN M_PriceList pl ON ps.M_Pricingsystem_ID = pl.M_Pricingsystem_ID AND pl.C_Country_ID = l.C_Country_ID AND pl.isActive = 'Y'

LEFT OUTER JOIN AD_ClientInfo ci ON ci.AD_Client_ID=i.ad_client_id AND ci.isActive = 'Y'
LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID=ci.C_AcctSchema1_ID AND acs.isActive = 'Y'
LEFT OUTER JOIN C_Currency c ON COALESCE(pl.C_Currency_ID,acs.C_Currency_ID)=c.C_Currency_ID AND c.isActive = 'Y'
WHERE i.M_Inventory_ID = $1 AND i.isActive = 'Y'
limit 1
$$
LANGUAGE sql STABLE;



-- Function: Docs_Purchase_InOut_Material_Disposal_Page_Header
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Page_Header(IN record_id numeric, IN ad_language character varying);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Page_Header(IN p_record_id numeric, IN ad_language character varying);

-- Function: de_metas_endcustomer_fresh_reports.docs_purchase_inout_page_header(numeric, character varying)

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_purchase_InOut_Material_Disposal_page_header(numeric, character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_purchase_InOut_Material_Disposal_page_header(IN p_record_id numeric, IN ad_language character varying)
  RETURNS TABLE(
				documentno text, 
				inventorydate timestamp without time zone, 
				bp_value character varying, 
				printname character varying
				) 
  AS
$$

SELECT 
	i.DocumentNo AS documentNo,
	i.movementDate as inventorydate,
	COALESCE(dtt.printName, dt.printName) as printname,
	bp.Value as BP_Value
	

FROM M_Inventory i

-- data from inventory
INNER JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.isActive = 'Y' AND dtt.AD_Language = $2

--data from inout
INNER JOIN M_InventoryLine il ON i.M_Inventory_ID = il.M_Inventory_ID AND il.isActive = 'Y'
INNER JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'

INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'

WHERE i.M_Inventory_ID = $1 AND i.isActive = 'Y'

ORDER BY bp_value	
	
$$
  LANGUAGE sql STABLE;


-- Function: Docs_Purchase_InOut_Material_Disposal_Root
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Root(IN record_id numeric);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Root(IN p_record_id numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Root(IN p_record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Root(IN p_record_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	m_inout_id integer,
	displayhu text,
	DocStatus Character (2)
	)
AS
$$	
SELECT 
	i.ad_org_id,
	MIN(io.M_inOut_ID)::int,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM M_InventoryLine il1
			WHERE m_hu_pi_item_product_id IS NOT NULL AND il1.M_Inventory_ID = $1
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
	i.docstatus
	
FROM M_Inventory i


INNER JOIN M_InventoryLine il ON i.M_Inventory_ID = il.M_Inventory_ID
INNER JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'


WHERE i.M_Inventory_ID = $1 AND i.isActive = 'Y'

GROUP BY
	i.ad_org_id,i.docstatus

$$
LANGUAGE sql STABLE;


-- Function: Docs_Purchase_InOut_Page_Header
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Page_Header(IN record_id numeric, IN ad_language character varying);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Page_Header(IN p_record_id numeric, IN ad_language character varying);

-- Function: de_metas_endcustomer_fresh_reports.docs_purchase_inout_page_header(numeric, character varying)

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_purchase_inout_page_header(numeric, character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_purchase_inout_page_header(IN p_record_id numeric, IN ad_language character varying)
  RETURNS TABLE(
				documentno text, 
				dateinvoiced timestamp without time zone, 
				bp_value character varying, 
				printname character varying
				) 
  AS
$$

SELECT
	CASE
		WHEN io.DocNo_hi = io.DocNo_lo THEN io.DocNo_lo
		ELSE io.DocNo_lo || ' ff.'
	END AS documentno,
	io.movementdate AS dateinvoiced,
	bp.value AS bp_value,
	COALESCE(dtt.PrintName, dt.PrintName) AS PrintName
FROM
	C_OrderLine ol
	INNER JOIN (
		SELECT
			ol.C_Order_ID,
			MAX(io.movementdate) as movementdate,
			MIN(io.Documentno) as Docno_lo,
			MAX(io.Documentno) as Docno_hi,
			MAX(io.C_DocType_ID) AS C_DocType_ID
		FROM
			C_OrderLine ol
	
			INNER JOIN M_InOutLine iol ON ol.C_OrderLine_ID = iol.C_OrderLine_ID AND iol.isActive = 'Y'
			INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
		WHERE ol.C_OrderLine_ID = $1 AND ol.isActive = 'Y'
		GROUP BY
			ol.C_Order_ID
	) io ON ol.C_Order_ID = io.C_Order_ID
	INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
	INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
WHERE
	ol.C_OrderLine_ID = $1 AND ol.isActive = 'Y'
$$
  LANGUAGE sql STABLE;


-- Function: Docs_Purchase_InOut_POS_Root
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_POS_Root(IN record_id numeric, IN ad_table_id numeric);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_POS_Root(IN p_record_id numeric, IN ad_table_id numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_POS_Root(IN p_record_id numeric, IN ad_table_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_POS_Root(IN p_record_id numeric, IN ad_table_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	c_order_id integer,
	c_orderline_id integer,
	displayhu text
	)
AS
$$
SELECT
	ol.ad_org_id,
	ol.C_Order_ID::int,
	ol.C_OrderLine_ID::int,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM c_orderline ol1
			join c_order o ON ol1.c_order_id = o.c_order_id and o.isactive = 'Y'
			join m_inout io2 ON o.c_order_id = io2.c_order_id and io2.isactive = 'Y'
			join m_inoutLine iol2 ON io2.m_inout_id = iol2.m_inout_id and iol2.isactive = 'Y'

			INNER JOIN M_Product p ON iol2.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol2.AD_Client_ID, iol2.AD_Org_ID)
				AND ol.C_OrderLine_ID = ol1.C_OrderLine_ID AND ol1.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu
FROM
	C_OrderLine ol
WHERE
	ol.C_OrderLine_ID = $1 AND ol.isActive = 'Y' AND get_Table_ID('C_OrderLine') = $2

UNION
(SELECT
	ol.ad_org_id,
	ol.C_Order_ID::int,
	ol.C_OrderLine_ID::int,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM c_orderline ol1
			join c_order o ON ol1.c_order_id = o.c_order_id and o.isactive = 'Y'
			join m_inout io2 ON o.c_order_id = io2.c_order_id and io2.isactive = 'Y'
			join m_inoutLine iol2 ON io2.m_inout_id = iol2.m_inout_id and iol2.isactive = 'Y'

			INNER JOIN M_Product p ON iol2.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol2.AD_Client_ID, iol2.AD_Org_ID)
				AND ol.C_OrderLine_ID = ol1.C_OrderLine_ID AND ol1.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu
FROM
M_ReceiptSchedule rs
JOIN C_OrderLine ol ON rs.C_OrderLine_ID = ol.C_OrderLine_id AND ol.isActive = 'Y'	
WHERE
	rs.M_ReceiptSchedule_ID = $1 AND rs.isActive = 'Y' AND get_Table_ID('M_ReceiptSchedule') = $2

)
$$
LANGUAGE sql STABLE;

-- Function: Docs_Purchase_InOut_Root
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Root(IN p_record_id numeric);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Root(IN p_p_record_id numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Root(IN p_record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Root(IN p_record_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	c_orderline_id integer,
	displayhu text,
	c_order_id integer,
	movementdate date
	)
AS
$$	
SELECT
	ol.AD_Org_ID,
	ol.C_OrderLine_ID::int,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM c_orderline ol1
			join c_order o ON ol1.c_order_id = o.c_order_id and o.isactive = 'Y'
			join m_inout io2 ON o.c_order_id = io2.c_order_id and io2.isactive = 'Y'
			join m_inoutLine iol2 ON io2.m_inout_id = iol2.m_inout_id and iol2.isactive = 'Y'

			INNER JOIN M_Product p ON iol2.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol2.AD_Client_ID, iol2.AD_Org_ID)
				AND ol.C_OrderLine_ID = ol1.C_OrderLine_ID AND ol1.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
	ol.C_Order_ID::int,
	(
		SELECT	MAX(io.movementdate)::date AS Movementdate
		FROM	M_ReceiptSchedule rs
			INNER JOIN M_ReceiptSchedule_Alloc rsa ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
			INNER JOIN M_InOutLine siol ON rsa.M_InOutLine_ID = siol.M_InOutLine_ID
			INNER JOIN M_InOut io ON siol.M_InOut_ID = io.M_InOut_ID
		WHERE 	rs.AD_Table_ID = (SELECT Get_Table_ID( 'C_OrderLine' )) AND ol.C_OrderLine_ID = rs.C_OrderLine_ID
	) AS MovementDate
FROM
	-- All In Out Lines directly linked to the order
	M_InOutLine iol
	INNER JOIN M_ReceiptSchedule_Alloc rsa ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID
	INNER JOIN M_ReceiptSchedule rs ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
		AND rs.AD_Table_ID = (SELECT Get_Table_ID( 'C_OrderLine' ))
	INNER JOIN C_OrderLine ol ON rs.C_OrderLine_ID = ol.C_OrderLine_ID
WHERE
	iol.M_InOut_ID = p_record_id

UNION
DISTINCT

SELECT ol.AD_Org_ID,
       ol.C_OrderLine_ID::int,
       CASE
           WHEN
               EXISTS(SELECT 0
                      FROM c_orderline ol1
                               JOIN c_order o ON ol1.c_order_id = o.c_order_id AND o.isactive = 'Y'
                               JOIN m_inout io2 ON o.c_order_id = io2.c_order_id AND io2.isactive = 'Y'
                               JOIN m_inoutLine iol2 ON io2.m_inout_id = iol2.m_inout_id AND iol2.isactive = 'Y'

                               INNER JOIN M_Product p ON iol2.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
                               INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
                      WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol2.AD_Client_ID, iol2.AD_Org_ID)
                        AND ol.C_OrderLine_ID = ol1.C_OrderLine_ID
                        AND ol1.isActive = 'Y')
               THEN 'Y'
               ELSE 'N'
       END                                             AS displayhu,
       ol.C_Order_ID::int,
       (SELECT MAX(io.movementdate)::date AS Movementdate
        FROM M_InOut io
                 INNER JOIN M_InOutLine siol ON io.m_inout_id = siol.m_inout_id
        WHERE ol.C_OrderLine_ID = siol.C_OrderLine_ID) AS MovementDate
FROM
    M_InOutLine iol
        INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
WHERE iol.M_InOut_ID = p_record_id

LIMIT 1
$$
LANGUAGE sql STABLE;

-- Function: Docs_Purchase_InOut_Vendor_Returns_Description
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Description(IN record_id numeric, IN AD_Language Character Varying (6));
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Description(IN p_record_id numeric, IN AD_Language Character Varying (6));

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Description(IN p_record_id numeric, IN AD_Language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Description(IN p_record_id numeric, IN AD_Language Character Varying (6))
RETURNS TABLE 
	(
	printname character varying(60),
	io_printname character varying(60),
	documentno character varying(30),
	io_documentno text,
	bp_value character varying(40),
	movementdate_io timestamp without time zone, 
	movementdate timestamp without time zone
	)
AS
$$	
SELECT
	COALESCE(dtt.printName, dt.printName) as printname,
	COALESCE(io_dtt.printName, io_dt.printName) as io_printname,
	io.DocumentNo AS documentNo,
	CASE
		WHEN io_origin.DocNo_hi = io_origin.DocNo_lo THEN io_origin.DocNo_lo
		ELSE io_origin.DocNo_lo || ' ff.'
	END AS io_documentno,
	bp.Value as BP_Value,
	io.movementDate as movementDate,
	io_origin.movementDate as movementdate_io

FROM M_Inout io --vendor return
--INNER JOIN M_InOutLine iol ON io.M_Inout_ID = iol.M_Inout_ID
INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.isActive = 'Y' AND dtt.AD_Language = $2


--data from original inout
INNER JOIN (
	SELECT 
		iol.M_InOut_ID,
		MAX(io_origin.movementdate) as movementdate,
		MIN(io_origin.Documentno) as Docno_lo,
		MAX(io_origin.Documentno) as Docno_hi,
		MAX(io_origin.C_DocType_ID) AS C_DocType_ID
	FROM M_InOutLine iol
	INNER JOIN M_InOutLine iol_origin ON iol.return_origin_inoutline_id = iol_origin.M_InOutLine_ID AND iol_origin.isActive = 'Y'
	INNER JOIN M_InOut io_origin ON iol_origin.M_InOut_ID = io_origin.M_InOut_ID AND io_origin.isActive = 'Y'
	
	GROUP BY iol.M_InOut_ID
) io_origin ON io.M_InOut_ID = io_origin.M_InOut_ID

INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
INNER JOIN C_DocType io_dt ON io_origin.C_DocType_ID = io_dt.C_DocType_ID AND io_dt.isActive = 'Y'
LEFT OUTER JOIN C_DocType_Trl io_dtt ON io_dt.C_DocType_ID = io_dtt.C_DocType_ID AND io_dtt.isActive = 'Y' AND io_dtt.AD_Language = $2

WHERE io.M_InOut_ID = $1

	
$$
LANGUAGE sql STABLE;

-- Function: Docs_Purchase_InOut_Vendor_Returns_Details_HU
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Details_HU(IN record_id numeric, IN AD_Language Character Varying (6));
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Details_HU(IN p_record_id numeric, IN AD_Language Character Varying (6));

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Details_HU(IN p_record_id numeric, IN AD_Language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Details_HU(IN p_record_id numeric, IN AD_Language Character Varying (6))
RETURNS TABLE 
(
	Name character varying, 
	HUQty numeric,
	MovementQty numeric,
	UOMSymbol character varying,
	Description character varying
)
AS
$$	

SELECT 
	Name, -- product
	SUM( HUQty ) AS HUQty,
	SUM( MovementQty ) AS MovementQty,
	UOMSymbol,
	iol.Description

FROM

(
SELECT
	COALESCE(pt.Name, p.name) AS Name,
	iol.QtyEnteredTU AS HUQty,
	iol.MovementQty AS MovementQty,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	iol.Description
	

FROM M_Inout io --vendor return

INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID

INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
				AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'

-- Unit of measurement & its translation
INNER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'

WHERE
		pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID) AND io.M_InOut_ID = $1
)iol


GROUP BY 
	Name, 
	UOMSymbol,
	iol.Description
$$
LANGUAGE sql STABLE;

-- Function: Docs_Purchase_InOut_Vendor_Returns_Root
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Root(IN record_id numeric);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Root(IN p_record_id numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Root(IN p_record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Root(IN p_record_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	displayhu text,
	DocStatus Character (2)
	)
AS
$$	
SELECT
	io.AD_Org_ID,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM M_InOutLine iol1
			INNER JOIN M_Product p ON iol1.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol1.AD_Client_ID, iol1.AD_Org_ID)
				AND io.M_InOut_ID = iol1.M_InOut_ID AND iol1.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
	io.docstatus
FROM
	M_InOut io
WHERE
	io.M_InOut_ID = $1 AND io.isActive = 'Y'
$$
LANGUAGE sql STABLE;

-- Function: Docs_Purchase_Invoice_Root
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Root(IN record_id numeric, IN ad_language Character Varying (6));
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Root(IN p_record_id numeric, IN ad_language Character Varying (6));

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Root(IN p_record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Root(IN p_record_id numeric, IN ad_language Character Varying (6))
RETURNS TABLE 
	(
	ad_org_id numeric,
	docstatus character(2),
	printname character varying(60),
	displayhu text
	)
AS
$$
SELECT
	i.AD_Org_ID,
	i.DocStatus,
	dt.PrintName,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM C_InvoiceLine il
			INNER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
			AND il.C_Invoice_ID = i.C_Invoice_ID AND il.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu
FROM
	C_Invoice i
	INNER JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON i.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
WHERE
	i.C_Invoice_ID = $1 AND i.isActive = 'Y'

$$
LANGUAGE sql STABLE;

-- Function: Docs_Purchase_Order_Details_HU
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details_HU(IN record_id numeric, IN ad_language Character Varying (6));
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details_HU(IN p_record_id numeric, IN ad_language Character Varying (6));

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details_HU(IN p_record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details_HU(IN p_record_id numeric, IN ad_language Character Varying (6))

RETURNS TABLE
(
	qtyordered numeric, 
	Name character varying,
	Price numeric, 
	LineNetAmt numeric,  
	UOMSymbol character varying(10),
	Description character varying

)
AS
$$
SELECT
	ol.QtyOrdered,
	COALESCE(pt.Name, p.name)		AS Name,
	ol.PriceEntered			AS Price,
	ol.LineNetAmt,
	COALESCE(uom.UOMSymbol, uomt.UOMSymbol)	AS UOMSymbol,
	ol.Description
FROM
	C_OrderLine ol
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ol.C_UOM_ID = uom.C_UOM_ID
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2
WHERE
	ol.C_Order_ID = $1 AND ol.isActive = 'Y'
	AND pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)

$$
LANGUAGE sql STABLE	
;

-- Function: Docs_Purchase_Order_Root
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Root(IN record_id numeric, IN ad_language Character Varying (6));
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Root(IN p_record_id numeric, IN ad_language Character Varying (6));

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Root(IN p_record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Root(IN p_record_id numeric, IN ad_language Character Varying (6))
RETURNS TABLE 
	(
	ad_org_id numeric(10,0),
	docstatus character(2),
	printname character varying(60),
	displayhu text
	)
AS
$$	

SELECT
	o.AD_Org_ID,
	o.DocStatus,
	dt.PrintName,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM C_OrderLine ol
			INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
				AND ol.C_Order_ID = o.C_Order_ID
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu
FROM
	C_Order o
	INNER JOIN C_DocType dt ON o.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON o.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
WHERE
	o.C_Order_ID = $1 AND o.isActive = 'Y'
$$	
LANGUAGE sql STABLE
;

-- Function: Docs_Sales_InOut_Customer_Returns_Description
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Customer_Returns_Description(IN record_id numeric, IN AD_Language Character Varying (6));
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Customer_Returns_Description(IN p_record_id numeric, IN AD_Language Character Varying (6));

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Customer_Returns_Description(IN p_record_id numeric, IN AD_Language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Customer_Returns_Description(IN p_record_id numeric, IN AD_Language Character Varying (6))
RETURNS TABLE 
	(
	printname character varying(60),
	documentno character varying(30),
	bp_value character varying(40),
	movementdate timestamp without time zone
	)
AS
$$	
SELECT
	COALESCE(dtt.printName, dt.printName) as printname,
	io.DocumentNo AS documentNo,
	bp.Value as BP_Value,
	io.movementDate as movementDate

FROM M_Inout io --customer return
INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.isActive = 'Y' AND dtt.AD_Language = $2


INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'

WHERE io.M_InOut_ID = $1

	
$$
LANGUAGE sql STABLE;

-- Function: Docs_Sales_Order_Root
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Root(IN record_id numeric, IN ad_language Character Varying (6));
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Root(IN p_record_id numeric, IN ad_language Character Varying (6));

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Root(IN p_record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Root(IN p_record_id numeric, IN ad_language Character Varying (6))
RETURNS TABLE 
	(
	ad_org_id numeric(10,0),
	docstatus character(2),
	printname character varying(60),
	C_Currency_ID numeric,
	poreference varchar(60),
	displayhu text,
	isoffer character(1),
	isprepay character(1)
	)
AS
$$	

SELECT
	o.AD_Org_ID,
	o.DocStatus,
	dt.PrintName,
	o.C_Currency_ID,
	poreference,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM C_OrderLine ol
			INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
			AND ol.C_Order_ID = o.C_Order_ID AND ol.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
		CASE WHEN dt.docbasetype = 'SOO' AND dt.docsubtype IN ('ON', 'OB')
		THEN 'Y'
		ELSE 'N'
	END AS isoffer,
	CASE WHEN dt.docbasetype = 'SOO' AND dt.docsubtype ='PR'
		THEN 'Y'
		ELSE 'N'
	END AS isprepay
FROM
	C_Order o
	INNER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON o.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
WHERE
	o.C_Order_ID = $1 AND o.isActive = 'Y'
$$
LANGUAGE sql STABLE
;

-- Function: Docs_Sales_OrderCheckup_Description
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Description(IN record_id numeric);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Description(IN p_record_id numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Description(IN p_record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Description(IN p_record_id numeric)
RETURNS TABLE 
	(
	bpValue character varying(50),
	order_no character varying(30),
	reference character varying(40),
	preparationdate timestamp with time zone,
	datepromised timestamp with time zone,
	ReportDocumentTypeName character varying(40),
	handoverlocation character varying(140),
    warehousename character varying(60),
    plantname character varying(60)
	)
AS
$$	
SELECT
	r.bpValue,
	r.document_no as order_no,
	r.poreference as reference,
	r.preparationdate as PreparationDate,
	r.datepromised as DatePromised,
	r.ReportDocumentTypeName,
	r.handoverlocation ,
    r.warehousename,
    r.plantname
	
FROM report.RV_C_Order_MFGWarehouse_Report_Description r
WHERE
	r.C_Order_MFGWarehouse_Report_ID = $1
LIMIT 1

$$
LANGUAGE sql STABLE;

-- Function: Docs_Sales_OrderCheckup_Details
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Details(IN record_id numeric);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Details(IN p_record_id numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Details(IN p_record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Details(IN p_record_id numeric)
RETURNS TABLE 
	(
	line numeric,
	attributes text,
	prodValue character varying,
	value character varying,
	name character varying(255),
	ean character varying,
	pricelist numeric,
	capacity numeric,
	priceactual numeric,
	qtyenteredtu numeric,
	qtyentered numeric,
	container character varying(60),
	uomsymbol character varying(10),
	c_order_mfgwarehouse_report_id numeric,
	reportdocumenttype character varying(2),
	C_Order_MFGWarehouse_ReportLine_ID numeric,
	c_order_id numeric,
	c_orderline_id numeric,
	m_warehouse_id numeric,
	pp_plant_id numeric,
	c_bpartner_id numeric,
	datepromised timestamp with time zone,
	barcode character varying(255)
	)
AS
$$	

SELECT
	ol.line,
	att.Attributes,
	p.value AS prodValue,
	COALESCE(bpp.ProductNo, p.value) AS Value,
	p.Name AS Name,
	COALESCE(bpp.UPC, p.UPC) AS EAN,
	-- Rounding these columns is important to have them in one group
	-- Jasper groups by comparing the BigDecimals. In that logic, 1.00 is not the same as 1
	round(ol.pricelist, 3) AS PriceList,
	round(ip.qty, 3) AS Capacity,
	round(ol.Priceactual, 3) AS PriceActual,
	ol.QtyEnteredTU,
	ol.QtyEntered,
	pm.name as Container,
	uom.UOMSymbol AS UOMSymbol,
	--
	-- Filtering columns
	report.C_Order_MFGWarehouse_Report_ID,
	report.DocumentType as ReportDocumentType,
	reportLine.C_Order_MFGWarehouse_ReportLine_ID,
	o.C_Order_ID,
	ol.C_OrderLine_ID,
	report.M_Warehouse_ID,
	report.PP_Plant_ID,
	o.C_BPartner_ID,
	o.DatePromised,
	reportLine.barcode AS barcode 
FROM
	C_Order_MFGWarehouse_Report report
	INNER JOIN C_Order o on (report.C_Order_ID=o.C_Order_ID) AND o.isActive = 'Y'
	INNER JOIN C_Order_MFGWarehouse_ReportLine reportLine on (reportLine.C_Order_MFGWarehouse_Report_ID=report.C_Order_MFGWarehouse_Report_ID)
	INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID = reportLine.C_OrderLine_ID) AND ol.isActive = 'Y'
	--
	LEFT OUTER JOIN C_BPartner bp ON ol.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item_Product ip ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item pii ON ip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID AND pii.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item pmi ON pmi.M_HU_PI_Version_ID = pii.M_HU_PI_Version_ID  AND pmi.isActive = 'Y'
		AND pmi.ItemType= 'PM'
	LEFT OUTER JOIN M_HU_PackingMaterial pm ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID AND pm.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'

	LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND bpp.isActive='Y'
		AND p.M_Product_ID = bpp.M_Product_ID
	LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom ON ol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	-- ADR Attribute
	LEFT OUTER JOIN	LATERAL(
		SELECT 	String_agg ( ai_value, ', ' ) AS Attributes, M_AttributeSetInstance_ID
		FROM 	Report.fresh_Attributes
		WHERE	at_Value IN ( '1000015', '1000001', '1000002' ) -- Marke (ADR), task 08891: also Herkunft, task 2237: also Label
			AND M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID AND  ol.M_AttributeSetInstance_ID != 0
		GROUP BY	M_AttributeSetInstance_ID
	) att ON TRUE
WHERE
	1=1
	AND report.IsActive='Y' and reportLine.IsActive='Y'
	AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
	AND o.IsSOTrx != 'N'
	AND o.DocStatus = 'CO'
	AND report.C_Order_MFGWarehouse_Report_ID =  $1
	
ORDER BY ol.line

$$
LANGUAGE sql STABLE;

-- Function: Docs_Sales_OrderCheckup_Root
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root(numeric);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root(numeric, numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root( IN p_record_id numeric, IN bPartnerId numeric, IN datePromised date );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root(IN p_record_id  numeric,
                                                                                           IN bPartnerId   numeric,
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
  ELSE false -- shall never nappen
  END
LIMIT 1

$$
LANGUAGE sql
STABLE;

