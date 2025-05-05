package de.metas.dunning.api;

/*
 * #%L
 * de.metas.dunning
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


import de.metas.dunning.DunningDocId;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.IDunningAggregator;

import java.util.Collection;

/**
 * Dunning producer is responsible for taking {@link I_C_Dunning_Candidate}s and generate several {@link I_C_DunningDoc} s with {@link I_C_DunningDoc_Line}s.
 * 
 * Implementation of this class can also aggregate multiple {@link I_C_Dunning_Candidate}s to one {@link I_C_DunningDoc} with multiple {@link I_C_DunningDoc_Line}s or aggregate multiple
 * {@link I_C_Dunning_Candidate}s to one {@link I_C_DunningDoc_Line}.
 * 
 * The link between {@link I_C_Dunning_Candidate} and {@link I_C_DunningDoc_Line} will be recorded in {@link I_C_DunningDoc_Line_Source}.
 * 
 * {@link I_C_Dunning_Candidate}s that were processed will be set {@link I_C_Dunning_Candidate#COLUMNNAME_Processed} to Yes.
 * 
 * @author tsa
 * 
 */
public interface IDunningProducer
{
	/**
	 * If this option is set in context, the producer will use the async batch when enqueueing
	 */
	String CONTEXT_AsyncBatchDunningDoc = IDunningProducer.class.getName() + "#" + "AsyncBatch";

	/**
	 * If this option is set in context, the producer will also process the {@link I_C_DunningDoc}s.
	 */
	String CONTEXT_ProcessDunningDoc = IDunningProducer.class.getName() + "#" + "ProcessDunningDoc";
	boolean DEFAULT_ProcessDunningDoc = false;

	void setDunningContext(IDunningContext context);

	/**
	 * Adds another aggregator that is consulted on aggregation issues during invocations of {@link #addCandidate(I_C_Dunning_Candidate)}.
	 * 
	 * @param aggregator
	 */
	void addAggregator(IDunningAggregator aggregator);

	/**
	 * Add a dunning candidate. Implementation of this method can generate an {@link I_C_DunningDoc} right away or can put the line in a queue and generate the document later.
	 * 
	 * @param candidate
	 */
	void addCandidate(I_C_Dunning_Candidate candidate);

	/**
	 * By calling this method you notify the {@link IDunningProducer} that there are no more candidates so this class can finish it's job (e.g. generate the last {@link I_C_DunningDoc} for candidates
	 * that are currently in queue, if any).
	 */
	void finish();

	Collection<DunningDocId> getCreatedDunningDocIds();
}
