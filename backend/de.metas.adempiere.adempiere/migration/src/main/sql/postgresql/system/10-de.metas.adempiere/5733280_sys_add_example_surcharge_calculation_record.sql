INSERT INTO M_DiscountSchema_Calculated_Surcharge (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_DiscountSchema_Calculated_Surcharge_ID,Name,Surcharge_Calc_SQL,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-09-12 13:57:16.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540000,'Transportkosten','SELECT COALESCE (dsc.freight_cost_calc_price / (piitem_lu.qty*mhpip.qty), 0::numeric) as surcharge_transport
from m_productprice mpp
         left join m_product mp on mp.m_product_id = mpp.m_product_id
         left join m_hu_pi_item_product mhpip on mpp.m_hu_pi_item_product_id = mhpip.m_hu_pi_item_product_id
         left join m_hu_pi_item piitem on mhpip.m_hu_pi_item_id = piitem.m_hu_pi_item_id
         left join m_hu_pi_version piv on piitem.m_hu_pi_version_id = piv.m_hu_pi_version_id and piv.iscurrent = ''Y''
         left join m_hu_pi pi on piv.m_hu_pi_id = pi.m_hu_pi_id
         left join m_hu_pi pi_lu on pi_lu.isdefaultlu = ''Y''
         left join m_hu_pi_version piv_lu on pi_lu.m_hu_pi_id = piv_lu.m_hu_pi_id and piv_lu.iscurrent = ''Y''
         left join m_hu_pi_item piitem_lu on pi.m_hu_pi_id = piitem_lu.included_hu_pi_id
         left join m_pricelist_version plv on plv.m_pricelist_version_id = $1 -- Target_PriceList_Version_ID
         left join M_DiscountSchema_Calculated_Surcharge_Price dsc ON dsc.isActive = ''Y'' AND (dsc.c_region_id = plv.c_region_id OR dsc.c_region_id ISNULL)
where mpp.m_productprice_id = $2 --Source_M_ProductPrice_ID
ORDER BY dsc.c_region_id DESC NULLS LAST, surcharge_transport DESC
LIMIT 1;',TO_TIMESTAMP('2024-09-12 13:57:16.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
