
package de.metas.ui.web.handlingunits.process;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.ISerialNoDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;

import com.google.common.base.Splitter;
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
import de.metas.ui.web.handlingunits.HUEditorRowId;
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
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final ISerialNoDAO serialNoDAO = Services.get(ISerialNoDAO.class);
	
	private static final String SERIALNO_SEPARATOR = ",";

	private static final String PARAM_SerialNo = "SerialNo";
	@Param(parameterName = PARAM_SerialNo, mandatory = true)
	private String p_SerialNo;

	ArrayList<String> _serialNumbers;

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
		orderSelectedCUs().forEach(this::processCURow);

		return MSG_OK;
	}

	private ArrayList<String> getSerialNumbers()
	{
		if (_serialNumbers == null)
		{
			_serialNumbers = parseSerialNumbers();
		}
		return _serialNumbers;

	}

	private ArrayList<String> parseSerialNumbers()
	{
		final String serialNumbersNorm = p_SerialNo
				.replace("\n", SERIALNO_SEPARATOR)
				.replace(";", SERIALNO_SEPARATOR);

		final List<String> serialNosList = Splitter.on(SERIALNO_SEPARATOR)
				.trimResults()
				.omitEmptyStrings()
				.splitToList(serialNumbersNorm);

		return new ArrayList<>(serialNosList);
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

		return huRow.isHUStatusPlanning() || huRow.isHUStatusActive();

	}

	private ImmutableList<HUEditorRow> orderSelectedCUs()
	{
		return streamSelectedRows(HUEditorRowFilter.select(Select.ALL))
				.sorted(new Comparator<HUEditorRow>()
				{
					@Override
					public int compare(final HUEditorRow o1, final HUEditorRow o2)
					{
						if (!isAggregateHU(o1))
						{
							return -1;
						}
						if (!isAggregateHU(o2))
						{
							return 1;
						}
						return 0;
					}
				}).collect(ImmutableList.toImmutableList());
	}

	private void processCURow(final HUEditorRow selectedCuRow)
	{
		final ArrayList<String> availableSerialNumbers = getSerialNumbers();
		if (availableSerialNumbers.isEmpty())
		{
			return;
		}

		final int qtyCU = selectedCuRow.getQtyCU().intValueExact();
		if (qtyCU == 1)
		{
			final String serialNo = availableSerialNumbers.remove(0);
			assignSerialNumberToCU(selectedCuRow.getM_HU(), serialNo);
		}
		else
		{
			final int serialNoCount = availableSerialNumbers.size();
			final int cusToCreateCount = qtyCU < serialNoCount ? qtyCU : serialNoCount;

			final List<I_M_HU> splittedCUs = splitIntoCUs(selectedCuRow, cusToCreateCount);
			assignSerialNumbersToCUs(splittedCUs, availableSerialNumbers);
		}
	}

	private List<I_M_HU> splitIntoCUs(final HUEditorRow cuRow, final int cuNumber)
	{
		if (cuNumber == 0)
		{
			return ImmutableList.of();
		}

		final List<I_M_HU> createdCUs = new ArrayList<>();
		while (cuNumber > createdCUs.size())
		{
			final int numberOfCUsStillToBeCreated = cuNumber - createdCUs.size();
			createdCUs.addAll(createCUsBatch(cuRow, numberOfCUsStillToBeCreated));
		}

		final ImmutableList<Integer> huIDsToAdd = createdCUs.stream().map(hu -> hu.getM_HU_ID()).collect(ImmutableList.toImmutableList());
		getView().addHUIdsAndInvalidate(huIDsToAdd);

		return createdCUs;
	}

	private List<I_M_HU> createCUsBatch(final HUEditorRow cuRow, final int numberOfCUsStillToBeCreated)
	{
		final HUEditorRow parentRow = getParentHURow(cuRow);
		final WebuiHUTransformParameters huTransformParams;
		HUEditorRow cuRowToSplit = cuRow;
		if (parentRow == null)
		{
			huTransformParams = WebuiHUTransformParameters.builder()
					.actionType(ActionType.CU_To_NewCU)
					.qtyCU(BigDecimal.ONE)
					.build();
		}
		else
		{
			Check.assume(parentRow.isTU(), " Parent row must be TU: " + parentRow);

			final I_M_HU parentHU;

			if (isAggregateHU(parentRow))
			{
				parentHU = createNonAggregatedTU(parentRow);
				cuRowToSplit = getIncludedCURow(parentHU);
			}
			else
			{
				parentHU = parentRow.getM_HU();
			}

			huTransformParams = WebuiHUTransformParameters.builder()
					.actionType(ActionType.CU_To_ExistingTU)
					.tuHU(parentHU)
					.qtyCU(BigDecimal.ONE)
					.build();
		}

		final int qtyCU = cuRowToSplit.getQtyCU().intValueExact();
		final int numberOfCUsToCreate = numberOfCUsStillToBeCreated < qtyCU ? numberOfCUsStillToBeCreated : qtyCU;
		final List<I_M_HU> createdCUs = new ArrayList<>();
		for (int i = 0; i < numberOfCUsToCreate; i++)
		{
			final I_M_HU newCU = createNewCU(cuRowToSplit, huTransformParams);
			createdCUs.add(newCU);
		}
		return createdCUs;
	}

	private I_M_HU createNonAggregatedTU(final HUEditorRow tuRow)
	{
		final HUEditorRow luRow = getParentHURow(tuRow);

		final WebuiHUTransformParameters parentParameters = WebuiHUTransformParameters.builder()
				.actionType(ActionType.TU_To_NewTUs)
				.qtyTU(BigDecimal.ONE)
				.tuHU(tuRow.getM_HU())

				.build();

		final WebuiHUTransformCommand command = WebuiHUTransformCommand.builder()
				.selectedRow(tuRow)
				.parameters(parentParameters)
				.build();

		final WebuiHUTransformCommandResult resultValue = trxManager.call(() -> command.execute());

		final ImmutableSet<Integer> huIdsToAddToView = resultValue.getHuIdsToAddToView();

		getView().addHUIdsAndInvalidate(huIdsToAddToView);

		Check.assume(huIdsToAddToView.size() == 1, "Only one non-aggregated TU shall be created");

		if (luRow != null)
		{
			moveTUToLU(huIdsToAddToView.asList().get(0), luRow);
		}

		final I_M_HU newParentHU = create(Env.getCtx(), huIdsToAddToView.asList().get(0), I_M_HU.class, ITrx.TRXNAME_ThreadInherited);

		return newParentHU;

	}

	private void moveTUToLU(final int tuId, final HUEditorRow luRow)
	{

		final I_M_HU newTU = create(Env.getCtx(), tuId, I_M_HU.class, ITrx.TRXNAME_ThreadInherited);

		final WebuiHUTransformParameters tuToLUParameters = WebuiHUTransformParameters.builder()
				.actionType(ActionType.TU_To_ExistingLU)
				.qtyTU(BigDecimal.ONE)
				.tuHU(newTU)
				.luHU(luRow.getM_HU())
				.build();

		final HUEditorRowId newTURowId = HUEditorRowId.ofHU(tuId, -1);

		final HUEditorRow newTURow = getView().getById(newTURowId.toDocumentId());

		final WebuiHUTransformCommand tuToLUcommand = WebuiHUTransformCommand.builder()
				.selectedRow(newTURow)
				.parameters(tuToLUParameters)
				.build();

		trxManager.call(() -> tuToLUcommand.execute());

		getView().invalidateAll();

	}

	private HUEditorRow getIncludedCURow(final I_M_HU parentHU)
	{
		final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(parentHU);
		final HUEditorRowId newTURowId = HUEditorRowId.ofHU(parentHU.getM_HU_ID(), topLevelParent == null ? -1 : topLevelParent.getM_HU_ID());

		final HUEditorRow newTURow = getView().getById(newTURowId.toDocumentId());

		final List<HUEditorRow> newCURow = newTURow.getIncludedRows();
		Check.assume(newCURow.size() == 1, " There should be only one CU line included in " + newTURow);

		return newCURow.get(0);
	}

	private I_M_HU createNewCU(final HUEditorRow cuRowToUse, final WebuiHUTransformParameters parameters)
	{
		final WebuiHUTransformCommand command = WebuiHUTransformCommand.builder()
				.selectedRow(cuRowToUse)
				.parameters(parameters)
				.build();

		final WebuiHUTransformCommandResult resultValue = trxManager.call(() -> command.execute());

		final ImmutableSet<Integer> huIdsToAddToView = resultValue.getHuIdsCreated();

		Check.assume(huIdsToAddToView.size() <= 1, "Only one or no CU should be created");

		if (Check.isEmpty(huIdsToAddToView))
		{
			return cuRowToUse.getM_HU();
		}

		final int huId = huIdsToAddToView.asList().get(0);
		final I_M_HU cu = create(Env.getCtx(), huId, I_M_HU.class, ITrx.TRXNAME_ThreadInherited);

		return cu;
	}

	private void assignSerialNumbersToCUs(final List<I_M_HU> cus, final List<String> availableSerialNumbers)
	{
		final int numberOfCUs = cus.size();

		for (int i = 0; i < numberOfCUs; i++)
		{
			if (availableSerialNumbers.isEmpty())
			{
				return;
			}

			assignSerialNumberToCU(cus.get(i), availableSerialNumbers.remove(0));
		}
	}

	private void assignSerialNumberToCU(final I_M_HU hu, final String serialNo)
	{
		final IContextAware ctxAware = getContextAware(hu);

		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);

		final IAttributeStorage attributeStorage = getAttributeStorage(huContext, hu);

		final I_M_Attribute serialNoAttribute = serialNoDAO.getSerialNoAttribute(ctxAware.getCtx());

		Check.assume(attributeStorage.hasAttribute(serialNoAttribute), "There is no SerialNo attribute defined for the handling unit" + hu);

		attributeStorage.setValue(serialNoAttribute, serialNo.trim());
		attributeStorage.saveChangesIfNeeded();

	}

	private HUEditorRow getParentHURow(final HUEditorRow cuRow)
	{
		return getView().getParentRowByChildIdOrNull(cuRow.getId());
	}

	private boolean isAggregateHU(final HUEditorRow huRow)
	{
		final I_M_HU hu = huRow.getM_HU();
		return handlingUnitsBL.isAggregateHU(hu);
	}

	private final IAttributeStorage getAttributeStorage(final IHUContext huContext, final I_M_HU hu)
	{
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu);
		return attributeStorage;
	}

}
