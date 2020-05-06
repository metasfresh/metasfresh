drop function if exists getPriceListVersionsUpToBase
(
	/* p_Start_PriceList_Version_ID */ numeric
);
drop function if exists getPriceListVersionsUpToBase
(
	/* p_M_PriceList_ID */ numeric,
    /* p_Date */ timestamp with time zone
);


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
            M_PriceList_Version_Base_ID
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
            base_plv.M_PriceList_Version_Base_ID
        from priceListVersion as plv
        inner join M_PriceList_Version base_plv on (base_plv.M_PriceList_Version_ID=plv.M_PriceList_Version_Base_ID)
        where
            plv.FallbackToBasePriceListPrices='Y'
            and base_plv.IsActive='Y'
    )
    select array_agg(M_PriceList_Version_ID order by SeqNo) from priceListVersion;
$BODY$
LANGUAGE SQL
;
COMMENT ON FUNCTION getPriceListVersionsUpToBase(numeric) 
IS 'Gets an array starting with your given price list version and then recursivelly all base price list versions to fallback for pricing';








create or replace function getPriceListVersionsUpToBase
(
	p_M_PriceList_ID numeric,
    p_Date timestamp with time zone
)
returns numeric[]
as
$BODY$
		SELECT getPriceListVersionsUpToBase(
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
			)
		)
$BODY$
LANGUAGE SQL
;




