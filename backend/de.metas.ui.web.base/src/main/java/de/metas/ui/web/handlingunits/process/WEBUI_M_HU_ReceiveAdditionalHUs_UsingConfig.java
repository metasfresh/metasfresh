package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.CreatePlanningHUsRequest;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
public class WEBUI_M_HU_ReceiveAdditionalHUs_UsingConfig extends HUEditorProcessTemplate implements IProcessDefaultParametersProvider
{
	private static final String PARAM_IsSaveLUTUConfiguration = "IsSaveLUTUConfiguration";
	//
	// Parameters
	private static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	//
	private static final String PARAM_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";
	//
	private static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	//
	private static final String PARAM_QtyTU = "QtyTU";
	//
	private static final String PARAM_QtyLU = "QtyLU";
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final DocumentCollection documentsCollection = SpringContextHolder.instance.getBean(DocumentCollection.class);
	private I_M_HU_LUTU_Configuration _defaultLUTUConfiguration; // lazy
	@Param(parameterName = PARAM_IsSaveLUTUConfiguration)
	private boolean p_IsSaveLUTUConfiguration;
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private int p_M_HU_PI_Item_Product_ID;
	@Param(parameterName = PARAM_M_LU_HU_PI_ID)
	private int p_M_LU_HU_PI_ID;
	@Param(parameterName = PARAM_QtyCUsPerTU)
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
			case PARAM_QtyCUsPerTU:
				return getDefaultLUTUConfiguration().getQtyCUsPerTU();
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
		final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule().orElse(null);
		if (receiptSchedule == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not applying for HUs without receipt schedule");
		}

		return ProcessPreconditionsResolution.accept();

	}

	private I_M_HU_LUTU_Configuration getDefaultLUTUConfiguration()
	{
		if (_defaultLUTUConfiguration == null)
		{
			final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule().orElseThrow(() -> new AdempiereException("No receipt schedule found"));
			final I_M_HU_LUTU_Configuration defaultLUTUConfiguration = huReceiptScheduleBL.getCurrentLUTUConfiguration(receiptSchedule);
			huReceiptScheduleBL.adjustLUTUConfiguration(defaultLUTUConfiguration, receiptSchedule);
			_defaultLUTUConfiguration = defaultLUTUConfiguration;
		}
		return _defaultLUTUConfiguration;
	}

	private Optional<I_M_ReceiptSchedule> getM_ReceiptSchedule()
	{
		return getView()
				.getReferencingDocumentPaths().stream()
				.map(this::getReceiptSchedule)
				.findFirst();
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

	private I_M_HU_LUTU_Configuration createLUTUConfiguration(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_M_HU_LUTU_Configuration lutuConfigurationOrig = huReceiptScheduleBL.getCurrentLUTUConfiguration(receiptSchedule);
		final I_M_HU_LUTU_Configuration lutuConfiguration = createLUTUConfigurationFromTemplate(lutuConfigurationOrig);
		lutuConfigurationFactory.save(lutuConfiguration);
		return lutuConfiguration;
	}

	private I_M_HU_LUTU_Configuration createLUTUConfigurationFromTemplate(final I_M_HU_LUTU_Configuration template)
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
			throw new FillMandatoryException(PARAM_QtyCUsPerTU);
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
				.qtyCUsPerTU(qtyCU)
				.tuHUPIItemProductID(M_HU_PI_Item_Product_ID)
				.luHUPIID(M_LU_HU_PI_ID)
				.build();

		return lutuConfigurationFactory.createNewLUTUConfigWithParams(lutuConfigRequest);
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule().orElseThrow(() -> new AdempiereException("No receipt schedule found"));

		final List<I_M_HU> hus = huReceiptScheduleBL.createPlanningHUs(
				CreatePlanningHUsRequest.builder()
						.lutuConfiguration(createLUTUConfiguration(receiptSchedule))
						.receiptSchedule(receiptSchedule)
						.isUpdateReceiptScheduleDefaultConfiguration(isUpdateReceiptScheduleDefaultConfiguration())
						.isDestroyExistingHUs(false)
						.lotNumber(getLotNumber().orElse(null))
						.build());

		addToCurrentView(hus);

		return MSG_OK;
	}

	private Optional<String> getLotNumber()
	{
		return getView().streamByIds(HUEditorRowFilter.ALL)
				.map(row -> row.getAttributes().getValueAsString(AttributeConstants.ATTR_LotNumber))
				.findAny();
	}

	private void addToCurrentView(final List<I_M_HU> hus)
	{
		final ImmutableSet<HuId> huIds = hus.stream().map(hu -> HuId.ofRepoId(hu.getM_HU_ID())).collect(ImmutableSet.toImmutableSet());
		getView().addHUIds(huIds);
	}
}
