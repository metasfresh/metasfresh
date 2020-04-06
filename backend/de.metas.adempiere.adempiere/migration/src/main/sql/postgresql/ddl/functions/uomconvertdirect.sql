create or replace function uomConvertDirect
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
	v_MultiplyRate numeric;
	v_QtyConv numeric;
begin
	-- If quantity is null or zero, there is no point to convert
	if (p_Qty is null or p_Qty = 0)
	then
		return p_Qty;
	end if;
	
	-- If same UOM, there is no point to convert
	if (p_C_UOM_From_ID = p_C_UOM_To_ID)
	then
		return p_Qty;
	end if;
	
	--
	-- Direct: p_C_UOM_From_ID -> p_C_UOM_To_ID
	select MultiplyRate
	into v_MultiplyRate
	from C_UOM_Conversion c
	where c.M_Product_ID=p_M_Product_ID
	and c.C_UOM_ID = p_C_UOM_From_ID and c.C_UOM_To_ID = p_C_UOM_To_ID
	and c.IsActive='Y';
	
	--
	-- Direct (reversed): p_C_UOM_To_ID -> p_C_UOM_From_ID
	if (v_MultiplyRate is null)
	then
		select DivideRate
		into v_MultiplyRate
		from C_UOM_Conversion c
		where c.M_Product_ID=p_M_Product_ID
		and c.C_UOM_ID = p_C_UOM_To_ID and c.C_UOM_To_ID = p_C_UOM_From_ID
		and c.IsActive='Y';
	end if;
	
	if (v_MultiplyRate is null)
	then
		return null;
	end if;
	
	v_QtyConv := p_Qty * v_MultiplyRate;

	return v_QtyConv;
end;
$BODY$
LANGUAGE plpgsql STABLE
COST 100;

COMMENT ON FUNCTION public.uomconvertdirect(numeric, numeric, numeric, numeric) 
IS 'Attempts to convert between two UOMs by using the C_UOM_Conversion table. If there is no conversion table record it can use, the function returns null.
Note that the function tries both "directions" so we need just one C_UOM_Conversion record to convert both from "A" to "B" and from "B" to "A".';

