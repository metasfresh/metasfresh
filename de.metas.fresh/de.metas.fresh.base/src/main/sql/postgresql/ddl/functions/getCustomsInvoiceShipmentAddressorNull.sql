--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.getCustomsInvoiceShipmentAddressorNull(numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.getCustomsInvoiceShipmentAddressorNull(IN p_C_Customs_Invoice_Id numeric)

    RETURNS TABLE
            (
                inout_C_BPartner_ID   numeric,
                inout_bpartneraddress text
            )
AS
$$
DECLARE

    rec_customs_invoice RECORD;
    v_rows_no           NUMERIC := 0;
    v_bpartner_id       NUMERIC;
    v_address           text;
BEGIN

    SELECT Count(distinct io.M_InOut_ID)::NUMERIC
    INTO v_rows_no
    FROM C_Customs_Invoice_Line cil
             JOIN M_InOutLine_To_C_Customs_Invoice_Line alloc
                  on cil.C_Customs_Invoice_Line_ID = alloc.C_Customs_Invoice_Line_ID
             JOIN M_InOutLine iol ON iol.M_InOutLine_id = alloc.M_InOutLine_id
             JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID
    WHERE cil.C_Customs_Invoice_Id = p_C_Customs_Invoice_Id
    GROUP BY cil.C_Customs_Invoice_Id;

    IF (v_rows_no > 1 OR v_rows_no = 0) THEN
        RETURN QUERY select null::NUMERIC, null::text;
    ELSE

        RETURN QUERY SELECT io.c_bpartner_id::NUMERIC, io.bpartneraddress::text
                     FROM C_Customs_Invoice_Line cil
                              JOIN M_InOutLine_To_C_Customs_Invoice_Line alloc
                                   on cil.C_Customs_Invoice_Line_ID = alloc.C_Customs_Invoice_Line_ID
                              JOIN M_InOutLine iol ON iol.M_InOutLine_id = alloc.M_InOutLine_id
                              JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID
                     WHERE cil.C_Customs_Invoice_Id = p_C_Customs_Invoice_Id;
    END IF;

END;
$$
    LANGUAGE 'plpgsql';

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.getCustomsInvoiceShipmentAddressorNull(numeric) IS 'Returns the inout address if is there only one inout linked to customs invoice';