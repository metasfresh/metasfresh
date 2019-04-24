/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.process.*;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.SecurPharmConstants;
import de.metas.vertical.pharma.securpharm.model.AttributeScannedValue;
import de.metas.vertical.pharma.securpharm.model.ProductData;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.util.lang.IContextAware;
import org.apache.commons.lang3.StringUtils;
import org.compiere.Adempiere;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

public class M_HU_SecurpharmScan extends JavaProcess implements IProcessPrecondition
{

	protected final SecurPharmService securPharmService = Adempiere.getBean(SecurPharmService.class);

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@Param(parameterName = SecurPharmConstants.SCAN_PROCESS_DATAMATRIX_PARAM)
	private String dataMatrix;

	@Override
	protected String doIt() throws Exception
	{
		final I_M_HU handlingUnit = getHandlingUnit();
		scanAndUpdate(handlingUnit);
		return MSG_OK;
	}

	protected I_M_HU getHandlingUnit()
	{
		return getRecord(I_M_HU.class);
	}

	private void scanAndUpdate(@NonNull final I_M_HU handlingUnit) throws Exception
	{
		final HuId huId = HuId.ofRepoId(handlingUnit.getM_HU_ID());
		final SecurPharmProductDataResult result = securPharmService.getAndSaveProductData(dataMatrix, huId);
		final IAttributeStorage attributeStorage = getAttributeStorage(handlingUnit);
		if (!result.isError() && result.getProductData() != null)
		{
			final ProductData productData = result.getProductData();
			if (productData.isActive())
			{
				//TODO check if it fits current data and split otherwise
				attributeStorage.setValue(AttributeConstants.ATTR_BestBeforeDate, productData.getExpirationDate());
				attributeStorage.setValue(AttributeConstants.ATTR_LotNr, productData.getLot());
				attributeStorage.setValue(AttributeConstants.ATTR_Scanned, AttributeScannedValue.Y);
			}
			else
			{
				attributeStorage.setValue(AttributeConstants.ATTR_SerialNo, productData.getSerialNumber());
				attributeStorage.setValue(AttributeConstants.ATTR_Scanned, AttributeScannedValue.E);
				//TODO HU split
			}
			attributeStorage.saveChangesIfNeeded();
		}else{
			attributeStorage.setValue(AttributeConstants.ATTR_Scanned, AttributeScannedValue.E);
		}
	}

	private IAttributeStorage getAttributeStorage(I_M_HU handlingUnit)
	{
		final IContextAware ctxAware = getContextAware(handlingUnit);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		return attributeStorageFactory.getAttributeStorage(handlingUnit);
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
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
		I_M_HU handlingUnit = context.getSelectedModel(I_M_HU.class);
		if (StringUtils.equals(handlingUnit.getHUStatus(), X_M_HU.HUSTATUS_Destroyed) || StringUtils.equals(handlingUnit.getHUStatus(), X_M_HU.HUSTATUS_Shipped))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("handling unit status not appropriate");
		}
		final IAttributeStorage attributeStorage = getAttributeStorage(handlingUnit);
		if (!attributeStorage.hasAttribute(AttributeConstants.ATTR_SerialNo) || !attributeStorage.hasAttribute(AttributeConstants.ATTR_LotNr)
				|| !attributeStorage.hasAttribute(AttributeConstants.ATTR_BestBeforeDate) || !attributeStorage.hasAttribute(AttributeConstants.ATTR_Scanned))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("attributes missing");
		}
		//TODO check if vendor != manufacturer - this is in material receipt
		return ProcessPreconditionsResolution.accept();
	}

}
