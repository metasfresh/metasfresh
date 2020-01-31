
create or replace function getPriceListVersionsUpToBase
(
	p_Start_PriceList_Version_ID numeric
)
returns numeric[]
as
$BODY$
    with recursive priceListVersion as (
        select
            1 as SeqNo,
            M_PriceList_Version_ID,
            FallbackToBasePriceListPrices,
            M_PriceList_Version_Base_ID,
            ARRAY[M_PriceList_Version_ID]::numeric(10,0)[] as path -- used to prevent running forever in case of cycles
        from M_PriceList_Version
        where
            M_PriceList_Version_ID=p_Start_PriceList_Version_ID
            and IsActive='Y'
        --
        union all
        --
        select
            plv.SeqNo + 1 as SeqNo,
            base_plv.M_PriceList_Version_ID,
            base_plv.FallbackToBasePriceListPrices,
            base_plv.M_PriceList_Version_Base_ID,
            (plv.path || base_plv.M_PriceList_Version_ID)::numeric(10,0)[] as path -- add new base-plv array
        from priceListVersion as plv
        inner join M_PriceList_Version base_plv on (base_plv.M_PriceList_Version_ID=plv.M_PriceList_Version_Base_ID)
        where
            plv.FallbackToBasePriceListPrices='Y'
            and base_plv.IsActive='Y'
            and NOT plv.path @> ARRAY[base_plv.M_PriceList_Version_ID] -- stop recursing if we already saw the current base-plv's M_PriceList_Version_ID
    )
    select array_agg(M_PriceList_Version_ID order by SeqNo) from priceListVersion;
$BODY$
LANGUAGE SQL
;
COMMENT ON FUNCTION getPriceListVersionsUpToBase(numeric) 
IS 'Gets an array starting with your given price list version and then recursively all base price list versions to fallback for pricing. Robost against loops.';

