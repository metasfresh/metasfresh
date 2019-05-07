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

package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.SecurPharmConstants;
import de.metas.vertical.pharma.securpharm.attribute.ScannedAttributeValue;
import de.metas.vertical.pharma.securpharm.model.ProductData;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.util.lang.IContextAware;
import org.compiere.Adempiere;
import org.springframework.beans.factory.annotation.Autowired;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

public class WEBUI_M_HU_ReturnFromCustomer_Pharma extends WEBUI_M_HU_ReturnFromCustomer
{

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@Autowired
	private SecurPharmService securPharmService;
	@Param(mandatory = true, parameterName = SecurPharmConstants.SCAN_PROCESS_DATAMATRIX_PARAM)
	private String dataMatrix;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		if (!streamSelectedHUIds(Select.ONLY_TOPLEVEL).findAny().isPresent())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}
		if (!Adempiere.getBean(SecurPharmService.class).hasConfig())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No securpharm config");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		streamSelectedHUs(Select.ONLY_TOPLEVEL).forEach(handlingUnit -> scanAndUpdate(handlingUnit));
		return super.doIt();
	}

	//TODO maybe refactor after moving scan process here
	private void scanAndUpdate(@NonNull final I_M_HU handlingUnit)
	{
		final HuId huId = HuId.ofRepoId(handlingUnit.getM_HU_ID());
		final IAttributeStorage attributeStorage = getAttributeStorage(handlingUnit);
		final SecurPharmProductDataResult result;
		try
		{
			result = securPharmService.getAndSaveProductData(dataMatrix, huId);
			if (!result.isError() && result.getProductData() != null)
			{
				final ProductData productData = result.getProductData();
				if (productData.isActive())
				{
					//TODO just update or split?
					attributeStorage.setValue(AttributeConstants.ATTR_BestBeforeDate, productData.getExpirationDate());
					attributeStorage.setValue(AttributeConstants.ATTR_LotNr, productData.getLot());
					attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.Y.name());
				}
				else
				{
					attributeStorage.setValue(AttributeConstants.ATTR_SerialNo, productData.getSerialNumber());
					attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.E.name());
					//TODO should the process still continue with return?
				}
			}
			else
			{
				attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.E.name());
				//TODO should the process still continue with return?
			}
		}
		catch (Exception e)
		{
			attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.E);
		}
		attributeStorage.saveChangesIfNeeded();
	}

	private IAttributeStorage getAttributeStorage(I_M_HU handlingUnit)
	{
		final IContextAware ctxAware = getContextAware(handlingUnit);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		return attributeStorageFactory.getAttributeStorage(handlingUnit);
	}

}
