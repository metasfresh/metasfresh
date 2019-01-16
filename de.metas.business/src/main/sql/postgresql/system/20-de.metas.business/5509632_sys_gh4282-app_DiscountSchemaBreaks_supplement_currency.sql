
-- supplement our accounting schema's currency in case it is missing
UPDATE M_DiscountSchemaBreak SET Updated=now(), UpdatedBy=99, C_Currency_ID=(select C_Currency_ID from C_AcctSchema where C_AcctSchema_ID=1000000)
WHERE true 
  AND IsActive='Y' AND IsValid='Y'
  AND C_Currency_ID IS NULL
  AND (
    /*fixed price*/PriceBase='F' 
    OR 
    /*pricing-system with surcharge*/ PriceBase='P' AND COALESCE(PricingSystemSurchargeAmt,0) !=0
  );
