package de.metas.edi.sscc18;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.impl.pack.CreateEDIDesadvPackRequest;
import de.metas.edi.api.impl.pack.EDIDesadvPack;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.edi.api.impl.pack.EDIDesadvPackRepository;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator.LUQtys;
import de.metas.handlingunits.attributes.sscc18.SSCC18;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBL;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Producer which is used to generate {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack}s labels and print them.
 *
 * @author tsa
 */
public class DesadvLineSSCC18Generator
{
	private static final Logger logger = LogManager.getLogger(DesadvLineSSCC18Generator.class);

	// services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final SSCC18CodeBL sscc18CodeBL;
	private final IDesadvBL desadvBL;
	private final EDIDesadvPackRepository ediDesadvPackRepository;

	//
	// Parameters
	/**
	 * For an {@link I_EDI_DesadvLine}, shall we also print the existing labels?
	 */
	private final boolean printExistingLabels;

	//
	// status
	/**
	 * {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack} IDs to print
	 */
	private final Set<EDIDesadvPackId> desadvLineSSCC_IDs_ToPrint = new LinkedHashSet<>();

	@Builder
	private DesadvLineSSCC18Generator(
			@NonNull final SSCC18CodeBL sscc18CodeService,
			@NonNull final IDesadvBL desadvBL,
			@NonNull final EDIDesadvPackRepository ediDesadvPackRepository,
			final boolean printExistingLabels)
	{
		this.sscc18CodeBL = sscc18CodeService;
		this.desadvBL = desadvBL;
		this.ediDesadvPackRepository = ediDesadvPackRepository;
		this.printExistingLabels = printExistingLabels;
	}

	/**
	 * Generates {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack} records until {@link IPrintableDesadvLineSSCC18Labels#getRequiredSSCC18sCount()} is fulfilled.
	 * <p>
	 * It will enqueue the SSCC18 labels to be printed.
	 * To actually print the labels, call {@link #printAll()}.
	 */
	public void generateAndEnqueuePrinting(@NonNull final IPrintableDesadvLineSSCC18Labels desadvLineLabels)
	{
		final List<EDIDesadvPack> desadvLineSSCCsExisting = desadvLineLabels.getExistingSSCC18s();
		final int countExisting = desadvLineSSCCsExisting.size();
		final int countRequired = desadvLineLabels.getRequiredSSCC18sCount().intValueExact();
		final TotalQtyCUBreakdownCalculator totalQtyCUsRemaining = desadvLineLabels.breakdownTotalQtyCUsToLUs();

		for (int i = 0; i < countRequired; i++)
		{
			// Use existing SSCC if any
			if (i < countExisting)
			{
				final EDIDesadvPack desadvPack = desadvLineSSCCsExisting.get(i);

				// Subtract the "LU" of this SSCC from total QtyCUs remaining
				totalQtyCUsRemaining.subtractLU()
						.setQtyTUsPerLU(desadvPack.getQtyTU())
						.setQtyCUsPerTU(desadvPack.getQtyCU())
						.setQtyCUsPerLU_IfGreaterThanZero(desadvPack.getQtyCUsPerLU())
						.build();

				if (printExistingLabels)
				{
					enqueueToPrint(desadvPack);
				}
			}
			// Generate a new SSCC record
			else
			{
				final I_EDI_DesadvLine desadvLine = desadvLineLabels.getEDI_DesadvLine();

				// Subtract one LU from total QtyCUs remaining.
				final LUQtys luQtys = totalQtyCUsRemaining.subtractOneLU();

				final EDIDesadvPack desadvPack = generateDesadvLineSSCC(desadvLine, luQtys);
				enqueueToPrint(desadvPack);
			}
		}
	}

	/**
	 * Generates {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack} records until {@link IPrintableDesadvLineSSCC18Labels#getRequiredSSCC18sCount()} is fullfilled.
	 * <p>
	 * It will enqueue the SSCC18 labels to be printed.
	 * To actually print the labels, call {@link #printAll()}.
	 *
	 * @param desadvLineLabelsCollection collection of {@link IPrintableDesadvLineSSCC18Labels}
	 */
	public void generateAndEnqueuePrinting(final Collection<IPrintableDesadvLineSSCC18Labels> desadvLineLabelsCollection)
	{
		// Do nothing if there is nothing to print
		if (Check.isEmpty(desadvLineLabelsCollection))
		{
			logger.info("Parameter 'desadvLineLabelsCollection' is empty; returning");
			return;
		}

		//
		// Generate all SSCC18 labels in one transaction
		trxManager.runInThreadInheritedTrx(() -> {
			for (final IPrintableDesadvLineSSCC18Labels desadvLineLabels : desadvLineLabelsCollection)
			{
				generateAndEnqueuePrinting(desadvLineLabels);
			}
		});
	}

	/**
	 * Print all enqueued SSCC18 labels.
	 */
	public void printAll()
	{
		if (Check.isEmpty(desadvLineSSCC_IDs_ToPrint))
		{
			// nothing to print
			return;
		}

		desadvBL.printSSCC18_Labels(Env.getCtx(), desadvLineSSCC_IDs_ToPrint);
		desadvLineSSCC_IDs_ToPrint.clear();
	}

	/**
	 * Creates a new {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack} record with a new {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item} record.
	 * <p>
	 * The SSCC18 code will be generated.
	 */
	private EDIDesadvPack generateDesadvLineSSCC(final I_EDI_DesadvLine desadvLine, final LUQtys luQtys)
	{
		//
		// Generate the actual SSCC18 number and update the SSCC record
		final SSCC18 sscc18 = sscc18CodeBL.generate(OrgId.ofRepoId(desadvLine.getAD_Org_ID()));
		final String ipaSSCC18 = sscc18.asString(); // humanReadable=false

		// Create SSCC record
		final CreateEDIDesadvPackRequest.CreateEDIDesadvPackItemRequest createEDIDesadvPackItemRequest = CreateEDIDesadvPackRequest.CreateEDIDesadvPackItemRequest
				.builder()
				.ediDesadvLineId(EDIDesadvLineId.ofRepoId(desadvLine.getEDI_DesadvLine_ID()))
				.qtyCu(luQtys.getQtyCUsPerTU())
				.qtyTu(luQtys.getQtyTUsPerLU().intValueExact())
				.qtyCUsPerLU(luQtys.getQtyCUsPerLU())
				.movementQtyInStockUOM(luQtys.getQtyCUsPerLU())
				.build();

		final CreateEDIDesadvPackRequest createEDIDesadvPackRequest = CreateEDIDesadvPackRequest.builder()
				.orgId(OrgId.ofRepoId(desadvLine.getAD_Org_ID()))
				.ediDesadvId(EDIDesadvId.ofRepoId(desadvLine.getEDI_Desadv_ID()))
				.sscc18(ipaSSCC18)
				.isManualIpaSSCC(true)
				.createEDIDesadvPackItemRequest(createEDIDesadvPackItemRequest)
				.build();

		return ediDesadvPackRepository.createDesadvPack(createEDIDesadvPackRequest);
	}

	private void enqueueToPrint(@NonNull final EDIDesadvPack desadvLineSSCC)
	{
		desadvLineSSCC_IDs_ToPrint.add(desadvLineSSCC.getEdiDesadvPackId());
	}

	public ImmutableSet<EDIDesadvPackId> getLineSSCCIdsToPrint()
	{
		return ImmutableSet.copyOf(desadvLineSSCC_IDs_ToPrint);
	}

}
