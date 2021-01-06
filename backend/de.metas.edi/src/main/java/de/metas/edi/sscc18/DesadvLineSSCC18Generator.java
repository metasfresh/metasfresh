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

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import de.metas.edi.api.IDesadvBL;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator.LUQtys;
import de.metas.handlingunits.attributes.sscc18.SSCC18;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBL;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Producer which is used to generate {@link I_EDI_DesadvLine_Pack}s labels and print them.
 *
 * @author tsa
 *
 */
public class DesadvLineSSCC18Generator
{
	private static final transient Logger logger = LogManager.getLogger(DesadvLineSSCC18Generator.class);

	// services
	private final transient SSCC18CodeBL sscc18CodeBL;
	private final transient IDesadvBL desadvBL = Services.get(IDesadvBL.class);

	//
	// Parameters
	private IContextAware _context;
	/** For an {@link I_EDI_DesadvLine}, shall we also print the existing labels? */
	private boolean printExistingLabels = true;

	//
	// status
	/** {@link I_EDI_DesadvLine_Pack} IDs to print */
	private final Set<Integer> desadvLineSSCC_IDs_ToPrint = new LinkedHashSet<>();

	public DesadvLineSSCC18Generator(@NonNull final SSCC18CodeBL sscc18CodeService)
	{
		this.sscc18CodeBL = sscc18CodeService;
	}

	/**
	 * Generates {@link I_EDI_DesadvLine_Pack} records until {@link IPrintableDesadvLineSSCC18Labels#getRequiredSSCC18sCount()} is fulfilled.
	 *
	 * It will enqueue the SSCC18 labels to be printed.
	 * To actually print the labels, call {@link #printAll()}.
	 */
	public DesadvLineSSCC18Generator generateAndEnqueuePrinting(@NonNull final IPrintableDesadvLineSSCC18Labels desadvLineLabels)
	{
		final List<I_EDI_DesadvLine_Pack> desadvLineSSCCsExisting = desadvLineLabels.getExistingSSCC18s();
		final int countExisting = desadvLineSSCCsExisting.size();
		final int countRequired = desadvLineLabels.getRequiredSSCC18sCount().intValueExact();
		final TotalQtyCUBreakdownCalculator totalQtyCUsRemaining = desadvLineLabels.breakdownTotalQtyCUsToLUs();

		for (int i = 0; i < countRequired; i++)
		{
			// Use existing SSCC if any
			if (i < countExisting)
			{
				final I_EDI_DesadvLine_Pack desadvLineSSCC = desadvLineSSCCsExisting.get(i);

				// Subtract the "LU" of this SSCC from total QtyCUs remaining
				totalQtyCUsRemaining.subtractLU()
						.setQtyTUsPerLU(desadvLineSSCC.getQtyTU())
						.setQtyCUsPerTU(desadvLineSSCC.getQtyCU())
						.setQtyCUsPerLU_IfGreaterThanZero(desadvLineSSCC.getQtyCUsPerLU())
						.build();

				if (printExistingLabels)
				{
					enqueueToPrint(desadvLineSSCC);
				}
			}
			// Generate a new SSCC record
			else
			{
				final I_EDI_DesadvLine desadvLine = desadvLineLabels.getEDI_DesadvLine();

				// Subtract one LU from total QtyCUs remaining.
				final LUQtys luQtys = totalQtyCUsRemaining.subtractOneLU();

				final I_EDI_DesadvLine_Pack desadvLineSSCC = generateDesadvLineSSCC(desadvLine, luQtys);
				enqueueToPrint(desadvLineSSCC);
			}
		}

		return this;
	}

