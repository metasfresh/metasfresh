
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU_Individual ( IN Record_ID numeric, IN AD_Language Character Varying (6) );


-- table of same sort as  de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU_Individual;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU_Individual
(
	MovementQty numeric,
	Name Character Varying,
	UOMSymbol Character Varying (10)
);




CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU_Individual ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU AS
$$

(

select 
	max(x.movementqty), x.name, max(x.uomsymbol)
from
(
	(
		select 
		rep.movementqty, rep.name, rep.uomsymbol
		from de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU ( $1, $2 ) rep
	)

	UNION ALL
	(
		select 
			null as movementqty,
			p.name, 
			null as uomsymbol
		from M_InOut io 
		join C_BP_DocLine_Sort bps on bps.C_BPartner_ID = io. C_BPartner_ID AND bps.isActive = 'Y'
		join C_DocLine_Sort dls on dls.C_DocLine_Sort_ID = bps.C_DocLine_Sort_ID AND dls.isActive = 'Y'
		join C_DocLine_Sort_Item dsi on dls.C_DocLine_Sort_ID = dsi.C_DocLine_Sort_ID ANd dsi.isActive = 'Y'
		join m_Product p on dsi.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
		where  io.M_InOut_ID = $1 and io.isActive = 'Y'
		order by dsi.seqno
	)
) x

group by x.name order by x.name
)

	
$$
LANGUAGE sql STABLE
;