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
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION uomConvertDirect(numeric, numeric, numeric, numeric) OWNER TO adempiere;
