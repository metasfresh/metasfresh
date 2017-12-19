
package de.metas.ui.web.handlingunits.process;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.api.ISerialNoDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.handlingunits.process.WebuiHUTransformCommand.ActionType;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_Add_Batch_SerialNo_To_CUs extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private static transient IHandlingUnitsBL huBL = Services.get(IHandlingUnitsBL.class);

	private static final String PARAM_SerialNo = "SerialNo";
	@Param(parameterName = PARAM_SerialNo, mandatory = true)
	private String p_SerialNo;

	List<String> serialNumbers = Collections.emptyList();

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!streamSelectedRows(HUEditorRowFilter.select(Select.ALL)).allMatch(huRow -> isEligibleHuRow(huRow)))
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_CU));
		}

		return ProcessPreconditionsResolution.accept();

	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{

		final String serialNumbersString = replaceAllSeparatorsWithComma(p_SerialNo);
		serialNumbers = splitIntoSerialNumbers(serialNumbersString);

		final ImmutableList<HUEditorRow> selectedCuRows = streamSelectedRows(HUEditorRowFilter.select(Select.ALL))
				.sorted(new Comparator<HUEditorRow>()
				{
					@Override
					public int compare(final HUEditorRow o1, final HUEditorRow o2)
					{
						return !isAggregateHU(o1) ? -1 : 1;
					}
				}).collect(ImmutableList.toImmutableList());

		// filter(huRow -> !isAggregateHU(huRow)).sorted(!isAggregateHU(huRow))collect(ImmutableList.toImmutableList());

		for (HUEditorRow selectedCuRow : selectedCuRows)
		{

			final int qtyCU = selectedCuRow.getQtyCU().intValueExact();

			if (qtyCU == 1)
			{
				assignSerialNumberToCU(selectedCuRow.getM_HU(), serialNumbers.get(0));
				serialNumbers.remove(0);
			}
			else
			{
				final int serialNoCount = serialNumbers.size();
				final int cusToCreateNo = qtyCU < serialNoCount ? qtyCU : serialNoCount;

				final List<I_M_HU> createdCus = createNewCus(selectedCuRow, cusToCreateNo);
				assignSerialNumbersToCUs(createdCus);

			}
		}

		return MSG_OK;

	}

	@Override
	protected void postProcess(boolean success)
	{
		if (!success)
		{
			return;
		}

		invalidateView();
	}

	private List<I_M_HU> createNewCus(final HUEditorRow cuRow, final int cuNumber)
	{
		if (cuNumber == 0)
		{
			return Collections.emptyList();
		}

		final HUEditorRow parentRow = getParentHURow(cuRow);

		final List<I_M_HU> createdCUs = new ArrayList<>();

		for (int i = 0; i < cuNumber; i++)
		{

			final WebuiHUTransformParameters parameters;

			if (parentRow == null)
			{
				parameters = WebuiHUTransformParameters.builder()
						.actionType(ActionType.CU_To_NewCU)
						.qtyCU(BigDecimal.ONE)
						.build();
			}
			else
			{
				final I_M_HU parentHU;

				if (isAggregateHU(parentRow))
				{
					parentHU = createNonAggregatedTU(parentRow);

				}
				else
				{
					parentHU = parentRow.getM_HU();
				}

				parameters = WebuiHUTransformParameters.builder()
						.actionType(ActionType.CU_To_ExistingTU)
						.tuHU(parentHU)
						.qtyCU(BigDecimal.ONE)
						.build();

			}

			final WebuiHUTransformCommand command = WebuiHUTransformCommand.builder()
					.selectedRow(cuRow)
					.parameters(parameters)
					.build();

			final IMutable<WebuiHUTransformCommandResult> result = new Mutable<>();
			
			Services.get(ITrxManager.class).run(() -> {
				result.setValue(command.execute());
			});

			ImmutableSet<Integer> huIdsToAddToView = result.getValue().getHuIdsToAddToView();

			for (int huId : huIdsToAddToView)
			{
				final I_M_HU cu = create(Env.getCtx(), huId, I_M_HU.class, ITrx.TRXNAME_ThreadInherited);

				createdCUs.add(cu);
			}
		}

		return createdCUs;

	}

	private I_M_HU createNonAggregatedTU(final HUEditorRow tuRow)
	{

		final WebuiHUTransformParameters parentParameters = WebuiHUTransformParameters.builder()
				.actionType(ActionType.TU_To_NewTUs)
				.qtyTU(BigDecimal.ONE) 
				.tuHU(tuRow.getM_HU())
				.build();

		final WebuiHUTransformCommand command = WebuiHUTransformCommand.builder()
				.selectedRow(tuRow)
				.parameters(parentParameters)
				.build();

		final IMutable<WebuiHUTransformCommandResult> result = new Mutable<>();
		
		Services.get(ITrxManager.class).run(() -> {
			result.setValue(command.execute());
		});

		ImmutableSet<Integer> huIdsToAddToView = result.getValue().getHuIdsToAddToView();

		final I_M_HU newParentHU = create(Env.getCtx(), huIdsToAddToView.asList().get(0), I_M_HU.class, ITrx.TRXNAME_ThreadInherited);

		return newParentHU;

	}

	private HUEditorRow getParentHURow(final HUEditorRow cuRow)
	{
		return getView().getParentRowByChildId(cuRow.getId());
	}

	private boolean isAggregateHU(final HUEditorRow huRow)
	{
		final I_M_HU hu = huRow.getM_HU();

		return huBL.isAggregateHU(hu);
	}

	private void assignSerialNumbersToCUs(final List<I_M_HU> cus)
	{
		final int numberOfCUs = cus.size();

		for (int i = 0; i < numberOfCUs; i++)
		{
			if (Check.isEmpty(serialNumbers))
			{
				return;
			}

			assignSerialNumberToCU(cus.get(i), serialNumbers.get(0));
			serialNumbers.remove(0);
		}
	}

	private void assignSerialNumberToCU(final I_M_HU hu, final String serialNo)
	{
		final IContextAware ctxAware = getContextAware(hu);

		final IHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(ctxAware);

		final IAttributeStorage attributeStorage = getAttributeStorage(huContext, hu);

		final I_M_Attribute serialNoAttribute = Services.get(ISerialNoDAO.class).getSerialNoAttribute(ctxAware.getCtx());

		Check.assume(attributeStorage.hasAttribute(serialNoAttribute), "There is no SerialNo attribute defined for the handling unit" + hu);

		attributeStorage.setValue(serialNoAttribute, serialNo.trim());
		attributeStorage.saveChangesIfNeeded();

	}

	private final IAttributeStorage getAttributeStorage(final IHUContext huContext, final I_M_HU hu)
	{
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu);
		return attributeStorage;
	}

	private boolean isEligibleHuRow(final HUEditorRow huRow)
	{
		if (!huRow.isCU())
		{
			return false;
		}

		if (huRow.getC_UOM_ID() != IUOMDAO.C_UOM_ID_Each)
		{
			return false;
		}

		if (huRow.isHUStatusPlanning() || huRow.isHUStatusActive())
		{
			return true;
		}

		return false;
	}

	final String replaceAllSeparatorsWithComma(final String originalString)
	{
		return originalString.replaceAll("\\t", ",")
				.replaceAll(";", ",");
	}

	final List<String> splitIntoSerialNumbers(final String originalString)
	{
		final String[] split = originalString.split(",");

		return new LinkedList<>(Arrays.asList(split));
	}

}
