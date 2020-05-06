update AD_Column set IsRangeFilter='Y' where IsSelectionColumn='Y' and AD_Reference_ID in (
15 -- Date
, 16 -- DateTime
, 24 -- Time
, 11 -- Integer
, 22 -- Number
, 12 -- Amount
, 29 -- Quantity
, 27 -- CostPrice
);


