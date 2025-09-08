drop function if exists getPriceListVersionsUpToBase
(
    /* p_Start_PriceList_Version_ID */ numeric
);
drop function if exists getPriceListVersionsUpToBase
(
    /* p_M_PriceList_ID */ numeric,
    /* p_Date */ timestamp with time zone
);

drop function if exists getpricelistversionsuptobase_ForPricelistVersion
(
    /* p_start_pricelist_version_id */  numeric,
    /* p_datePromised */  timestamp with time zone
);

create or replace function getpricelistversionsuptobase_ForPricelistVersion
(
    p_start_pricelist_version_id numeric,
    p_datePromised timestamp with time zone
)
    returns numeric[]
as
$BODY$
with recursive priceListVersion as (
    select
        1 as SeqNo,
        plv1.M_PriceList_Version_ID,
        basePLV1.M_PriceList_Version_ID as basePLVID,
        plv1.M_Pricelist_ID,
        plv1.validFrom,
        ARRAY[plv1.M_PriceList_Version_ID]::numeric(10,0)[] as path -- used to prevent running forever in case of cycles
    from M_PriceList_Version plv1
             inner join M_Pricelist pl1 on plv1.M_Pricelist_ID = pl1.M_Pricelist_ID
             left join M_Pricelist basePL1 on pl1.basePricelist_ID = basePL1.M_PriceList_ID
             left join M_Pricelist_Version basePLV1 on basePLV1.M_Pricelist_ID = basePL1.M_Pricelist_ID
        and basePLV1.validFrom = (select max(ValidFrom)
                                  from M_Pricelist_Version
                                  where isActive = 'Y'
                                    and M_Pricelist_ID = basePL1.M_Pricelist_ID
                                    and validFrom <= p_datePromised)
    where
        plv1. M_PriceList_Version_ID=p_Start_PriceList_Version_ID
      and plv1.IsActive='Y'
    --
    union all
    --
    select
        plv2.SeqNo + 1 as SeqNo,
        plv2.M_PriceList_Version_ID,
        basePLV2.M_PriceList_Version_ID as basePLVID,
        plv2.M_Pricelist_ID,
        plv2.validFrom,
        (plv2.path || basePLV2.M_PriceList_Version_ID)::numeric(10,0)[] as path -- add new base-plv array
from priceListVersion as plv2
    inner join M_Pricelist pl2 on plv2.M_Pricelist_ID = pl2.M_Pricelist_ID
    left join M_Pricelist basePL2 on pl2.basePricelist_ID = basePL2.M_PriceList_ID
    left join M_Pricelist_Version basePLV2 on basePLV2.M_Pricelist_ID = basePL2.M_Pricelist_ID
    and basePLV2.validFrom = (select max(ValidFrom)
    from M_Pricelist_Version
    where isActive = 'Y'
    and M_Pricelist_ID = basePL2.M_Pricelist_ID
    and validFrom <= p_datePromised)
where
    basePLV2.IsActive='Y'
  and NOT plv2.path @> ARRAY[basePLV2.M_PriceList_Version_ID] -- stop recursing if we already saw the current base-plv's M_PriceList_Version_ID
    )
select array_agg(M_PriceList_Version_ID order by SeqNo) from priceListVersion;
$BODY$
    STABLE
    LANGUAGE SQL 
;
COMMENT ON FUNCTION public.getpricelistversionsuptobase_ForPricelistVersion(numeric, timestamp with time zone)
    IS 'Gets an array starting with your given price list version and the pricing date and then recursively all base price list versions to fallback for pricing. Robost against loops.';


create or replace function getPriceListVersionsUpToBase
(
    p_M_PriceList_ID numeric,
    p_Date timestamp with time zone
)
    returns numeric[]
as
$BODY$
SELECT getpricelistversionsuptobase_ForPricelistVersion(
               (
                   SELECT M_PriceList_Version.M_PriceList_Version_ID
                   from M_PriceList_Version
                   where M_PriceList_Version.M_PriceList_ID = p_M_PriceList_ID
                     AND M_PriceList_Version.IsActive = 'Y'
                     AND M_PriceList_Version.ValidFrom =
                         (
                             SELECT MAX(M_PriceList_Version.ValidFrom)
                             from M_PriceList_Version
                             where M_PriceList_Version.M_PriceList_ID = p_M_PriceList_ID
                               AND M_PriceList_Version.ValidFrom <=  p_Date
                             GROUP BY M_PriceList_ID
                         )
               ), p_date
       )
$BODY$
    STABLE
    LANGUAGE SQL
;
