package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.service.ISysConfigBL;
import org.compiere.util.CacheInterface;
import org.compiere.util.CacheMgt;
import org.compiere.util.Util;

import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedSpiProviderService;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IInvoicedSumProvider;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfigProvider;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityInvoiceLineGroupsBuilderProvider;
import de.metas.util.Check;
import de.metas.util.Services;

public class QualityBasedSpiProviderService implements IQualityBasedSpiProviderService
{
	private static final String SYSCONFIG_QualityBasedConfigProvider = "de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfigProvider";
	private static final String SYSCONFIG_QualityInvoiceLineGroupsBuilderProvider = "de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityInvoiceLineGroupsBuilderProvider";

	private IQualityBasedConfigProvider qualityBasedConfigProvider;
	private IQualityBasedConfigProvider qualityBasedConfigProviderDefault;

	private IQualityInvoiceLineGroupsBuilderProvider qualityInvoiceLineGroupsBuilderProvider;
	private IQualityInvoiceLineGroupsBuilderProvider qualityInvoiceLineGroupsBuilderProviderDefault;

	private IInvoicedSumProvider invoicedSumProvider;
	private IInvoicedSumProvider invoicedSumProviderDefault = new InvoicedSumProvider();

	public QualityBasedSpiProviderService()
	{
		super();

		CacheMgt.get().register(new CacheInterface()
		{

			@Override
			public int size()
			{
				return 2;
			}

			@Override
			public int reset()
			{
				QualityBasedSpiProviderService.this.resetDefaults();
				return 2;
			}
		});
	}

	private final void resetDefaults()
	{
		qualityBasedConfigProviderDefault = null;
		qualityInvoiceLineGroupsBuilderProviderDefault = null;
	}

	private final <T> T getInstance(final String sysConfigName, final Class<T> interfaceClass)
	{
		final String classname = Services.get(ISysConfigBL.class).getValue(sysConfigName);
		Check.assumeNotEmpty(classname, "AD_Sysconfig {} shall be set", sysConfigName);

		final T instance = Util.getInstance(interfaceClass, classname);
		return instance;
	}

	@Override
	public IQualityBasedConfigProvider getQualityBasedConfigProvider()
	{
		if (qualityBasedConfigProvider != null)
		{
			return qualityBasedConfigProvider;
		}

		if (qualityBasedConfigProviderDefault == null)
		{
			qualityBasedConfigProviderDefault = getInstance(SYSCONFIG_QualityBasedConfigProvider, IQualityBasedConfigProvider.class);
		}
		return qualityBasedConfigProviderDefault;
	}

	@Override
	public void setQualityBasedConfigProvider(final IQualityBasedConfigProvider provider)
	{
		qualityBasedConfigProvider = provider;
	}

	@Override
	public IQualityInvoiceLineGroupsBuilderProvider getQualityInvoiceLineGroupsBuilderProvider()
	{
		if (qualityInvoiceLineGroupsBuilderProvider != null)
		{
			return qualityInvoiceLineGroupsBuilderProvider;
		}

		if (qualityInvoiceLineGroupsBuilderProviderDefault == null)
		{
			qualityInvoiceLineGroupsBuilderProviderDefault = getInstance(SYSCONFIG_QualityInvoiceLineGroupsBuilderProvider, IQualityInvoiceLineGroupsBuilderProvider.class);
		}

		return qualityInvoiceLineGroupsBuilderProviderDefault;
	}

	@Override
	public void setQualityInvoiceLineGroupsBuilderProvider(final IQualityInvoiceLineGroupsBuilderProvider qualityInvoiceLineGroupsBuilderProvider)
	{
		this.qualityInvoiceLineGroupsBuilderProvider = qualityInvoiceLineGroupsBuilderProvider;
	}

	@Override
	public void setInvoicedSumProvider(final IInvoicedSumProvider invoicedSumProvider)
	{
		this.invoicedSumProvider = invoicedSumProvider;
	}

	@Override
	public IInvoicedSumProvider getInvoicedSumProvider()
	{
		if (invoicedSumProvider != null)
		{
			return invoicedSumProvider;
		}
		return invoicedSumProviderDefault;
	}
}
