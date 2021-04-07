DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.IntraTradeShipments(numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.IntraTradeShipments(IN p_C_Period_ID numeric)

    RETURNS TABLE
            (
                "Pos"                 varchar,
                "Produkt"             text,
                "Warennumer"          varchar,
                "Land geliefert von"  char(2),
                "Land geliefert nach" char(2),
                "Ursprungsland"       char(2),
                "Menge"               numeric,
                "Einheit"             varchar,
                "RG. Betrag"          numeric,
                "Währung"             varchar,
                "Monat"               varchar
            )
AS
$$
select (pos::varchar) as position,
       productName ||  E'\n' || productDescription as product,
       commoditynumber,
       deliveredFromCountry,
       deliveryCountry,
       OriginCountry,
       movementqty,
       UOMSymbol,
       grandtotal,
       cursymbol,
       Period
from (
         select row_number()
                over (order by deliveryCountry, commoditynumber, deliveredFromCountry, OriginCountry, UOMSymbol, cursymbol, C_Period_ID) as Pos,
                *
         from de_metas_endcustomer_fresh_reports.M_InOut_V i
         where C_Period_ID = p_C_Period_ID
     ) as v
order by pos;
$$
    LANGUAGE sql
    STABLE;
