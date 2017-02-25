package de.metas.ui.web.handlingunits.process;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.transfer.IHUSplitBuilder;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilder;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.WebRestApiApplication;
import de.metas.ui.web.handlingunits.HUDocumentView;
import de.metas.ui.web.handlingunits.HUDocumentViewSelection;
import de.metas.ui.web.process.ProcessInstance;
import de.metas.ui.web.view.IDocumentViewsRepository;
import de.metas.ui.web.window.datatypes.DocumentId;

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

@Profile(value = WebRestApiApplication.PROFILE_Webui)
public class WEBUI_M_HU_Transform extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return DEFAULT_VALUE_NOTAVAILABLE;
	}

	//
	// Services
	@Autowired
	private transient IDocumentViewsRepository documentViewsRepo;
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	//
	// View (internal) parameters
	@Param(parameterName = ProcessInstance.PARAM_ViewId, mandatory = true)
	private String p_WebuiViewId;
	@Param(parameterName = ProcessInstance.PARAM_ViewSelectedIds, mandatory = true)
	private String p_WebuiViewSelectedIdsStr;

	//
	// Parameters
	private static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private int p_M_HU_PI_Item_Product_ID;
	//
	private static final String PARAM_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";
	@Param(parameterName = PARAM_M_LU_HU_PI_ID)
	private int p_M_LU_HU_PI_ID;
	//
	private static final String PARAM_QtyCU = "QtyCU";
	@Param(parameterName = PARAM_QtyCU)
	private BigDecimal p_QtyCU;
	//
	private static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU)
	private BigDecimal p_QtyTU;
	
	// TODO: handle HUPlanningReceiptOwnerPM

	public WEBUI_M_HU_Transform()
	{
		Adempiere.autowire(this);
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final HUDocumentView cuRow = getSelectedRow();

		actionSplitCUToNew(cuRow);

		return MSG_OK;
	}

	private HUDocumentViewSelection getView()
	{
		return documentViewsRepo.getView(p_WebuiViewId, HUDocumentViewSelection.class);
	}

	private HUDocumentView getSelectedRow()
	{
		final Set<DocumentId> selectedDocumentIds = DocumentId.ofCommaSeparatedString(p_WebuiViewSelectedIdsStr);
		final DocumentId documentId = ListUtils.singleElement(selectedDocumentIds);
		return getView().getById(documentId);
	}

	private void actionSplitCUToNew(final HUDocumentView cuRow)
	{
		Check.assume(cuRow.isCU(), "CU Row: {}", cuRow);

		final IMutableHUContext huContextInitial = handlingUnitsBL.createMutableHUContextForProcessing(this);
		final IHUSplitBuilder splitBuilder = new HUSplitBuilder(huContextInitial);

		//
		// "Our" HU, the one which the user selected for split
		final I_M_HU huToSplit = InterfaceWrapperHelper.create(getCtx(), cuRow.getM_HU_ID(), I_M_HU.class, ITrx.TRXNAME_ThreadInherited);
		splitBuilder.setHUToSplit(huToSplit);

		//
		// DocumentLine / Trx Referenced model (if available)
		final IHUDocumentLine documentLine = null; // TODO huToSplitKey.findDocumentLineOrNull();
		splitBuilder.setDocumentLine(documentLine);
		splitBuilder.setCUTrxReferencedModel(documentLine == null ? null : documentLine.getTrxReferencedModel());

		splitBuilder.setCUProduct(InterfaceWrapperHelper.create(getCtx(), cuRow.getM_Product_ID(), I_M_Product.class, ITrx.TRXNAME_None));
		splitBuilder.setCUQty(cuRow.getQtyCU());
		splitBuilder.setCUUOM(InterfaceWrapperHelper.create(getCtx(), cuRow.getC_UOM_ID(), I_C_UOM.class, ITrx.TRXNAME_None));

		splitBuilder.setCUPerTU(p_QtyCU);
		splitBuilder.setTUPerLU(p_QtyTU);
		splitBuilder.setMaxLUToAllocate(BigDecimal.valueOf(Integer.MAX_VALUE));

		final I_M_HU_PI_Item_Product tuPI_ItemProduct = InterfaceWrapperHelper.create(getCtx(), p_M_HU_PI_Item_Product_ID, I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
		final I_M_HU_PI_Item tuPI_Item = tuPI_ItemProduct.getM_HU_PI_Item();
		splitBuilder.setTU_M_HU_PI_Item(tuPI_Item);

		// LU
		final I_M_HU_PI tuPI = tuPI_Item.getM_HU_PI_Version().getM_HU_PI();
		final I_C_BPartner bpartner = huToSplit.getC_BPartner();
		final I_M_HU_PI_Item luPI_Item = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, bpartner)
				.stream()
				.filter(piItem -> piItem.getM_HU_PI_Version().getM_HU_PI_ID() == p_M_LU_HU_PI_ID)
				.findFirst()
				.orElseThrow(() -> new AdempiereException(tuPI.getName() + " cannot be loaded to " + p_M_LU_HU_PI_ID));
		splitBuilder.setLU_M_HU_PI_Item(luPI_Item);

		final List<I_M_HU> husAfterSplit = splitBuilder.split();
		getView().addHUsAndInvalidate(husAfterSplit);
	}

	
	// Params:
	// * QtyCU to split
	private void action_SplitCU_To_NewCU()
	{
		
	}
	
	// Params:
	// * Existing TU (M_HU_ID)
	// * QtyCU to split
	private void action_SplitCU_To_ExistingTU()
	{
		
	}

	// Params:
	// * TU's M_HU_PI_Item_Product_ID
	// * total QtyCU to split
	// * HUPlanningReceiptOwnerPM
	private void action_SplitCU_To_NewTUs()
	{
		
	}
	
	// Params:
	// * TU's M_HU_PI_Item_Product_ID
	// * QtyTUs
	// * HUPlanningReceiptOwnerPM
	private void action_SplitTU_To_NewTUs()
	{
		
	}
	
	// Params:
	// * TU's M_HU_PI_Item_Product_ID
	// * LU's M_HU_PI_Item_ID
	// * QtyTUs
	// * (only one LU)
	// * HUPlanningReceiptOwnerPM
	private void action_SplitTU_To_NewLU()
	{
		
	}

	// Params:
	// * existing LU (M_HU_ID)
	// * QtyTUs
	private void action_SplitTU_To_ExistingLU()
	{

	}
}
