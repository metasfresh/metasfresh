package de.metas.inoutcandidate.api.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;

import com.google.common.base.MoreObjects;

import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.spi.AsyncReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.IReceiptScheduleWarehouseDestProvider;
import de.metas.inoutcandidate.spi.impl.OrderLineReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.impl.OrderReceiptScheduleProducer;

public class ReceiptScheduleProducerFactory implements IReceiptScheduleProducerFactory
{
	private final Map<String, CopyOnWriteArrayList<Class<? extends IReceiptScheduleProducer>>> producerClasses = new ConcurrentHashMap<>();
	/** Source table name to {@link IReceiptScheduleWarehouseDestProvider} */
	private final CompositeReceiptScheduleWarehouseDestProvider warehouseDestProviders = new CompositeReceiptScheduleWarehouseDestProvider(DefaultFromOrderLineWarehouseDestProvider.instance);

	public ReceiptScheduleProducerFactory()
	{
		super();

		// Register standard producers:
		registerProducer(I_C_Order.Table_Name, OrderReceiptScheduleProducer.class);
		registerProducer(I_C_OrderLine.Table_Name, OrderLineReceiptScheduleProducer.class);
	}

	@Override
	public IReceiptScheduleProducer createProducer(final String tableName, final boolean async)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");

		if (async)
		{
			return new AsyncReceiptScheduleProducer();
		}

		final List<Class<? extends IReceiptScheduleProducer>> producerClassesForTable = producerClasses.get(tableName);
		if (producerClassesForTable == null || producerClassesForTable.isEmpty())
		{
			throw new AdempiereException("No " + IReceiptScheduleProducer.class + " implementation was found for " + tableName);
		}

		final CompositeReceiptScheduleProducerExecutor compositeProducer = new CompositeReceiptScheduleProducerExecutor();
		compositeProducer.setReceiptScheduleProducerFactory(this);
		for (final Class<? extends IReceiptScheduleProducer> producerClass : producerClassesForTable)
		{
			try
			{
				final IReceiptScheduleProducer producer = producerClass.newInstance();
				producer.setReceiptScheduleProducerFactory(this);
				compositeProducer.addReceiptScheduleProducer(producer);
			}
			catch (Exception e)
			{
				throw new AdempiereException("Cannot instantiate producer class " + producerClass, e);
			}
		}

		return compositeProducer;
	}

	@Override
	public IReceiptScheduleProducerFactory registerProducer(final String tableName, final Class<? extends IReceiptScheduleProducer> producerClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(producerClass, "producerClass not null");

		producerClasses
				.computeIfAbsent(tableName, tableNameKey -> new CopyOnWriteArrayList<>())
				.addIfAbsent(producerClass);

		return this;
	}

	@Override
	public IReceiptScheduleWarehouseDestProvider getWarehouseDestProvider()
	{
		return warehouseDestProviders;
	}

	@Override
	public IReceiptScheduleProducerFactory registerWarehouseDestProvider(final IReceiptScheduleWarehouseDestProvider provider)
	{
		warehouseDestProviders.addProvider(provider);
		return this;
	}

	/** Mutable composite {@link IReceiptScheduleWarehouseDestProvider} */
	private static final class CompositeReceiptScheduleWarehouseDestProvider implements IReceiptScheduleWarehouseDestProvider
	{
		private final IReceiptScheduleWarehouseDestProvider defaultProvider;
		private final CopyOnWriteArrayList<IReceiptScheduleWarehouseDestProvider> providers = new CopyOnWriteArrayList<>();

		private CompositeReceiptScheduleWarehouseDestProvider(final IReceiptScheduleWarehouseDestProvider defaultProvider)
		{
			super();

			Check.assumeNotNull(defaultProvider, "Parameter defaultProvider is not null");
			this.defaultProvider = defaultProvider;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("providers", providers)
					.add("defaultProvider", defaultProvider)
					.toString();
		}

		private void addProvider(final IReceiptScheduleWarehouseDestProvider provider)
		{
			Check.assumeNotNull(provider, "Parameter provider is not null");
			providers.add(provider);
		}

		@Override
		public I_M_Warehouse getWarehouseDest(final IContext context)
		{
			return Stream.concat(providers.stream(), Stream.of(defaultProvider))
					.map(provider -> provider.getWarehouseDest(context))
					.filter(warehouseDest -> warehouseDest != null)
					.findFirst()
					.orElse(null);
		}
	}
}
