









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


-- Function to check if M_HU_PI_Item is TU level
CREATE OR REPLACE FUNCTION is_tu_level_packing(p_M_HU_PI_Item_ID numeric)
RETURNS boolean AS
$$
BEGIN
    RETURN EXISTS(
        SELECT 1
        FROM M_HU_PI_Item i, M_HU_PI_Version v
        WHERE i.M_HU_PI_Version_ID = v.M_HU_PI_Version_ID
          AND i.M_HU_PI_Item_ID = p_M_HU_PI_Item_ID
          AND v.HU_UnitType = 'TU'
    );
END;
$$
LANGUAGE plpgsql STABLE;





-- Name: M_HU_PI_Item_Product for PP_Order_Candidate
-- 2026-03-24T11:21:14.904Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540775,TO_TIMESTAMP('2026-03-24 11:21:14.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','M_HU_PI_Item_Product for PP_Order_Candidate','S',TO_TIMESTAMP('2026-03-24 11:21:14.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_HU_PI_Item_Product for PP_Order_Candidate
-- 2026-03-24T11:28:24.656Z
UPDATE AD_Val_Rule SET Code='M_HU_PI_Item_Product.AD_Org_ID IN (0, @AD_Org_ID@)

-- Must be a TU-level packing instruction
AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN (
    SELECT i.M_HU_PI_Item_ID
    FROM M_HU_PI_Item i
    WHERE i.M_HU_PI_Version_ID IN (
        SELECT v.M_HU_PI_Version_ID
        FROM M_HU_PI_Version v
        WHERE v.HU_UnitType = ''TU''
    )
)

-- Product must match (or packing instruction allows any product)
AND (
    M_HU_PI_Item_Product.M_Product_ID = @M_Product_ID@
    OR (
        M_HU_PI_Item_Product.IsAllowAnyProduct = ''Y''
        AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID NOT IN (100)
    )
)

-- Business partner filter: derive BPartner from linked OrderLine or ShipmentSchedule
AND (
    -- Case 1: a BPartner can be found via the linked order line or shipment schedule
    -- => allow packing instructions matching that BPartner, or generic ones (NULL)
    (
        EXISTS (
            SELECT 1
            FROM C_OrderLine ol
            INNER JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
            WHERE ol.C_OrderLine_ID = @C_OrderLine_ID@
              AND o.C_BPartner_ID = M_HU_PI_Item_Product.C_BPartner_ID
        )
        OR EXISTS (
            SELECT 1
            FROM M_ShipmentSchedule ss
            WHERE ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
              AND ss.C_BPartner_ID = M_HU_PI_Item_Product.C_BPartner_ID
        )
        OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL
    )
    -- Case 2: no linked order line or shipment schedule => only allow NULL BPartner
    OR (
        (@C_OrderLine_ID@ IS NULL OR @C_OrderLine_ID@ <= 0)
        AND (@M_ShipmentSchedule_ID@ IS NULL OR @M_ShipmentSchedule_ID@ <= 0)
        AND M_HU_PI_Item_Product.C_BPartner_ID IS NULL
    )
)

-- Date validity
AND M_HU_PI_Item_Product.ValidFrom <= @DatePromised@
AND (
    M_HU_PI_Item_Product.ValidTo IS NULL
    OR M_HU_PI_Item_Product.ValidTo >= @DatePromised@
)',Updated=TO_TIMESTAMP('2026-03-24 11:28:24.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540775
;

-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2026-03-24T11:34:51.991Z
UPDATE AD_Column SET AD_Val_Rule_ID=540775,Updated=TO_TIMESTAMP('2026-03-24 11:34:51.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=583382
;



-- Field: Produktionsdisposition(541894,EE01) -> Produktionsdisposition(548132,EE01) -> Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2026-03-24T12:09:43.232Z
UPDATE AD_Field SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2026-03-24 12:09:43.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=746288
;







-- Field: Produktionsdisposition(541894,EE01) -> Produktionsdisposition(548132,EE01) -> Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2026-03-24T12:44:41.793Z
UPDATE AD_Field SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2026-03-24 12:44:41.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=746288
;

-- Field: Produktionsdisposition(541894,EE01) -> Produktionsdisposition(548132,EE01) -> Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2026-03-24T12:45:10.183Z
UPDATE AD_Field SET AD_Val_Rule_ID=540775,Updated=TO_TIMESTAMP('2026-03-24 12:45:10.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=746288
;

-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2026-03-24T12:46:03.538Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2026-03-24 12:46:03.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=583382
;

-- Field: Produktionsdisposition(541894,EE01) -> Produktionsdisposition(548132,EE01) -> Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2026-03-24T12:47:41.271Z
UPDATE AD_Field SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2026-03-24 12:47:41.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=746288
;

-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2026-03-24T12:47:52.996Z
UPDATE AD_Column SET AD_Val_Rule_ID=540775,Updated=TO_TIMESTAMP('2026-03-24 12:47:52.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=583382
;



-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2026-03-24T12:52:39.876Z
UPDATE AD_Column SET AD_Reference_Value_ID=541948, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-03-24 12:52:39.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=583382
;

-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2026-03-24T14:17:01.405Z
UPDATE AD_Column SET AD_Reference_Value_ID=NULL, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2026-03-24 14:17:01.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=583382
;



-- Name: M_HU_PI_Item_Product for PP_Order_Candidate
-- 2026-03-24T12:58:07.515Z



-- Name: M_HU_PI_Item_Product for PP_Order_Candidate
-- 2026-03-24T13:04:30.965Z
UPDATE AD_Val_Rule SET Code='M_HU_PI_Item_Product.AD_Org_ID IN (0, @AD_Org_ID@)
AND is_tu_level_packing(M_HU_PI_Item_Product.M_HU_PI_Item_ID) = true
AND (
    M_HU_PI_Item_Product.M_Product_ID = @M_Product_ID@
    OR (
        M_HU_PI_Item_Product.IsAllowAnyProduct = ''Y''
        AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID NOT IN (100)
    )
)
AND (
    get_bpartner_from_pp_order_candidate(@PP_Order_Candidate_ID@) = M_HU_PI_Item_Product.C_BPartner_ID
    OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL
)
AND M_HU_PI_Item_Product.ValidFrom <= ''@DatePromised@''
AND (
    M_HU_PI_Item_Product.ValidTo IS NULL
    OR M_HU_PI_Item_Product.ValidTo >= ''@DatePromised@''
)',Updated=TO_TIMESTAMP('2026-03-24 13:04:30.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540775
;

