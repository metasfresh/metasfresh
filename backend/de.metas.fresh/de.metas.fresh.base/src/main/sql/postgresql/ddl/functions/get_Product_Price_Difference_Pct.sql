DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_Product_Price_Difference_Pct(numeric,
                                                                                            numeric,
                                                                                            date)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_Product_Price_Difference_Pct(p_productId   numeric,
                                                                                               p_priceListId numeric,
                                                                                               p_onDate      date)
    RETURNS numeric
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    priceStd        numeric;
    currentCost     numeric;
    priceUOMId      numeric;
    costUOMId       numeric;
    priceCurrencyID numeric;
    costCurrencyID  numeric;
BEGIN
    SELECT * FROM de_metas_endcustomer_fresh_reports.get_Product_Price(p_productId, p_priceListId, p_onDate) INTO priceStd, priceUOMId, priceCurrencyID;
    SELECT currentcostprice, c_uom_id, c_currency_id
    FROM m_cost
    WHERE m_product_id = p_productId
      AND m_costelement_id = 1000002 --Bestellpreis Durchschnitt
    INTO currentCost, costUOMId, costCurrencyID;

    IF (priceUOMId IS NULL) OR (costUOMId IS NULL) THEN
        RETURN 0;
        -- is there a better way to signal that a price could not be found?
    END IF;
    IF priceUOMId != costUOMId THEN
        currentCost = priceuomconvert(p_productId, currentCost, costUOMId, priceUOMId, 8);
    END IF;
    IF (priceCurrencyID IS NULL) OR (costCurrencyID IS NULL) THEN
        RETURN 0;
    END IF;
    IF priceCurrencyID != costCurrencyID THEN
        currentCost := currencyconvert(currentCost, costCurrencyID, priceCurrencyID, p_onDate);
    END IF;
    IF (currentCost IS NULL) OR (currentcost = 0) THEN RETURN 0; END IF;
    RETURN (priceStd - currentCost) * 100 / currentCost;
END;
$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.get_Product_Price_Difference_Pct(numeric, numeric, date) IS 'Returns the difference between the current purchase price of a product and the average purchase price as a percentage of the current purchase price'
;
