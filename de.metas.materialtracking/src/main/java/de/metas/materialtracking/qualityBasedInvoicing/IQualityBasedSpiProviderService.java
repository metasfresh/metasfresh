package de.metas.materialtracking.qualityBasedInvoicing;

import de.metas.materialtracking.qualityBasedInvoicing.spi.IInvoicedSumProvider;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfigProvider;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityInvoiceLineGroupsBuilderProvider;
import de.metas.util.ISingletonService;

public interface IQualityBasedSpiProviderService extends ISingletonService
{
	/**
	 *
	 * @return config provider; never return <code>null</code>
	 */
	IQualityBasedConfigProvider getQualityBasedConfigProvider();

	void setQualityBasedConfigProvider(IQualityBasedConfigProvider provider);

	/**
	 *
	 * @return invoice line group builder provider; never return <code>null</code>
	 */
	IQualityInvoiceLineGroupsBuilderProvider getQualityInvoiceLineGroupsBuilderProvider();

	void setQualityInvoiceLineGroupsBuilderProvider(IQualityInvoiceLineGroupsBuilderProvider qualityInvoiceLineGroupsBuilderProvider);

	/**
	 * Allows to register a provider that will for a given material tracking return the amount that was already invoiced so far.
	 * <p>
	 * Used to get the already paid downpayment amounts when it is time for the final settlement.
	 */
	void setInvoicedSumProvider(IInvoicedSumProvider provider);

	IInvoicedSumProvider getInvoicedSumProvider();

}
