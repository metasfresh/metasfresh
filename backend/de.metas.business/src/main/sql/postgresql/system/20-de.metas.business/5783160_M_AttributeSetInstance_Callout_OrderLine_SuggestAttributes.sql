SELECT db_drop_functions('*.M_AttributeSetInstance_Callout_OrderLine_SuggestAttributes')
;

CREATE OR REPLACE FUNCTION M_AttributeSetInstance_Callout_OrderLine_SuggestAttributes(p_request jsonb)
    RETURNS jsonb
AS
$$
DECLARE
    v_contextTableName          text;
    v_C_OrderLine_ID            numeric;
    v_changedAttributeCode      text;
    v_M_PriceList_Version_ID    numeric;
    v_M_Product_ID              numeric;
    v_M_AttributeSetInstance_ID numeric;
    v_result                    jsonb := '{}'::jsonb;
    v_attribute_record          record;
BEGIN
    RAISE INFO 'M_AttributeSetInstance_Callout_test: %', p_request;

    v_contextTableName := (p_request ->> 'contextTableName')::text;
    IF (LOWER(v_contextTableName) != LOWER('C_OrderLine')) THEN
        RAISE NOTICE 'This trigger is only or C_OrderLine';
        RETURN NULL;
    END IF;

    -- Extract context information
    v_C_OrderLine_ID := (p_request ->> 'contextRecordId')::numeric;
    v_changedAttributeCode := p_request ->> 'attributeCode';

    -- Get M_PriceList_Version_ID and M_Product_ID from C_OrderLine
    SELECT ol.M_PriceList_Version_ID, ol.M_Product_ID
    INTO v_M_PriceList_Version_ID, v_M_Product_ID
    FROM C_OrderLine ol
    WHERE ol.C_OrderLine_ID = v_C_OrderLine_ID;

    IF v_M_PriceList_Version_ID IS NULL OR v_M_Product_ID IS NULL THEN
        RAISE NOTICE 'Could not find order line or missing price list version/product for C_OrderLine_ID=%', v_C_OrderLine_ID;
        RETURN NULL;
    END IF;

    -- Find the best matching M_ProductPrice based on current attributes
    -- Strategy: Try to find a product price with an ASI that matches the current attributes
    -- Find the best matching M_ProductPrice based on current attributes
    -- Strategy: Calculate score once and reuse it for filtering and ordering
    WITH scored_prices AS (SELECT pp.M_AttributeSetInstance_ID,
                                  pp.MatchSeqNo,
                                  pp.SeqNo,
                                  pp.M_ProductPrice_ID,
                                  PriceAttributes_Matching_Score(
                                          p_Price_AttributeSetInstance_ID=>pp.M_AttributeSetInstance_ID,
                                          p_attributes=>p_request -> 'attributes',
                                          p_MustMatch_AttributeCode=>v_changedAttributeCode) AS matchScore
                           FROM M_ProductPrice pp
                           WHERE pp.M_PriceList_Version_ID = v_M_PriceList_Version_ID
                             AND pp.M_Product_ID = v_M_Product_ID
                             AND pp.IsActive = 'Y'
                             AND pp.isAttributeDependant = 'Y'
                             AND pp.M_AttributeSetInstance_ID IS NOT NULL)
    SELECT M_AttributeSetInstance_ID
    INTO v_M_AttributeSetInstance_ID
    FROM scored_prices
    WHERE matchScore > 0
    ORDER BY matchScore DESC, MatchSeqNo, SeqNo, M_ProductPrice_ID
    LIMIT 1;
    IF v_M_AttributeSetInstance_ID IS NULL OR v_M_AttributeSetInstance_ID <= 0 THEN
        RAISE NOTICE 'No matching M_ProductPrice found for M_PriceList_Version_ID=%, M_Product_ID=%', v_M_PriceList_Version_ID, v_M_Product_ID;
        RETURN v_result;
    END IF;

    -- Extract attribute values from the found ASI
    -- Exclude the attribute that is currently being edited
    FOR v_attribute_record IN
        SELECT a.Value AS attributeCode,
               CASE
                   WHEN a.AttributeValueType = 'S' THEN TO_JSONB(ai.Value)
                   WHEN a.AttributeValueType = 'L' THEN TO_JSONB(ai.Value)
                   WHEN a.AttributeValueType = 'N' THEN TO_JSONB(ai.ValueNumber)
                   WHEN a.AttributeValueType = 'D' THEN TO_JSONB(ai.ValueDate)
                                                   ELSE TO_JSONB(ai.Value)
               END     AS attributeValue
        FROM M_AttributeInstance ai
                 INNER JOIN M_Attribute a ON a.M_Attribute_ID = ai.M_Attribute_ID
        WHERE ai.M_AttributeSetInstance_ID = v_M_AttributeSetInstance_ID
          AND a.IsActive = 'Y'
          AND a.Value != v_changedAttributeCode -- Exclude the currently edited attribute
        LOOP
            v_result := v_result || JSONB_BUILD_OBJECT(v_attribute_record.attributeCode, v_attribute_record.attributeValue);
        END LOOP;

    RETURN v_result;
END;
$$
    LANGUAGE plpgsql
;
