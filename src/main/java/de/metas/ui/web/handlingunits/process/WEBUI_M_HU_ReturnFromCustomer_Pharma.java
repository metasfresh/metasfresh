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

package de.metas.ui.web.handlingunits.process;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.util.lang.IContextAware;
import org.compiere.Adempiere;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.logging.LogManager;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.attribute.ScannedAttributeValue;
import de.metas.vertical.pharma.securpharm.model.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.model.ProductDetails;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.process.M_HU_SecurpharmScan;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.NonNull;

public class WEBUI_M_HU_ReturnFromCustomer_Pharma extends WEBUI_M_HU_ReturnFromCustomer
{
	private static final Logger logger = LogManager.getLogger(WEBUI_M_HU_ReturnFromCustomer_Pharma.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@Autowired
	private SecurPharmService securPharmService;

	@Param(mandatory = true, parameterName = M_HU_SecurpharmScan.PARAM_DataMatrix)
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
		streamSelectedHUs(Select.ONLY_TOPLEVEL).forEach(this::scanAndUpdate);
		return super.doIt();
	}

	// TODO maybe refactor after moving scan process here
	private void scanAndUpdate(@NonNull final I_M_HU hu)
	{
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		final IAttributeStorage attributeStorage = getAttributeStorage(hu);
		try
		{
			final SecurPharmProduct product = securPharmService.getAndSaveProductData(getDataMatrix(), huId);
			if (!product.isError() && product.getProductDetails() != null)
			{
				final ProductDetails productDetails = product.getProductDetails();
				if (productDetails.isActive())
				{
					// TODO just update or split?
					attributeStorage.setValue(AttributeConstants.ATTR_BestBeforeDate, productDetails.getExpirationDate());
					attributeStorage.setValue(AttributeConstants.ATTR_LotNr, productDetails.getLot());
					attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.Y.name());
				}
				else
				{
					attributeStorage.setValue(AttributeConstants.ATTR_SerialNo, productDetails.getSerialNumber());
					attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.E.name());
					// TODO should the process still continue with return?
				}
			}
			else
			{
				attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.E.name());
				// TODO should the process still continue with return?
			}
		}
		catch (final Exception ex)
		{
			logger.warn("", ex);
			attributeStorage.setValue(AttributeConstants.ATTR_Scanned, ScannedAttributeValue.E);
		}

		attributeStorage.saveChangesIfNeeded();
	}
	
	protected final DataMatrixCode getDataMatrix()
	{
		return DataMatrixCode.ofString(dataMatrix);
	}

	private IAttributeStorage getAttributeStorage(final I_M_HU hu)
	{
		final IContextAware ctxAware = getContextAware(hu);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		return attributeStorageFactory.getAttributeStorage(hu);
	}

}
