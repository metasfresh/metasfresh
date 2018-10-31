package de.metas.ui.web.handlingunits.process;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Attribute;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters.CreateReceiptsParametersBuilder;
import de.metas.i18n.ITranslatableString;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowAttributes;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

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

public abstract class WEBUI_M_HU_CreateReceipt_Base
		extends WEBUI_M_HU_Receipt_Base
		implements IProcessPrecondition
{

	@Autowired
	private IViewsRepository viewsRepo;

	@Autowired
	private DocumentCollection documentsCollection;

	private final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	/**
	 * Only allows rows whose HU is in the "planning" status.
	 */
	@Override
	protected final ProcessPreconditionsResolution rejectResolutionOrNull(final HUEditorRow document)
	{
		if (!document.isHUStatusPlanning())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Only planning HUs can be received");
		}

		final HUEditorRowAttributes attributes = document.getAttributes();
		for (final String mandatoryAttributeName : attributes.getMandatoryAttributeNames())
		{
			final Object value = attributes.getValue(mandatoryAttributeName);
			if (Check.isEmpty(value))
			{
				final I_M_Attribute attribute = attributeDAO.retrieveAttributeByValue(mandatoryAttributeName);
				final I_M_Attribute translatedAttribute = InterfaceWrapperHelper.translate(attribute, I_M_Attribute.class);
				final ITranslatableString msg = msgBL.getTranslatableMsgText("WEBUI_Receipt_Missing_Mandatory_HU_Attribute", translatedAttribute.getName());
				return ProcessPreconditionsResolution.reject(msg);
			}
		}
		return null;
	}

	@Override
	@RunOutOfTrx // IHUReceiptScheduleBL.processReceiptSchedules creates its own transaction
	protected String doIt() throws Exception
	{
		// Generate material receipts
		final List<I_M_ReceiptSchedule> receiptSchedules = getM_ReceiptSchedules();
		final Set<HuId> selectedHuIds = retrieveHUsToReceive();

		final CreateReceiptsParametersBuilder parametersBuilder = CreateReceiptsParameters.builder()
				.commitEachReceiptIndividually(false)
				.createReceiptWithDatePromised(false)
				.ctx(getCtx())
				.destinationLocatorIdOrNull(null) // use receipt schedules' destination-warehouse settings
				.printReceiptLabels(true)
				.receiptSchedules(receiptSchedules)
				.selectedHuIds(selectedHuIds);

		customizeParametersBuilder(parametersBuilder);

		final CreateReceiptsParameters parameters = parametersBuilder.build();

		huReceiptScheduleBL.processReceiptSchedules(parameters);
		// NOTE: at this point, the user was already notified about generated material receipts

		// Reset the view's affected HUs
		getView().invalidateAll();

		viewsRepo.notifyRecordsChanged(TableRecordReferenceSet.of(TableRecordReference.ofSet(receiptSchedules)));

		return MSG_OK;
	}

	protected abstract void customizeParametersBuilder(final CreateReceiptsParametersBuilder parametersBuilder);

	@Override
	protected HUEditorView getView()
	{
		return getView(HUEditorView.class);
	}

	protected List<I_M_ReceiptSchedule> getM_ReceiptSchedules()
	{
		return getView()
				.getReferencingDocumentPaths().stream()
				.map(referencingDocumentPath -> getReceiptSchedule(referencingDocumentPath))
				.collect(GuavaCollectors.toImmutableList());
	}

	private I_M_ReceiptSchedule getReceiptSchedule(@NonNull final DocumentPath referencingDocumentPath)
	{
		return documentsCollection
				.getTableRecordReference(referencingDocumentPath)
				.getModel(this, I_M_ReceiptSchedule.class);
	}

	protected Set<HuId> retrieveHUsToReceive()
	{
		// https://github.com/metasfresh/metasfresh/issues/1863
		// if the queryFilter is empty, then *do not* return everything to avoid an OOME
		final IQueryFilter<I_M_HU> processInfoFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final IQuery<I_M_HU> query = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class, this)
				.filter(processInfoFilter)
				.addOnlyActiveRecordsFilter()
				.create();

		final Set<HuId> huIds = query
				.listIds()
				.stream()
				.map(HuId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
		if (huIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@ @M_HU_ID@")
					.appendParametersToMessage()
					.setParameter("query", query);
		}
		return huIds;
	}
}
