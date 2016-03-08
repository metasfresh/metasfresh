update AD_ModelValidator set modelvalidationClass='de.metas.procurement.base.Main' where modelvalidationClass='de.metas.procurement.base.model.interceptor.Main';
update C_Queue_PackageProcessor set classname='de.metas.procurement.base.order.async.PMM_GenerateOrders' where classname='de.metas.procurement.base.async.PMM_GenerateOrders';
UPDATE C_Queue_PackageProcessor SET InternalName='de.metas.procurement.base.order.async.PMM_GenerateOrders',Updated=TO_TIMESTAMP('2016-03-01 13:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540039;


