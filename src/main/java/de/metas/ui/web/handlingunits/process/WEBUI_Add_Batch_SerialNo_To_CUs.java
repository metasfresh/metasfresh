package de.metas.ui.web.handlingunits.process;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.ISerialNoDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
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
	private static transient IHandlingUnitsDAO huDAO = Services.get(IHandlingUnitsDAO.class);

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

		final DocumentIdsSelection selectedRowIds = getSelectedDocumentIds();
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
	protected String doIt() throws Exception
	{

		final String serialNumbersString = replaceAllSeparatorsWithComma(p_SerialNo);
		serialNumbers = splitIntoSerialNumbers(serialNumbersString);

		final ImmutableList<HUEditorRow> nonAggregatedCURows = streamSelectedRows(HUEditorRowFilter.select(Select.ALL)).filter(huRow -> !isAggregateHU(huRow)).collect(ImmutableList.toImmutableList());

		for (HUEditorRow nonAggregatedCURow : nonAggregatedCURows)
		{
			final int qtyCU = nonAggregatedCURow.getQtyCU().intValueExact();

			if (qtyCU == 1)
			{
				assignSerialNoToCU(nonAggregatedCURow.getM_HU(), serialNumbers.get(0));
				serialNumbers.remove(0);
			}
			else
			{
				final int serialNoCount = serialNumbers.size();
				final int cusToCreateNo = qtyCU < serialNoCount ? qtyCU : serialNoCount;

				final List<I_M_HU> createdCus = createNewCus(nonAggregatedCURow, cusToCreateNo);
				assignSerialNumbersToCUs(createdCus);

			}
		}
		// createNewCus(cuRow.getQtyCU());
		//
		// final int nonAggregatedCUsNo = nonAggregatedCUs.size();
		//
		final ImmutableList<HUEditorRow> aggregatedCURows = streamSelectedRows(HUEditorRowFilter.select(Select.ALL)).filter(huRow -> isAggregateHU(huRow)).collect(ImmutableList.toImmutableList());
		
		for (HUEditorRow aggregatedCURow : aggregatedCURows)
		{
			final int qtyCU = aggregatedCURow.getQtyCU().intValueExact();

			if (qtyCU == 1)
			{
				assignSerialNoToCU(aggregatedCURow.getM_HU(), serialNumbers.get(0));
				serialNumbers.remove(0);
			}
			else
			{
				final int serialNoCount = serialNumbers.size();
				final int cusToCreateNo = qtyCU < serialNoCount ? qtyCU : serialNoCount;

				final List<I_M_HU> createdCus = createNewCus(aggregatedCURow, cusToCreateNo);
				assignSerialNumbersToCUs(createdCus);

			}
		}
	
		return MSG_OK;

	}

	private List<I_M_HU> createNewCus(final HUEditorRow cuRow, final int cuNumber)
	{
		if(cuNumber == 0)
		{
			return Collections.emptyList();
		}

		final List<I_M_HU> createdCUs = new ArrayList<>();

		for (int i = 0; i < cuNumber; i++)
		{
			final I_M_HU_Item parentTU = huDAO.retrieveParentItem(cuRow.getM_HU());

			final WebuiHUTransformParameters parameters;

			if (parentTU == null)
			{
				parameters = WebuiHUTransformParameters.builder()
						.actionType(ActionType.CU_To_NewCU)
						.qtyCU(BigDecimal.ONE)
						.build();
			}
			else
			{
				parameters = WebuiHUTransformParameters.builder()
						.actionType(ActionType.CU_To_ExistingTU)
						.qtyCU(BigDecimal.ONE)
						.tuHU(parentTU.getM_HU())
						.build();

			}

			final WebuiHUTransformCommand command = WebuiHUTransformCommand.builder()
					.selectedRow(cuRow)
					.parameters(parameters)
					.build();

			final WebuiHUTransformCommandResult result = command.execute();

			final ImmutableSet<Integer> huIdsToAddToView = result.getHuIdsToAddToView();

			for (int huId : huIdsToAddToView)
			{
				final I_M_HU cu = create(Env.getCtx(), huId, I_M_HU.class, ITrx.TRXNAME_ThreadInherited);

				createdCUs.add(cu);
			}
		}

		return createdCUs;

	}

	private boolean isAggregateHU(final HUEditorRow huRow)
	{
		final I_M_HU hu = huRow.getM_HU();

		return huBL.isAggregateHU(hu);
	}

	private void assignSerialNumbersToCUs(final List<I_M_HU> createdCus)
	{
		final int numberOfCUs = createdCus.size();

		for (int i = 0; i < numberOfCUs; i++)
		{
			assignSerialNoToCU(createdCus.get(i), serialNumbers.get(0));
			serialNumbers.remove(0);
		}

	}

	private void assignSerialNoToCU(final I_M_HU hu, final String serialNo)
	{
		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(hu);

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

		return new LinkedList<String>(Arrays.asList(split));
	}

}
