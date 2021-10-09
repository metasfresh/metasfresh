create or replace function uomConvert
(
	p_M_Product_ID numeric
	, p_C_UOM_From_ID numeric
	, p_C_UOM_To_ID numeric
	, p_Qty numeric
)
returns numeric
as
$BODY$
declare
	v_Product_UOM_ID numeric;
	v_Precision numeric;
	v_QtyConv numeric;
begin
	-- Try converting using the direct conversion (if exists)
	v_QtyConv := uomConvertDirect(p_M_Product_ID, p_C_UOM_From_ID, p_C_UOM_To_ID, p_Qty);

	-- Try converting using product's UOM as intermediate UOM
	if (v_QtyConv is null) then
		-- Fetch product's UOM and precision
		select p.C_UOM_ID
		into v_Product_UOM_ID
		from M_Product p
		where p.M_Product_ID=p_M_Product_ID;

		-- Convert from p_C_UOM_From_ID to Product UOM
		v_QtyConv := uomConvertDirect(p_M_Product_ID, p_C_UOM_From_ID, v_Product_UOM_ID, p_Qty);
		-- Convert from Product UOM to p_C_UOM_To_ID
		v_QtyConv := uomConvertDirect(p_M_Product_ID, v_Product_UOM_ID, p_C_UOM_To_ID, v_QtyConv);
	end if;

	--
	-- Round to precision
	select u.StdPrecision into v_Precision from C_UOM u where u.C_UOM_ID=p_C_UOM_To_ID;
	if (v_Precision is not null) then
		v_QtyConv := round(v_QtyConv, v_Precision);
	end if;

	return v_QtyConv;
end;
$BODY$
LANGUAGE plpgsql STABLE
COST 100;

COMMENT ON FUNCTION public.uomconvert(numeric, numeric, numeric, numeric) 
IS 'Attempts to convert between two UOMs by first calling the function uomconvertdirect with the given p_c_uom_from_id and p_c_uom_to_id. 
If uomconvertdirect returns null, it  makes another try, this time with two hops, with the given product''s ID in the middle.
In other woths if there is not conversion rule to convert between "A" and "C" and if the given product has the uom "B", the function tries to convert "A" => "B" and then "B" => "C" in two steps';

