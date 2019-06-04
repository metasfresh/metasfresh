/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.process;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

import java.util.List;
import java.util.Objects;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.util.lang.IContextAware;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.logging.LogManager;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.attribute.ScannedAttributeValue;
import de.metas.vertical.pharma.securpharm.model.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.model.ProductDetails;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.NonNull;

// TODO move to webui for HU splitting and view update
public class M_HU_SecurpharmScan extends JavaProcess implements IProcessPrecondition
{
	private static final Logger logger = LogManager.getLogger(M_HU_SecurpharmScan.class);

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
	protected final SecurPharmService securPharmService = Adempiere.getBean(SecurPharmService.class);

	public static final String PARAM_DataMatrix = "dataMatrix";
	@Param(mandatory = true, parameterName = PARAM_DataMatrix)
	private String dataMatrixString;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
		if (!securPharmService.hasConfig())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no SecurPharm config");
		}

		final I_M_HU hu = context.getSelectedModel(I_M_HU.class);
		if (hu == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		if (Objects.equals(hu.getHUStatus(), X_M_HU.HUSTATUS_Destroyed)
				|| Objects.equals(hu.getHUStatus(), X_M_HU.HUSTATUS_Shipped))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("handling unit status not appropriate");
		}

		final IAttributeStorage attributeStorage = getAttributeStorage(hu);
		if (!attributeStorage.hasAttribute(AttributeConstants.ATTR_SerialNo)
				|| !attributeStorage.hasAttribute(AttributeConstants.ATTR_LotNr)
				|| !attributeStorage.hasAttribute(AttributeConstants.ATTR_BestBeforeDate)
				|| !attributeStorage.hasAttribute(AttributeConstants.ATTR_Scanned))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("attributes missing");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final I_M_HU handlingUnit = getHandlingUnit();
		scanAndUpdate(handlingUnit);
		return MSG_OK;
	}

	protected I_M_HU getHandlingUnit()
	{
		final HuId huId = HuId.ofRepoId(getRecord_ID());
		return handlingUnitsRepo.getById(huId);
	}

	protected final DataMatrixCode getDataMatrix()
	{
		return DataMatrixCode.ofString(dataMatrixString);
	}

	private void scanAndUpdate(@NonNull final I_M_HU hu)
	{
		final IAttributeStorage attributeStorage = getAttributeStorage(hu);

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		final SecurPharmProduct product = securPharmService.getAndSaveProduct(getDataMatrix(), huId);
		if (!product.isError())
		{
			final ProductDetails productDetails = product.getProductDetails();
			if (productDetails.isActive())
			{
				// TODO check if it fits current data and split otherwise
				attributeStorage.setValue(AttributeConstants.ATTR_BestBeforeDate, productDetails.getExpirationDate().toLocalDate());
				attributeStorage.setValue(AttributeConstants.ATTR_LotNr, productDetails.getLot());
				attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.YES.getCode());
			}
			else
			{
				// FRAUD!
				attributeStorage.setValue(AttributeConstants.ATTR_SerialNo, productDetails.getSerialNumber());
				attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.ERROR.getCode());
				// TODO HU split
			}
		}
		else
		{
			attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.ERROR.getCode());
		}

		attributeStorage.saveChangesIfNeeded();
	}

	private IAttributeStorage getAttributeStorage(final I_M_HU hu)
	{
		final IContextAware ctxAware = getContextAware(hu);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		return attributeStorageFactory.getAttributeStorage(hu);
	}

	private I_M_HU getHUBySerialNoAndHUStatus(@NonNull final String serialNo, @NonNull final String huStatus)
	{
		final List<I_M_HU> hus = handlingUnitsRepo.createHUQueryBuilder()
				.addOnlyWithAttribute(AttributeConstants.ATTR_SerialNo, serialNo)
				// TODO: make sure LotNo and Scanned exists
				.addHUStatusToInclude(huStatus)
				.createQueryBuilder()
				.orderBy(I_M_HU.COLUMN_M_HU_ID)
				.setLimit(2)
				.create()
				.list();

		if (hus.isEmpty())
		{
			return null;
		}
		else if (hus.size() == 1)
		{
			return hus.get(0);
		}
		else
		{
			logger.warn("More than one HU found for serialNo={} and huStatus={}. Returning first one: {}", serialNo, huStatus, hus);
			return hus.get(0);
		}
	}
}
