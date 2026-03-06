SELECT db_drop_functions('*.PriceAttributes_Matching_Score')
;

-- Function to calculate how well a given ASI matches the requested attributes
-- Returns a score between 0 and 1:
--   0 = not matching at all (must-match attribute failed)
--   1 = fully matches (all ASI attributes have matching values in request)
--   0 < score < 1 = partial match (some attributes missing/null/mismatched in request)
CREATE OR REPLACE FUNCTION PriceAttributes_Matching_Score(
    p_Price_AttributeSetInstance_ID numeric,
    p_attributes                    jsonb,
    p_MustMatch_AttributeCode       text DEFAULT NULL
)
    RETURNS numeric
AS
$$
DECLARE
    v_priceAttributeRecord         record;
    v_actualAttributeValue         text;
    v_priceAttributeValue          text;
    v_countAttributes              int     := 0;
    v_attributeScore               numeric := 0;
    v_attributeScoreSum            numeric := 0;
    v_finalScore                   numeric := 0;
    v_MustMatchAttributeWasMatched boolean := FALSE;
BEGIN
    -- If no ASI provided, return 0
    IF p_Price_AttributeSetInstance_ID IS NULL OR p_Price_AttributeSetInstance_ID <= 0 THEN
        RETURN 0;
    END IF;

    -- Loop through each attribute in the price ASI
    FOR v_priceAttributeRecord IN (SELECT a.Value AS attributeCode,
                                          a.AttributeValueType,
                                          CASE
                                              WHEN a.AttributeValueType IN ('S', 'L') THEN ai.Value::text
                                              WHEN a.AttributeValueType = 'N'         THEN ai.ValueNumber::text
                                              WHEN a.AttributeValueType = 'D'         THEN ai.ValueDate::text
                                                                                      ELSE ai.Value::text
                                          END     AS attributeValue
                                   FROM M_AttributeInstance ai
                                            INNER JOIN M_Attribute a ON a.M_Attribute_ID = ai.M_Attribute_ID
                                   WHERE ai.M_AttributeSetInstance_ID = p_Price_AttributeSetInstance_ID
                                     AND a.IsActive = 'Y')
        LOOP
            v_priceAttributeValue := NULLIF(TRIM(v_priceAttributeRecord.attributeValue), '');
            v_actualAttributeValue := NULLIF(TRIM(p_attributes -> v_priceAttributeRecord.attributeCode ->> 'value'), '');


            -- Compute attribute matching score
            v_attributeScore := 0;
            IF v_priceAttributeValue = v_actualAttributeValue THEN
                v_attributeScore := 1;
            ELSIF v_actualAttributeValue IS NULL THEN
                v_attributeScore := 0.5;
            ELSE
                v_attributeScore := 0;
            END IF;

            -- Check if this is a must-match attribute
            IF p_MustMatch_AttributeCode IS NOT NULL
                AND v_priceAttributeRecord.attributeCode = p_MustMatch_AttributeCode THEN

                IF (v_attributeScore >= 1) THEN
                    v_MustMatchAttributeWasMatched = TRUE;
                ELSE
                    RETURN 0;
                END IF;
            END IF;

            -- Accumulate score
            v_attributeScoreSum := v_attributeScoreSum + v_attributeScore;
            v_countAttributes := v_countAttributes + 1;
        END LOOP;

    -- If no attributes found in ASI, return 0
    IF v_countAttributes = 0 THEN
        RETURN 0;
    END IF;

    -- If the "must match attribute" was not found in price ASI, return 0
    IF p_MustMatch_AttributeCode IS NOT NULL AND NOT v_MustMatchAttributeWasMatched THEN
        RETURN 0;
    END IF;

    -- Calculate final score:
    v_finalScore := v_attributeScoreSum / v_countAttributes;

    RETURN v_finalScore;
END;
$$
    LANGUAGE plpgsql STABLE
;

