update PP_Order_Node n set C_UOM_ID=(
	select p.C_UOM_ID
	from PP_Order o
	inner join M_Product p on p.M_Product_ID=o.M_Product_ID
	where o.PP_Order_ID=n.PP_Order_ID)
where n.C_UOM_ID is null;

