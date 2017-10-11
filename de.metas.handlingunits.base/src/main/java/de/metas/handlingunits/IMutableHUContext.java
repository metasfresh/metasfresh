package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;

import com.google.common.annotations.VisibleForTesting;

import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.impl.CompositeHUTrxListener;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.handlingunits.storage.IHUStorageFactory;

public interface IMutableHUContext extends IHUContext
{
	@Override
	Date getDate();

	void setDate(Date date);

	@Override
	IHUStorageFactory getHUStorageFactory();

	void setHUStorageFactory(IHUStorageFactory huStorageFactory);

	@Override
	IAttributeStorageFactory getHUAttributeStorageFactory();

	void setHUAttributeStorageFactory(IAttributeStorageFactory attributesStorageFactory);

	@Override
	String getTrxName();

	void setTrxName(String trxName);

	@Override
	public CompositeHUTrxListener getTrxListeners();

	/**
	 * Allows to inject a mocked collector for testing. If this setter is not called, this instance has the default colloector.
	 *
	 * @param huPackingMaterialsCollector
	 * @return
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1975
	 */
	@VisibleForTesting
	IMutableHUContext setHUPackingMaterialsCollector(IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> huPackingMaterialsCollector);

	void addEmptyHUListener(EmptyHUListener emptyHUListener);
}
