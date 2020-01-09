DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.getShipmentAddressorNull(numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.getCustomsInvoiceShipmentAddressorNull(numeric);

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




DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Customs_Invoice_Description ( IN C_Invoice_ID numeric, IN AD_Language Character Varying(6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Customs_Invoice_Description(
  IN C_Invoice_ID numeric, IN AD_Language Character Varying(6))
  RETURNS TABLE
  (documentno            character varying(30),
   dateinvoiced          timestamp without time zone,
   VATaxID               character varying(60),
   C_BPartner_ID         numeric,
   bp_value              character varying(40),
   cont_name             text,
   cont_phone            character varying(40),
   cont_fax              character varying(40),
   cont_email            character varying(60),
   PrintName             character varying(60),
   inout_bpartneraddress varchar,
   inout_C_BPartner_ID   numeric
  )
AS
$$
SELECT
  i.documentno                          as documentno,
  i.dateinvoiced                        as dateinvoiced,
  bp.VATaxID,
  i.C_BPartner_ID,
  bp.value                              as bp_value,
  Coalesce(cogr.name, '') ||
  Coalesce(' ' || cont.title, '') ||
  Coalesce(' ' || cont.firstName, '') ||
  Coalesce(' ' || cont.lastName, '')    as cont_name,
  cont.phone                            as cont_phone,
  cont.fax                              as cont_fax,
  cont.email                            as cont_email,
  COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
  io.inout_bpartneraddress,
  io.inout_C_BPartner_ID
FROM
  C_Customs_Invoice i
  JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
  LEFT JOIN AD_User cont ON i.AD_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
  LEFT JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
  LEFT OUTER JOIN C_DocType dt ON i.c_doctype_id = dt.C_DocType_ID AND dt.isActive = 'Y'
  LEFT OUTER JOIN C_DocType_Trl dtt ON i.c_doctype_id = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
  LEFT JOIN C_Customs_Invoice_Line il on il.C_Customs_Invoice_id = i.C_Customs_Invoice_ID
  LEFT JOIN de_metas_endcustomer_fresh_reports.getCustomsInvoiceShipmentAddressorNull($1) as io on 1=1
WHERE
  i.C_Customs_Invoice_id = $1
$$
LANGUAGE sql
STABLE;

