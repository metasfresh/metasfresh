-- update AD_Workflow.AD_User_InCharge_ID with M_warehouse.AD_User_ID from corresponding PP_Product_Planning
update AD_Workflow set AD_User_InCharge_ID = data.AD_User_ID
from
(
select distinct pp.M_Warehouse_ID, pp.AD_Workflow_ID, w.ad_user_ID 
from  PP_Product_Planning pp
join M_warehouse w on w.M_Warehouse_ID = pp.M_warehouse_ID
join AD_Workflow wf on wf.AD_Workflow_ID = pp.AD_Workflow_ID
where w.ad_user_ID is not null
) as data
where data.AD_Workflow_ID = AD_Workflow.AD_Workflow_ID;