package de.metas.allocation.api;

/*
 * #%L
 * de.metas.swat.base
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

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Convenient way to create (and maybe complete) and {@link I_C_AllocationHdr} with {@link I_C_AllocationLine}s.
 */
@ToString(of = { "allocHdr", "_built", "allocationLineBuilders" })
public class C_AllocationHdr_Builder
{
	// services
	private static final transient Logger logger = LogManager.getLogger(C_AllocationHdr_Builder.class);
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	// Status
	private final I_C_AllocationHdr allocHdr;
	private boolean _built;

	private final ArrayList<C_AllocationLine_Builder> allocationLineBuilders = new ArrayList<>();
	private final ArrayList<I_C_AllocationLine> allocationLines = new ArrayList<>();

	public C_AllocationHdr_Builder()
	{
		// Make sure we are running in transaction
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.assertThreadInheritedTrxExists();

		// Create the allocation header (draft).
		// It will be saved only when needed.
		this.allocHdr = InterfaceWrapperHelper.newInstance(I_C_AllocationHdr.class);
	}

	@Nullable
	public I_C_AllocationHdr create(final boolean complete)
	{
		markAsBuilt();

		// Supplier which provides a created & saved allocation header.
		// To be used by line builder only when the line builder is sure it will create a line.
		// We are memorizing the result because we want to save it only first time when it's called.
		final Supplier<I_C_AllocationHdr> allocHdrSupplier = Suppliers.memoize(() -> {
			allocationDAO.save(allocHdr);
			return allocHdr;
		});

		//
		// Iterate all line builders and create allocation lines, if needed.
		int createdLinesCount = 0;
		for (final C_AllocationLine_Builder line : allocationLineBuilders)
		{
			final I_C_AllocationLine allocationLine = line.create(allocHdrSupplier);
			if (allocationLine != null)
			{
				createdLinesCount++;
				allocationLines.add(allocationLine);
			}
		}

		// If the allocation was not saved, it means it was not needed, so we stop here
		if (allocHdr.getC_AllocationHdr_ID() <= 0)
		{
			Check.assume(createdLinesCount == 0, "When allocation header is not saved, we expect no lines to be created but we got {}", createdLinesCount);
			return null;
		}
		// If there were no lines created, but our allocation header is created and saved, we shall get rid of it.
		// NOTE: this case could happen only if the line builder is poorly written.
		if (createdLinesCount <= 0)
		{
			final AdempiereException ex = new AdempiereException("No allocation lines were created even though the allocation header was saved. Deleting it: " + allocHdr);
			logger.warn(ex.getLocalizedMessage(), ex);
			InterfaceWrapperHelper.delete(allocHdr);
		}

		//
		// Process the allocation if asked
		if (complete)
		{
			documentBL.processEx(allocHdr, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}

		return allocHdr;
	}

	public final I_C_AllocationHdr createAndComplete()
	{
		final boolean complete = true;
		return create(complete);
	}

	private void markAsBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	private void assertNotBuilt()
	{
		Check.assume(!_built, "Not already built");
	}

	IAllocationDAO getAllocationDAO()
	{
		return allocationDAO;
	}

	public final C_AllocationHdr_Builder orgId(@NonNull final OrgId orgId)
	{
		return orgId(orgId.getRepoId());
	}

	public final C_AllocationHdr_Builder orgId(final int adOrgId)
	{
		assertNotBuilt();
		allocHdr.setAD_Org_ID(adOrgId);
		return this;
	}

	public final C_AllocationHdr_Builder dateTrx(LocalDate dateTrx)
	{
		return dateTrx(TimeUtil.asTimestamp(dateTrx));
	}

	public final C_AllocationHdr_Builder dateTrx(Timestamp dateTrx)
	{
		assertNotBuilt();
		allocHdr.setDateTrx(dateTrx);
		return this;
	}

	public final C_AllocationHdr_Builder dateAcct(LocalDate dateAcct)
	{
		return dateAcct(TimeUtil.asTimestamp(dateAcct));
	}

	public final C_AllocationHdr_Builder dateAcct(Timestamp dateAcct)
	{
		assertNotBuilt();
		allocHdr.setDateAcct(dateAcct);
		return this;
	}

	public final C_AllocationHdr_Builder currencyId(@NonNull final CurrencyId currencyId)
	{
		return currencyId(currencyId.getRepoId());
	}

	public final C_AllocationHdr_Builder currencyId(int currencyId)
	{
		assertNotBuilt();
		allocHdr.setC_Currency_ID(currencyId);
		return this;
	}

	public final C_AllocationHdr_Builder manual(final boolean manual)
	{
		assertNotBuilt();
		allocHdr.setIsManual(manual);
		return this;
	}

	public C_AllocationHdr_Builder description(final String description)
	{
		assertNotBuilt();
		allocHdr.setDescription(description);
		return this;
	}

	public C_AllocationLine_Builder addLine()
	{
		assertNotBuilt();

		final C_AllocationLine_Builder lineBuilder = new C_AllocationLine_Builder(this);
		allocationLineBuilders.add(lineBuilder);

		return lineBuilder;
	}

	public final ImmutableList<I_C_AllocationLine> getC_AllocationLines()
	{
		return ImmutableList.copyOf(allocationLines);
	}

	public C_AllocationHdr_Builder disableUpdateBPartnerTotalOpenBanace()
	{
		IBPartnerStatisticsUpdater.DYNATTR_DisableUpdateTotalOpenBalances.setValue(allocHdr, true);
		return this;
	}
}