	/**
	 * Generates {@link I_EDI_DesadvLine_Pack} records until {@link IPrintableDesadvLineSSCC18Labels#getRequiredSSCC18sCount()} is fullfilled.
	 *
	 * It will enqueue the SSCC18 labels to be printed.
	 * To actually print the labels, call {@link #printAll()}.
	 *
	 * @param desadvLineLabelsCollection collection of {@link IPrintableDesadvLineSSCC18Labels}
	 */
	public DesadvLineSSCC18Generator generateAndEnqueuePrinting(final Collection<IPrintableDesadvLineSSCC18Labels> desadvLineLabelsCollection)
	{
		// Do nothing if there is nothing to print
		if (Check.isEmpty(desadvLineLabelsCollection))
		{
			logger.info("Parameter 'desadvLineLabelsCollection' is empty; returning");
			return this;
		}

		//
		// Generate all SSCC18 labels in one transaction
		final IContextAware contextInitial = getContext();
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(contextInitial.getTrxName(), new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				final IContextAware context = trxManager.createThreadContextAware(contextInitial.getCtx());
				setContext(context);

				for (final IPrintableDesadvLineSSCC18Labels desadvLineLabels : desadvLineLabelsCollection)
				{
					generateAndEnqueuePrinting(desadvLineLabels);
				}
			}

			@Override
			public void doFinally()
			{
				setContext(contextInitial); // restore context
			}
		});

		return this;
	}

	/**
	 * Print all enqueued SSCC18 labels.
	 */
	public DesadvLineSSCC18Generator printAll()
	{
		if (Check.isEmpty(desadvLineSSCC_IDs_ToPrint))
		{
			// nothing to print
			return this;
		}

		final IContextAware context = getContext();

		desadvBL.printSSCC18_Labels(context.getCtx(), desadvLineSSCC_IDs_ToPrint);
		desadvLineSSCC_IDs_ToPrint.clear();

		return this;
	}

	public final DesadvLineSSCC18Generator setContext(final IContextAware context)
	{
		this._context = context;
		return this;
	}

	private final IContextAware getContext()
	{
		Check.assumeNotNull(_context, "_context not null");
		return _context;
	}

	/**
	 * Sets if it shall also print the existing labels of an {@link I_EDI_DesadvLine} or only the newly generated ones.
	 *
	 * @param printExistingLabels
	 */
	public DesadvLineSSCC18Generator setPrintExistingLabels(final boolean printExistingLabels)
	{
		this.printExistingLabels = printExistingLabels;
		return this;
	}

	/**
	 * Creates a new {@link I_EDI_DesadvLine_Pack} record.
	 *
	 * The SSCC18 code will be generated.
	 */
	private final I_EDI_DesadvLine_Pack generateDesadvLineSSCC(final I_EDI_DesadvLine desadvLine, final LUQtys luQtys)
	{
		final IContextAware context = getContext();
		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();

		//
		// Generate the actual SSCC18 number and update the SSCC record
		final SSCC18 sscc18 = sscc18CodeBL.generate(OrgId.ofRepoId(desadvLine.getAD_Org_ID()));
		final String ipaSSCC18 = sscc18.asString(); // humanReadable=false

		//
		// Create SSCC record
		final I_EDI_DesadvLine_Pack desadvLineSSCC = InterfaceWrapperHelper.create(ctx, I_EDI_DesadvLine_Pack.class, trxName);
		desadvLineSSCC.setAD_Org_ID(desadvLine.getAD_Org_ID());
		desadvLineSSCC.setEDI_DesadvLine(desadvLine);
		desadvLineSSCC.setIPA_SSCC18(ipaSSCC18);
		desadvLineSSCC.setQtyCU(luQtys.getQtyCUsPerTU());
		desadvLineSSCC.setQtyTU(luQtys.getQtyTUsPerLU().intValueExact());
		desadvLineSSCC.setQtyCUsPerLU(luQtys.getQtyCUsPerLU());
		InterfaceWrapperHelper.save(desadvLineSSCC);

		return desadvLineSSCC;
	}

	private final void enqueueToPrint(@NonNull final I_EDI_DesadvLine_Pack desadvLineSSCC)
	{
		desadvLineSSCC_IDs_ToPrint.add(desadvLineSSCC.getEDI_DesadvLine_Pack_ID());
	}

}
