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

import java.util.Objects;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.util.lang.IContextAware;
import org.compiere.Adempiere;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
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
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	protected final SecurPharmService securPharmService = Adempiere.getBean(SecurPharmService.class);

	public static final String PARAM_DataMatrix = "dataMatrix";
	@Param(mandatory = true, parameterName = PARAM_DataMatrix)
	private String dataMatrix;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		if (!securPharmService.hasConfig())
		{
			return ProcessPreconditionsResolution.reject();
		}
		final I_M_HU hu = context.getSelectedModel(I_M_HU.class);
		if (hu != null)
		{
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
		return getRecord(I_M_HU.class);
	}

	protected final DataMatrixCode getDataMatrix()
	{
		return DataMatrixCode.ofString(dataMatrix);
	}

	private void scanAndUpdate(@NonNull final I_M_HU hu)
	{
		final IAttributeStorage attributeStorage = getAttributeStorage(hu);

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		final SecurPharmProduct product = securPharmService.getAndSaveProductData(getDataMatrix(), huId);
		if (!product.isError() && product.getProductDetails() != null)
		{
			final ProductDetails productData = product.getProductDetails();
			if (productData.isActive())
			{
				// TODO check if it fits current data and split otherwise
				attributeStorage.setValue(AttributeConstants.ATTR_BestBeforeDate, productData.getExpirationDate());
				attributeStorage.setValue(AttributeConstants.ATTR_LotNr, productData.getLot());
				attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.YES.getCode());
			}
			else
			{
				attributeStorage.setValue(AttributeConstants.ATTR_SerialNo, productData.getSerialNumber());
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
}
