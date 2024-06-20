package de.metas.handlingunits.attribute.impl;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class HUAttributesBL implements IHUAttributesBL
{

	private final static transient Logger logger = LogManager.getLogger(HUAttributesBL.class);

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IAttributesBL attributesBL = Services.get(IAttributesBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

	private final AdMessageKey MSG_MandatoryOnPicking = AdMessageKey.of("M_AttributeUse_MandatoryOnPicking");
	private final AdMessageKey MSG_MandatoryOnShipment = AdMessageKey.of("M_AttributeUse_MandatoryOnShipment");

	@Override
	@Nullable
	public I_M_HU getM_HU_OrNull(@Nullable final IAttributeSet attributeSet)
	{
		if (attributeSet instanceof IHUAware)
		{
			final IHUAware huAware = (IHUAware)attributeSet;
			return huAware.getM_HU();
		}
		else
		{
			return null;
		}
	}

	@Override
	public I_M_HU getM_HU(@NonNull final IAttributeSet attributeSet)
	{
		final I_M_HU hu = getM_HU_OrNull(attributeSet);
		if (hu == null)
		{
			throw new IllegalArgumentException("Cannot get M_HU from " + attributeSet);
		}

		return hu;
	}

	@Override
	public void updateHUAttributeRecursive(@NonNull final HuId huId, @NonNull final AttributeCode attributeCode, @Nullable final Object attributeValue, @Nullable final String onlyHUStatus)
	{
		final I_M_Attribute attribute = attributeDAO.retrieveAttributeByValueOrNull(attributeCode);
		if (attribute == null)
		{
			logger.debug("M_Attribute with Value={} does not exis of is inactive; -> do nothing", attributeCode.getCode());
			return;
		}
		final I_M_HU hu = handlingUnitsDAO.getById(huId);
		updateHUAttributeRecursive(hu, attribute, attributeValue, onlyHUStatus);
	}

	@Override
	@Deprecated
	public void updateHUAttributeRecursive(final I_M_HU hu,
			final I_M_Attribute attribute,
			final Object attributeValue,
			final String onlyHUStatus)
	{
		final ILoggable loggable = Loggables.get();

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IAttributeStorageFactory huAttributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory(storageFactory);

		final HUIterator iterator = new HUIterator();
		// I'm not 100% sure which time to pick, but i think for the iterator itself it makes no difference, and i also don't need it in the beforeHU implementation.
		iterator.setDate(SystemTime.asZonedDateTime());
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			public Result beforeHU(final IMutable<I_M_HU> currentHUMutable)
			{
				final I_M_HU currentHU = currentHUMutable.getValue();
				if (Check.isEmpty(onlyHUStatus) || onlyHUStatus.equals(currentHU.getHUStatus()))
				{
					final IAttributeStorage attributeStorage = huAttributeStorageFactory.getAttributeStorage(currentHU);

					if (attributeStorage.hasAttribute(attribute))
					{
						attributeStorage.setValueNoPropagate(attribute, attributeValue);
						attributeStorage.saveChangesIfNeeded();
						final String msg = "Updated IAttributeStorage " + attributeStorage + " of M_HU " + currentHU;
						loggable.addLog(msg);
					}
				}

				return Result.CONTINUE;
			}
		});
		iterator.iterate(hu);
	}

	@Override
	public BigDecimal getQualityDiscountPercent(final I_M_HU hu)
	{

		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(hu);

		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);

		final IAttributeStorage attributeStorage = getAttributeStorage(huContext, hu);

		final I_M_Attribute attr_QualityDiscountPercent = attributeDAO.retrieveAttributeByValue(HUAttributeConstants.ATTR_QualityDiscountPercent_Value);

		if (!attributeStorage.hasAttribute(attr_QualityDiscountPercent))
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal qualityDiscountPercent = attributeStorage.getValueAsBigDecimal(attr_QualityDiscountPercent);
		if (qualityDiscountPercent == null)
		{
			return BigDecimal.ZERO;
		}

		return qualityDiscountPercent.divide(Env.ONEHUNDRED);
	}

	private final IAttributeStorage getAttributeStorage(final IHUContext huContext, final I_M_HU hu)
	{
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu);
		return attributeStorage;
	}

	@Override
	public boolean isAutomaticallySetLotNumber()
	{
		return sysConfigBL.getBooleanValue("de.metas.handlingunits.attributes.AutomaticallySetLotNumber", false);
	}

	@Override
	public boolean isAutomaticallySetBestBeforeDate()
	{
		return sysConfigBL.getBooleanValue("de.metas.handlingunits.attributes.AutomaticallySetBestBeforeDate", false);
	}

	@Override
	public void validateMandatoryShipmentAttributes(@NonNull final HuId huId, @NonNull final ProductId productId)
	{
		final AttributeSetId attributeSetId = productBL.getAttributeSetId(productId);

		final ImmutableList<I_M_Attribute> attributesMandatoryOnShipment = attributeDAO.getAttributesByAttributeSetId(attributeSetId).stream()
				.filter(attribute -> attributesBL
						.isMandatoryOnShipment(productId,
											   AttributeId.ofRepoId(attribute.getM_Attribute_ID())))
				.collect(ImmutableList.toImmutableList());

		validateMandatoryAttributes(huId, productId, attributesMandatoryOnShipment, MSG_MandatoryOnShipment);

	}

	@Override
	public void validateMandatoryPickingAttributes(@NonNull final HuId huId, @NonNull final ProductId productId)
	{
		final ImmutableList<I_M_Attribute> attributesMandatoryOnPicking = attributesBL.getAttributesMandatoryOnPicking(productId);

		validateMandatoryAttributes(huId, productId, attributesMandatoryOnPicking, MSG_MandatoryOnPicking);
	}

	private void validateMandatoryAttributes(@NonNull final HuId huId,
			@NonNull final ProductId productId,
			@NonNull final ImmutableList<I_M_Attribute> mandatoryAttributes,
			@NonNull final AdMessageKey messageKey)
	{
		final I_M_HU huRecord = handlingUnitsDAO.getById(huId);

		final IAttributeStorageFactory attributesFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();

		final IAttributeStorage attributeStorage = attributesFactory.getAttributeStorage(huRecord);

		for (final I_M_Attribute attribute : mandatoryAttributes)
		{
			final Object attributeValue = attributeStorage.getValue(attribute);
			if (Check.isEmpty(attributeValue))
			{
				final String productName = productBL.getProductName(productId);
				throw new AdempiereException(
						messageKey,
						attribute.getName(), productName);
			}
		}
	}

	@Override
	public boolean areMandatoryPickingAttributesFulfilled(
			@NonNull final HuId huId,
			@NonNull final ProductId productId)
	{
		final ImmutableList<I_M_Attribute> attributesMandatoryOnPicking = attributesBL.getAttributesMandatoryOnPicking(productId);

		final I_M_HU huRecord = handlingUnitsDAO.getById(huId);

		final IAttributeStorageFactory attributesFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();

		final IAttributeStorage attributeStorage = attributesFactory.getAttributeStorage(huRecord);

		for (final I_M_Attribute attribute : attributesMandatoryOnPicking)
		{
			final Object attributeValue = attributeStorage.getValue(attribute);

			if (Check.isEmpty(attributeValue))
			{
				return false;
			}
		}

		return true;
	}

	public void transferAttributesForSingleProductHUs(
			@NonNull final I_M_HU huFrom,
			@NonNull final I_M_HU huTo)
	{
		final IHUContextProcessorExecutor executor = huTrxBL.createHUContextProcessorExecutor();

		executor.run(huContext -> {

			final IHUTransactionAttributeBuilder trxAttributesBuilder = executor.getTrxAttributesBuilder();

			final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
			final IAttributeStorage huAttributeStorageFrom = attributeStorageFactory.getAttributeStorage(huFrom);
			final IAttributeStorage huAttributeStorageTo = attributeStorageFactory.getAttributeStorage(huTo);

			final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
			final IHUStorage huStorageFrom = storageFactory.getStorage(huTo);

			final IHUProductStorage productStorage = handlingUnitsBL.getStorageFactory()
					.getSingleHUProductStorage(huTo);

			final IHUAttributeTransferRequest request = new HUAttributeTransferRequestBuilder(huContext)
					.setProductId(productStorage.getProductId())
					.setQuantity(productStorage.getQty())
					.setAttributeStorageFrom(huAttributeStorageFrom)
					.setAttributeStorageTo(huAttributeStorageTo)
					.setHUStorageFrom(huStorageFrom)
					.create();
			
			trxAttributesBuilder.transferAttributes(request);
		});
	}

	@Override
	public void updateHUAttribute(@NonNull final HuId huId, @NonNull final AttributeCode attributeCode, @Nullable final Object attributeValue)
	{
		final I_M_Attribute attribute = attributeDAO.retrieveAttributeByValueOrNull(attributeCode);
		if (attribute == null)
		{
			logger.debug("M_Attribute with Value={} does not exist or it is inactive; -> do nothing", attributeCode.getCode());
			return;
		}

		final I_M_HU hu = handlingUnitsDAO.getById(huId);

		huTrxBL.createHUContextProcessorExecutor().run(huContext -> {

			final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
			final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu);

			if (attributeStorage.hasAttribute(attribute))
			{
				attributeStorage.setValue(attribute, attributeValue);
			}

			attributeStorage.saveChangesIfNeeded();
		});
	}
}
