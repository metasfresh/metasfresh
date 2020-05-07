package de.metas.fresh.picking.form;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.picking.legacy.form.AbstractTableRowSearchSelectionMatcher;

/**
 * Matchers all rows suitable for given HU.
 * 
 * Mainly, developer needs to implement {@link #createHUQueryBuilder(IHUQueryBuilder)} method.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/06821_Kommissionier_Terminal_Extension_%28104171338645%29
 */
/* package */abstract class AbstractHUTableRowSearchSelectionMatcher extends AbstractTableRowSearchSelectionMatcher
		implements IHUAware
{
	private final Properties ctx;
	private final int warehouseId;

	private I_M_HU hu = null;

	public AbstractHUTableRowSearchSelectionMatcher(final Properties ctx, final int warehouseId)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;

		// Check.assume(warehouseId > 0, "warehouseId > 0");
		// NOTE: warehouseId can be <=0 which will make the matcher invalid
		this.warehouseId = warehouseId;
	}

	protected final Properties getCtx()
	{
		return ctx;
	}

	protected final int getM_Warehouse_ID()
	{
		return warehouseId;
	}

	@Override
	protected boolean load()
	{
		// If there is no warehouse then consider this matcher as invalid
		if (warehouseId <= 0)
		{
			return false;
		}

		//
		// Create initial (generic) HU query builder:
		final IHUQueryBuilder huQueryBuilderInitial = Services.get(IHandlingUnitsDAO.class).createHUQueryBuilder()
				.setContext(ctx, ITrx.TRXNAME_None)
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.addOnlyInWarehouseId(warehouseId);

		//
		// Ask extending classes to customize it
		final IHUQueryBuilder huQueryBuilder = createHUQueryBuilder(huQueryBuilderInitial);
		if (huQueryBuilder == null)
		{
			// customizer said that we will have an invalid rule
			return false;
		}

		//
		// Fetch the HU
		this.hu = huQueryBuilder.firstOnly();

		// If no HU was fetched, consider it as a Null Matcher => i.e. do nothing
		if (hu == null)
		{
			return false;
		}

		loadFromHU(hu);
		return true;
	}

	/**
	 * Creates actual {@link IHUQueryBuilder} to be used when fetching the HU.
	 * 
	 * NOTE: you can even reuse <code>huQueryBuilderInitial</code>, customize it and return it
	 * 
	 * @param huQueryBuilderInitial
	 * @return HU query builder to be used when fetching the HU
	 */
	protected abstract IHUQueryBuilder createHUQueryBuilder(IHUQueryBuilder huQueryBuilderInitial);

	/**
	 * Init this matcher from given HU.
	 * <ul>
	 * <li>Fetch products from given HU
	 * </ul>
	 * 
	 * @param hu
	 */
	private final void loadFromHU(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		//
		// Fetch BPartner for HU
		final int huBPartnerId = hu.getC_BPartner_ID();
		setC_BPartner_ID(huBPartnerId);

		//
		// Fetch products from HU Storage
		final TreeSet<Integer> productIds = new TreeSet<Integer>();
		final Properties ctx = InterfaceWrapperHelper.getCtx(hu);
		final IMutableHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(ctx);
		final List<IHUProductStorage> productStorages = huContext.getHUStorageFactory().getStorage(hu).getProductStorages();
		for (IHUProductStorage productStorage : productStorages)
		{
			final int productId = productStorage.getM_Product().getM_Product_ID();
			productIds.add(productId);
		}
		setProductIds(productIds);
	}

	@Override
	public final I_M_HU getM_HU()
	{
		init();
		return hu;
	}

	@Override
	public final boolean isAllowMultipleResults()
	{
		// it could be that multiple rows can match our HU
		return true;
	}

}
