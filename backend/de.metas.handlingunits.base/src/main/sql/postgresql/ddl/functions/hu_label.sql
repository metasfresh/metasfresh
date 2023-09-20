DROP FUNCTION IF EXISTS "de.metas.handlingunits".hu_label(
    p_M_HU_ID numeric
)
;


CREATE OR REPLACE FUNCTION "de.metas.handlingunits".hu_label(
    p_M_HU_ID numeric
)
    RETURNS TABLE
            (
                prod_name         character varying,
                prod_value        character varying,
                prod_desc         text,
                displayableqrcode text,
                renderedqrcode    text,
                vendorName        text,
                PurchaseOrderNo   text,
                receiptdate       timestamp WITHOUT TIME ZONE,
                proddate          date
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT DISTINCT p.name,
                p.value            AS prod_value,
                p.description      AS prod_desc,
                qr.displayableqrcode,
                qr.renderedqrcode,
                ri.vendorName,
                ri.PurchaseOrderNo,
                ri.receiptdate,
                tu.created :: date AS proddate
FROM M_HU tu

         /** Get Product */
         INNER JOIN M_HU_Storage tus ON tu.M_HU_ID = tus.M_HU_ID
         INNER JOIN M_Product p ON tus.M_Product_ID = p.M_Product_ID
    -- QR Code
         INNER JOIN M_HU_QRCode qr ON tu.m_hu_id = qr.m_hu_id

    /** receipt infos */
         LEFT OUTER JOIN (
    SELECT DISTINCT ON (tu.M_HU_ID) tu.M_HU_ID,
                                    bp.name         AS vendorName,
                                    o.DocumentNo    AS PurchaseOrderNo,
                                    io.movementdate AS receiptdate
    FROM M_HU tu
             LEFT OUTER JOIN M_HU_Item lui ON lui.M_HU_Item_ID = tu.M_HU_Item_Parent_ID AND lui.isActive = 'Y'
             LEFT OUTER JOIN M_HU lu ON lu.M_HU_ID = lui.M_HU_ID
             LEFT OUTER JOIN M_HU_PI_Version piv
                             ON piv.M_HU_PI_Version_ID = lu.M_HU_PI_Version_ID
             LEFT OUTER JOIN M_HU thu ON thu.M_HU_ID = COALESCE(lu.M_HU_ID, tu.M_HU_ID)
             LEFT OUTER JOIN M_HU_Assignment hf_a ON thu.M_HU_ID = hf_a.M_HU_ID
             LEFT JOIN M_ReceiptSchedule rs ON hf_a.Record_ID = rs.M_ReceiptSchedule_ID
        AND hf_a.ad_table_id = get_table_id('M_ReceiptSchedule') AND rs.isActive = 'Y'
             LEFT JOIN m_inoutLine iol ON hf_a.Record_ID = iol.m_inoutLine_ID
        AND hf_a.ad_table_id = get_table_id('m_inoutLine') AND iol.isActive = 'Y'
             LEFT JOIN m_inout io ON io.m_inout_id = iol.m_inout_id
             LEFT JOIN C_BPartner bp ON COALESCE(rs.C_BPartner_ID, io.c_bpartner_id) = bp.C_BPartner_ID
             LEFT JOIN C_Order o ON COALESCE(rs.C_Order_ID, io.c_order_id) = o.C_Order_ID
    ORDER BY tu.M_HU_ID, bp.name NULLS LAST, o.DocumentNo NULLS LAST
) ri ON ri.M_HU_ID = tu.M_HU_ID

WHERE tu.M_HU_ID = p_M_HU_ID;
$$
;