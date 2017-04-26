package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.CompositePredicate;
import org.eevolution.model.I_AD_Note;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.junit.Assert;

import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMaterialPlanningContext;

public class MockedMRPExecutor extends MRPExecutor
{
	private final CompositePredicate<I_AD_Note> allowMRPNoteMatchers = new CompositePredicate<I_AD_Note>();
	private boolean disallowMRPNotes = false;

	private List<Throwable> errors = new ArrayList<>();

	public MockedMRPExecutor()
	{
		allowMRPNoteMatchers.setAnd(false); // any of predicates shall be matched
	}

	@Override
	protected I_AD_Note createMRPNote(final IMRPNoteBuilder mrpNoteBuilder)
	{
		final I_AD_Note note = super.createMRPNote(mrpNoteBuilder);
		onMRPNoteBuilt(note, mrpNoteBuilder);

		return note;
	}

	protected void onMRPNoteBuilt(final I_AD_Note note, final IMRPNoteBuilder builder)
	{
		if (!isMRPNoteAllowed(note))
		{
			final MRPNoteBuilder mrpNoteBuilder = MRPNoteBuilder.cast(builder);
			
			final StringBuilder message = new StringBuilder("Got MRP Notice " + mrpNoteBuilder.getMRPCode_ToUse());
			message.append("\nMRP Context: " + mrpNoteBuilder.getMRPContext());
			message.append("\nComment: " + mrpNoteBuilder.getComment_ToUse());
			
			final I_PP_MRP mrpRecord = mrpNoteBuilder.getPP_MRP_ToUse();
			final String mrpRecordStr = mrpRecord == null ? "-" : MRPTracer.toString(mrpRecord);
			message.append("\nMRP Record: " + mrpRecordStr);
			
			final String parametersStr = mrpNoteBuilder.getParametersAsString();
			if (!Check.isEmpty(parametersStr, true))
			{
				message.append("\n").append(parametersStr);
			}
			
			message.append("\n");
			
			final AssertionError error = new AssertionError(message.toString(), mrpNoteBuilder.getException_ToUse());
			errors.add(error);
		}
	}

	/**
	 * Asserts no errors were encounted while executing MRP
	 */
	public void assertNoErrors()
	{
		if (errors.isEmpty())
		{
			return;
		}

		final PrintStream out = System.out;

		final int errorsCount = errors.size();
		for (int i = 0; i < errorsCount; i++)
		{
			final Throwable error = errors.get(i);
			out.println("===========================================================================================================");
			out.println("MRPExecutor error " + (i + 1) + "/" + errorsCount);
			out.println("Stacktrace: ");
			error.printStackTrace(out);
			out.println("===========================================================================================================");
		}

		out.flush();

		final String errmsg = "Encounted " + errorsCount + " errors while running MRP. Check console";
		Assert.fail(errmsg);
	}

	protected final boolean isMRPNoteAllowed(final I_AD_Note note)
	{
		final boolean allowMRPNote;
		if (allowMRPNoteMatchers.isEmpty())
		{
			allowMRPNote = !disallowMRPNotes;
		}
		else
		{
			allowMRPNote = allowMRPNoteMatchers.evaluate(note);
		}
		return allowMRPNote;
	}

	/**
	 * If set to <code>true</code> it will fail on any MRP note received which was not matched by our allowed MRP notes matchers (see {@link #createAllowMRPNodeRule()}).
	 * 
	 * @param disallowMRPNotes
	 */
	public final void setDisallowMRPNotes(final boolean disallowMRPNotes)
	{
		this.disallowMRPNotes = disallowMRPNotes;
	}

	public final MRPNoteMatcherBuilder createAllowMRPNodeRule()
	{
		return new MRPNoteMatcherBuilder(allowMRPNoteMatchers);
	}

	@Override
	protected void markMRPRecordsAvailable(final IMaterialPlanningContext mrpContext)
	{
		super.markMRPRecordsAvailable(mrpContext);

		applyMRPDeleteCascadeConstraints(mrpContext);
	}

	private void applyMRPDeleteCascadeConstraints(final IMaterialPlanningContext mrpContext)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<Integer> existingMRPIds = queryBL
				.createQueryBuilder(I_PP_MRP.class, mrpContext)
				.create()
				.listIds();

		//
		// Update PP_MRP.PP_MRP_Parent_ID=null if the ID no longer exists
		final ICompositeQueryUpdater<I_PP_MRP> setToNull_PP_MRP_Parent_ID = queryBL.createCompositeQueryUpdater(I_PP_MRP.class)
				.addSetColumnValue(I_PP_MRP.COLUMNNAME_PP_MRP_Parent_ID, null);
		queryBL.createQueryBuilder(I_PP_MRP.class, mrpContext)
				.addNotEqualsFilter(I_PP_MRP.COLUMN_PP_MRP_Parent_ID, null)
				.addNotInArrayFilter(I_PP_MRP.COLUMN_PP_MRP_Parent_ID, existingMRPIds)
				.create()
				.updateDirectly(setToNull_PP_MRP_Parent_ID);

		//
		// Delete PP_MRP_Alloc if PP_MRP_Demand_ID no longer exist
		queryBL.createQueryBuilder(I_PP_MRP_Alloc.class, mrpContext)
				.addNotInArrayFilter(I_PP_MRP_Alloc.COLUMN_PP_MRP_Demand_ID, existingMRPIds)
				.create()
				.deleteDirectly();

		//
		// Delete PP_MRP_Alloc if PP_MRP_Supply_ID no longer exist
		queryBL.createQueryBuilder(I_PP_MRP_Alloc.class, mrpContext)
				.addNotInArrayFilter(I_PP_MRP_Alloc.COLUMN_PP_MRP_Supply_ID, existingMRPIds)
				.create()
				.deleteDirectly();
	}

}
