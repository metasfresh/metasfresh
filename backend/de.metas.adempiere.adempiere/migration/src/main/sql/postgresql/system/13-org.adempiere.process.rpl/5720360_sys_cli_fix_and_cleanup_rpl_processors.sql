
/*fix exchangeName to make the exchange declared in camel-edi*/
update imp_processorparameter set value='exchangeName', parametervalue='de.metas.esb.to.metasfresh.durable', updated='2024-03-27 15:48:00', updatedby=99 where imp_processorparameter_id=540024;

-- actually this is the original and exp_processor_id=540014 is a duplicate, but in our recent DB-images, we have only the duplicate.
delete from exp_processorparameter where exp_processor_id=540011;
delete from exp_processor where exp_processor_id=540011;

/*fix exchangeName to make the exchange declared in camel-edi*/
update exp_processorparameter set value='exchangeName', parametervalue='de.metas.esb.from.metasfresh.durable' , updated='2024-03-27 15:48:00', updatedby=99 where exp_processorparameter_id=540036;



                             