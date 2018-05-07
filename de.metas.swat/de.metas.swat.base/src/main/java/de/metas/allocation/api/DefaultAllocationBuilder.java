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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.slf4j.Logger;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.builder.BuilderSupport;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;

/**
 * Default allocation builder implementation. Other modules/project can subclass this if they need to build extended allocations.
 * 
 * @author ts
 * 
 */
public class DefaultAllocationBuilder implements IAllocationBuilder
{
	// services
	private static final transient Logger logger = LogManager.getLogger(DefaultAllocationBuilder.class);

	// Parameters
	@ToStringBuilder(skip = true)
	private final Object contextProvider;

	// Status
	private final I_C_AllocationHdr allocHdr;
	private boolean _built;
	
	private final java.util.List<I_C_AllocationLine> allocationLines = new ArrayList<>();

	private final BuilderSupport<DefaultAllocationLineBuilder> s = new BuilderSupport<DefaultAllocationLineBuilder>(this);

	public DefaultAllocationBuilder(final IContextAware contextProvider)
	{
		super();

		// Make sure we are running in transaction
		Services.get(ITrxManager.class).assertTrxNotNull(contextProvider);
		this.contextProvider = contextProvider;

		// Create the allocation header (draft).
		// It will be saved only when needed.
		this.allocHdr = InterfaceWrapperHelper.newInstance(I_C_AllocationHdr.class, contextProvider);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public final IAllocationBuilder setAD_Org_ID(final int adOrgId)
	{
		assertNotBuilt();
		allocHdr.setAD_Org_ID(adOrgId);
		return this;
	}

	@Override
	public final IAllocationBuilder setDateTrx(Timestamp dateTrx)
	{
		assertNotBuilt();
		allocHdr.setDateTrx(dateTrx);
		return this;
	}

	@Override
	public final IAllocationBuilder setDateAcct(Timestamp dateAcct)
	{
		assertNotBuilt();
		allocHdr.setDateAcct(dateAcct);
		return this;
	}

	@Override
	public final IAllocationBuilder setC_Currency_ID(int c_Currency_ID)
	{
		assertNotBuilt();
		allocHdr.setC_Currency_ID(c_Currency_ID);
		return this;
	}

	@Override
	public final IAllocationBuilder setManual(final boolean manual)
	{
		assertNotBuilt();
		allocHdr.setIsManual(manual);
		return this;
	}

	@Override
	public IAllocationBuilder setDescription(final String description)
	{
		assertNotBuilt();
		allocHdr.setDescription(description);
		return this;
	}

	@Override
	public IAllocationLineBuilder addLine()
	{
		return addLine(DefaultAllocationLineBuilder.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final <T extends DefaultAllocationLineBuilder> T addLine(final Class<T> implClazz)
	{
		assertNotBuilt();
		return (T)s.addLine(implClazz);
	}

	
	
	@Override
	public I_C_AllocationHdr create(final boolean complete)
	{
		markAsBuilt();

		// Supplier which provides a created & saved allocation header.
		// To be used by line builder only when the line builder is sure it will create a line.
		// We are memorizing the result because we want to save it only first time when it's called.
		final Supplier<I_C_AllocationHdr> allocHdrSupplier = Suppliers.memoize(new Supplier<I_C_AllocationHdr>()
		{
			@Override
			public I_C_AllocationHdr get()
			{
				InterfaceWrapperHelper.save(allocHdr);
				return allocHdr;
			}
		});

		//
		// Iterate all line builders and create allocation lines, if needed.
		int createdLinesCount = 0;
		for (final DefaultAllocationLineBuilder line : s.getLines())
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
			Services.get(IDocumentBL.class).processEx(allocHdr, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}

		return allocHdr;
	}

	@Override
	public final I_C_AllocationHdr createAndComplete()
	{
		final boolean complete = true;
		return create(complete);
	}

	private final void markAsBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	private final void assertNotBuilt()
	{
		Check.assume(!_built, "Not already built");
	}

	/**
	 * Returns the contextProvider this instance was created with.
	 * 
	 * @return
	 */
	public final Object getContextProvider()
	{
		return contextProvider;
	}

	@Override
	public final int getC_AllocationLine_ID()
	{
		final Set<Integer> allocationLineIds = new HashSet<>();
		for (final DefaultAllocationLineBuilder line : s.getLines())
		{
			final int allocationLineId = line.getC_AllocationLine_ID();
			if (allocationLineId <= 0)
			{
				continue;
			}

			allocationLineIds.add(allocationLineId);
		}

		if (allocationLineIds.size() != 1)
		{
			return -1;
		}

		return allocationLineIds.iterator().next();
	}
	
	@Override
	public final java.util.List<I_C_AllocationLine> getC_AllocationLines()
	{
		return allocationLines;
	}

	@Override
	public IAllocationBuilder disableUpdateBPartnerTotalOpenBanace()
	{
		IBPartnerStatisticsUpdater.DYNATTR_DisableUpdateTotalOpenBalances.setValue(allocHdr, true);
		return this;
	}

	@Override
	public Set<Integer> getC_BPartner_IDs()
	{
		final Set<Integer> bpartnerIds = new HashSet<>();
		for (final DefaultAllocationLineBuilder line : s.getLines())
		{
			final int bpartnerId = line.getC_BPartner_ID();
			if (bpartnerId <= 0)
			{
				continue;
			}

			bpartnerIds.add(bpartnerId);
		}
		return bpartnerIds;
	}

}
