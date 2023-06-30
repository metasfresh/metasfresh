package de.metas.inoutcandidate.api;

import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.IReceiptScheduleWarehouseDestProvider;
import de.metas.util.ISingletonService;

/**
 * Service which is creating the right {@link IReceiptScheduleProducer} for given parameters.
 * 
 * @author tsa
 * 
 */
public interface IReceiptScheduleProducerFactory extends ISingletonService
{

	/**
	 * Creates the receipt schedules producer for given <code>tableName</code>
	 * 
	 * NOTE: in case we ask for an asynchronous producer, that producer will always return <code>null</code> results
	 * 
	 * @param async if true then a producer which creates the records asynchronously will be returned
	 */
	IReceiptScheduleProducer createProducer(String tableName, final boolean async);

	IReceiptScheduleProducerFactory registerProducer(String tableName, Class<? extends IReceiptScheduleProducer> producerClass);

	IReceiptScheduleWarehouseDestProvider getWarehouseDestProvider();

	IReceiptScheduleProducerFactory registerWarehouseDestProvider(IReceiptScheduleWarehouseDestProvider provider);

}
