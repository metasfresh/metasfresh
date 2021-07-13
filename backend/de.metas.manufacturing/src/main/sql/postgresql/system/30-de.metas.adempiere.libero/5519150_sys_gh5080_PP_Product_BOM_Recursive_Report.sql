drop function if exists PP_Product_BOM_Recursive_Report(numeric);
create or replace function PP_Product_BOM_Recursive_Report(p_PP_Product_BOM_ID numeric)
returns table
(
	Line text,
	ProductValue varchar,
	ProductName varchar,
	QtyBOM numeric,
	Percentage numeric,
	UOMSymbol varchar
)
as
$BODY$
	select
		t.Line,
		t.ProductValue,
		t.ProductName,
		t.QtyBOM,
		t.Percentage,
		t.UOMSymbol
	from PP_Product_BOM_Recursive(PP_Product_BOM_Recursive_Report.p_PP_Product_BOM_ID) t
	order by t.path
$BODY$
LANGUAGE sql STABLE
COST 100;
