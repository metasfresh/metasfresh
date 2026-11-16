CREATE OR REPLACE FUNCTION get_bpartner_from_pp_order_candidate(
    p_PP_Order_Candidate_ID numeric
)
RETURNS numeric AS
$$
DECLARE
    v_C_BPartner_ID numeric;
    v_C_OrderLine_ID numeric;
    v_M_ShipmentSchedule_ID numeric;
BEGIN
    -- Get the OrderLine_ID and ShipmentSchedule_ID from PP_Order_Candidate
    SELECT poc.C_OrderLine_ID, poc.M_ShipmentSchedule_ID
    INTO v_C_OrderLine_ID, v_M_ShipmentSchedule_ID
    FROM PP_Order_Candidate poc
    WHERE poc.PP_Order_Candidate_ID = p_PP_Order_Candidate_ID;

    -- Try to get BPartner from OrderLine
    IF v_C_OrderLine_ID IS NOT NULL AND v_C_OrderLine_ID > 0 THEN
        SELECT o.C_BPartner_ID
        INTO v_C_BPartner_ID
        FROM C_OrderLine ol
        INNER JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
        WHERE ol.C_OrderLine_ID = v_C_OrderLine_ID;

        IF v_C_BPartner_ID IS NOT NULL THEN
            RETURN v_C_BPartner_ID;
        END IF;
    END IF;

    -- Try to get BPartner from ShipmentSchedule
    IF v_M_ShipmentSchedule_ID IS NOT NULL AND v_M_ShipmentSchedule_ID > 0 THEN
        SELECT ss.C_BPartner_ID
        INTO v_C_BPartner_ID
        FROM M_ShipmentSchedule ss
        WHERE ss.M_ShipmentSchedule_ID = v_M_ShipmentSchedule_ID;

        IF v_C_BPartner_ID IS NOT NULL THEN
            RETURN v_C_BPartner_ID;
        END IF;
    END IF;

    -- Return NULL if no BPartner found
    RETURN NULL;
END;
$$
LANGUAGE plpgsql STABLE;