package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Receive planning HUs using given configuration (parameters).
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class WEBUI_M_ReceiptSchedule_ReceiveAdditionalHUs_UsingConfig extends HUEditorProcessTemplate implements IProcessDefaultParametersProvider
{
	private static final String PARAM_IsSaveLUTUConfiguration = "IsSaveLUTUConfiguration";
	//
	// Parameters
	private static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	//
	private static final String PARAM_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";
	//
	private static final String PARAM_QtyCU = "QtyCU";
	//
	private static final String PARAM_QtyTU = "QtyTU";
	//
	private static final String PARAM_QtyLU = "QtyLU";
	private final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
	private final IAttributeStorageFactory attributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();

	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

	@Autowired
	private DocumentCollection documentsCollection;
	private I_M_HU_LUTU_Configuration _defaultLUTUConfiguration; // lazy
	@Param(parameterName = PARAM_IsSaveLUTUConfiguration)
	private boolean p_IsSaveLUTUConfiguration;
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private int p_M_HU_PI_Item_Product_ID;
	@Param(parameterName = PARAM_M_LU_HU_PI_ID)
	private int p_M_LU_HU_PI_ID;
	@Param(parameterName = PARAM_QtyCU)
	private BigDecimal p_QtyCU;
	@Param(parameterName = PARAM_QtyTU)
	private BigDecimal p_QtyTU;
	@Param(parameterName = PARAM_QtyLU)
	private BigDecimal p_QtyLU;

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		switch (parameter.getColumnName())
		{
			case PARAM_M_HU_PI_Item_Product_ID:
				return getDefaultLUTUConfiguration().getM_HU_PI_Item_Product_ID();
			case PARAM_M_LU_HU_PI_ID:
				return getDefaultLUTUConfiguration().getM_LU_HU_PI_ID();
			case PARAM_QtyCU:
				return getDefaultLUTUConfiguration().getQtyCU();
			case PARAM_QtyTU:
				return getDefaultLUTUConfiguration().getQtyTU();
			case PARAM_QtyLU:
				return getDefaultLUTUConfiguration().getQtyLU();
			default:
				return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule();

		if (receiptSchedule == null)
		{
			return ProcessPreconditionsResolution.reject("not applying for HUs without receipt schedule");
		}

		return ProcessPreconditionsResolution.accept();

	}

	private I_M_HU_LUTU_Configuration getDefaultLUTUConfiguration()
	{
		if (_defaultLUTUConfiguration == null)
		{
			final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule();
			final I_M_HU_LUTU_Configuration defaultLUTUConfiguration = huReceiptScheduleBL.getCurrentLUTUConfiguration(receiptSchedule);
			huReceiptScheduleBL.adjustLUTUConfiguration(defaultLUTUConfiguration, receiptSchedule);
			_defaultLUTUConfiguration = defaultLUTUConfiguration;
		}
		return _defaultLUTUConfiguration;
	}

	private I_M_ReceiptSchedule getM_ReceiptSchedule()
	{

		return getView()
				.getReferencingDocumentPaths().stream()
				.map(referencingDocumentPath -> getReceiptSchedule(referencingDocumentPath))
				.collect(GuavaCollectors.toImmutableList())
				.stream().findFirst().orElse(null); // TODO is this ok ?
	}

	private I_M_ReceiptSchedule getReceiptSchedule(@NonNull final DocumentPath referencingDocumentPath)
	{
		return documentsCollection
				.getTableRecordReference(referencingDocumentPath)
				.getModel(this, I_M_ReceiptSchedule.class);
	}

	protected boolean isUpdateReceiptScheduleDefaultConfiguration()
	{
		return p_IsSaveLUTUConfiguration;
	}

	private I_M_HU_LUTU_Configuration createM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration template)
	{
		// Validate parameters
		final int M_LU_HU_PI_ID = p_M_LU_HU_PI_ID;
		final int M_HU_PI_Item_Product_ID = p_M_HU_PI_Item_Product_ID;
		final BigDecimal qtyCU = p_QtyCU;
		final BigDecimal qtyTU = p_QtyTU;
		final BigDecimal qtyLU = p_QtyLU;
		if (M_HU_PI_Item_Product_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_M_HU_PI_Item_Product_ID);
		}
		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCU);
		}
		if (qtyTU == null || qtyTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyTU);
		}
		if (M_LU_HU_PI_ID > 0 && qtyLU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyLU);
		}

		final ILUTUConfigurationFactory.CreateLUTUConfigRequest lutuConfigRequest = ILUTUConfigurationFactory.CreateLUTUConfigRequest.builder()
				.baseLUTUConfiguration(template)
				.qtyLU(qtyLU)
				.qtyTU(qtyTU)
				.qtyCU(qtyCU)
				.tuHUPIItemProductID(M_HU_PI_Item_Product_ID)
				.luHUPIID(M_LU_HU_PI_ID)
				.build();

		return lutuConfigurationFactory.createNewLUTUConfigWithParams(lutuConfigRequest);
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule();

		//
		// Get/Create the initial LU/TU configuration
		final I_M_HU_LUTU_Configuration lutuConfigurationOrig = huReceiptScheduleBL.getCurrentLUTUConfiguration(receiptSchedule);
		final I_M_HU_LUTU_Configuration lutuConfiguration = createM_HU_LUTU_Configuration(lutuConfigurationOrig);


		final IMutableHUContext huContextInitial = huContextFactory.createMutableHUContextForProcessing(getCtx(), ClientAndOrgId.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID()));

		final List<I_M_HU> hus = huReceiptScheduleBL.createPlanningHUs(receiptSchedule, lutuConfiguration, huContextInitial,
																	   isUpdateReceiptScheduleDefaultConfiguration(), false);

		final HUEditorView view = getView();

		hus.forEach(hu -> {
			updateAttributes(hu, receiptSchedule);
			view.addHUId(HuId.ofRepoId(hu.getM_HU_ID()));
		});

		return MSG_OK;
	}

	private void updateAttributes(@NonNull final I_M_HU hu, @NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		// TODO
		final IAttributeStorage huAttributes = attributeStorageFactory.getAttributeStorage(hu);
		// setAttributeLotNumber(hu, receiptSchedule, huAttributes); TODO
		//setAttributeBBD(receiptSchedule, huAttributes);
		//setVendorValueFromReceiptSchedule(receiptSchedule, huAttributes);
	}

	// private void setAttributeBBD(@NonNull final I_M_ReceiptSchedule receiptSchedule, @NonNull final IAttributeStorage huAttributes)
	// {
	// 	if (huAttributes.hasAttribute(AttributeConstants.ATTR_BestBeforeDate)
	// 			&& huAttributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate) == null
	// 			&& huAttributesBL.isAutomaticallySetBestBeforeDate()
	// 	)
	// 	{
	// 		final LocalDate bestBeforeDate = computeBestBeforeDate(ProductId.ofRepoId(receiptSchedule.getM_Product_ID()), TimeUtil.asLocalDate(receiptSchedule.getMovementDate()));
	// 		if (bestBeforeDate != null)
	// 		{
	// 			huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
	// 			huAttributes.saveChangesIfNeeded();
	// 		}
	// 	}
	// }
	//
	// private void setVendorValueFromReceiptSchedule(@NonNull final I_M_ReceiptSchedule receiptSchedule, @NonNull final IAttributeStorage huAttributes)
	// {
	// 	if (huAttributes.hasAttribute(AttributeConstants.ATTR_Vendor_BPartner_ID)
	// 			&& huAttributes.getValueAsInt(AttributeConstants.ATTR_Vendor_BPartner_ID) > -1)
	// 	{
	// 		final int bpId = receiptSchedule.getC_BPartner_ID();
	// 		if (bpId > 0)
	// 		{
	// 			huAttributes.setValue(AttributeConstants.ATTR_Vendor_BPartner_ID, bpId);
	// 			huAttributes.setSaveOnChange(true);
	// 			huAttributes.saveChangesIfNeeded();
	// 		}
	// 	}
	// }
	//
	// @Nullable
	// LocalDate computeBestBeforeDate(@NonNull final ProductId productId, final @NonNull LocalDate datePromised)
	// {
	// 	final int guaranteeDaysMin = productDAO.getProductGuaranteeDaysMinFallbackProductCategory(productId);
	//
	// 	if (guaranteeDaysMin <= 0)
	// 	{
	// 		return null;
	// 	}
	//
	// 	return datePromised.plusDays(guaranteeDaysMin);
	// }

}
