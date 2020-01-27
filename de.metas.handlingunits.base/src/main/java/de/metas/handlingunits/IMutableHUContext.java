package de.metas.handlingunits;

import java.time.ZonedDateTime;

import com.google.common.annotations.VisibleForTesting;

import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.impl.CompositeHUTrxListener;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.handlingunits.storage.IHUStorageFactory;

public interface IMutableHUContext extends IHUContext
{
	@Override
	ZonedDateTime getDate();

	void setDate(ZonedDateTime date);

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
	CompositeHUTrxListener getTrxListeners();

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
