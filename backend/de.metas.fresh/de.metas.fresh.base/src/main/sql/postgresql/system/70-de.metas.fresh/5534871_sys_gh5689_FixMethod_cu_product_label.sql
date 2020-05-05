DROP FUNCTION IF EXISTS report.cu_product_label(IN M_HU_ID numeric);

CREATE FUNCTION report.cu_product_label(IN M_HU_ID numeric) RETURNS TABLE
	(
	created date, 
	value Character Varying, 
	serialno text,
	name Character Varying (255),
	prod_value Character Varying,
	vendorName Character Varying (255),
    PurchaseOrderNo Character Varying
	)
AS 
$$
SELECT
  tu.created :: date,
  val.huvalue,
  tua_ser.value as serialno,
  p.name,
  p.value       as prod_value,
  ri.vendorName,
  ri.PurchaseOrderNo
FROM
  M_HU tu
  /** Get Product */
  INNER JOIN M_HU_Storage tus ON tu.M_HU_ID = tus.M_HU_ID AND tus.isActive = 'Y'
  INNER JOIN M_Product p ON tus.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
  /** Get serialno Attibute */
  LEFT OUTER JOIN M_HU_Attribute tua_ser ON tu.M_HU_ID = tua_ser.M_HU_ID
                                            AND tua_ser.M_Attribute_ID = ((SELECT M_Attribute_ID
                                                                           FROM M_Attribute
                                                                           WHERE value = 'SerialNo' AND isActive = 'Y'))
                                            AND tua_ser.isActive = 'Y'
  -- SerialNo
  /** receipt infos */
  LEFT OUTER JOIN (
                    select DISTINCT ON (tu.M_HU_ID)
                      tu.M_HU_ID,
                      bp.name      as vendorName,
                      o.DocumentNo as PurchaseOrderNo
                    from M_HU tu
                      LEFT OUTER JOIN M_HU_Item lui ON lui.M_HU_Item_ID = tu.M_HU_Item_Parent_ID AND lui.isActive = 'Y'
                      LEFT OUTER JOIN M_HU lu ON lu.M_HU_ID = lui.M_HU_ID
                      LEFT OUTER JOIN M_HU_PI_Version piv
                        ON piv.M_HU_PI_Version_ID = lu.M_HU_PI_Version_ID AND piv.isActive = 'Y'
                      LEFT OUTER JOIN M_HU thu ON thu.M_HU_ID = COALESCE(lu.M_HU_ID, tu.M_HU_ID)
                      LEFT OUTER JOIN M_HU_Assignment hf_a ON thu.M_HU_ID = hf_a.M_HU_ID
                      LEFT JOIN M_ReceiptSchedule rs ON hf_a.Record_ID = rs.M_ReceiptSchedule_ID AND rs.isActive = 'Y'
                      LEFT JOIN C_BPartner bp on rs.C_BPartner_ID = bp.C_BPartner_ID
                      LEFT JOIN C_Order o on rs.C_Order_ID = o.C_Order_ID
                    order by tu.M_HU_ID, bp.name nulls last, o.DocumentNo nulls last
                  ) ri ON ri.M_HU_ID = tu.M_HU_ID

  --get vallues for aggregated HUs if any
  left outer join "de.metas.handlingunits".get_TU_Values_From_Aggregation(tu.M_HU_ID) val on true
WHERE
	tu.M_HU_ID = $1

;

$$
  LANGUAGE sql STABLE;