package de.metas.dlm.partitioner.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.adempiere.util.lang.ITableRecordReference;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.google.common.collect.ImmutableList;

import de.metas.dlm.partitioner.impl.IIterateResult;
import de.metas.dlm.partitioner.process.DLM_FindPathBetweenRecords;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * This implementation is dedicated to finding a path between different records.
 * It collects all records that the crawler finds and in the end uses a graph library to find a path bitween them.
 *
 * @see DLM_FindPathBetweenRecords
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class FindPathIterateResult implements IIterateResult
{
	private final ITableRecordReference start;
	private final ITableRecordReference goal;

	private final DirectedGraph<ITableRecordReference, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

	private int size = 0;

	private boolean foundGoal = false;

	private final LinkedList<ITableRecordReference> queueItemsToProcess = new LinkedList<>();

	public FindPathIterateResult(final ITableRecordReference start, final ITableRecordReference goal)
	{
		this.start = start;
		this.goal = goal;
		queueItemsToProcess.addLast(start);
	}

	private boolean add(final ITableRecordReference referencingRecord, final ITableRecordReference referencedRecord, final boolean calledFromAddReferencedRecord)
	{
		final boolean referencingRecordAdded = referencingRecord == null ? false : g.addVertex(referencingRecord);
		final boolean referencedRecordAdded = referencedRecord == null ? false : g.addVertex(referencedRecord);

		if (referencingRecordAdded || referencedRecordAdded)
		{
			g.addEdge(referencingRecord, referencedRecord);
		}

		if (referencingRecordAdded)
		{
			size++;
		}

		if (referencedRecordAdded)
		{
			size++;
		}

		if (referencingRecord.equals(goal) || referencedRecord.equals(goal))
		{
			foundGoal = true;
		}

		return calledFromAddReferencedRecord ? referencedRecordAdded : referencingRecordAdded;
	}

	@Override
	public boolean addReferencedRecord(final ITableRecordReference referencingRecord, final ITableRecordReference referencedRecord, final int IGNORED)
	{
		final boolean added = add(referencingRecord, referencedRecord, true);
		if (added)
		{
			queueItemsToProcess.addLast(referencedRecord);
		}
		return added;
	}

	@Override
	public boolean addReferencingRecord(final ITableRecordReference referencingRecord, final ITableRecordReference referencedRecord, final int IGNORED)
	{
		final boolean added = add(referencingRecord, referencedRecord, false);
		if (added)
		{
			queueItemsToProcess.addLast(referencingRecord);
		}
		return added;
	}

	@Override
	public int size()
	{
		return size;
	}

	/**
	 * @return <code>true</code> if the "goal" record was found, or if there aren't forther records in the queue to check.
	 */
	@Override
	public boolean isQueueEmpty()
	{
		if (foundGoal)
		{
			return true;
		}

		return queueItemsToProcess.isEmpty();
	}

	@Override
	public ITableRecordReference nextFromQueue()
	{
		return queueItemsToProcess.removeFirst();
	}

	public boolean isFoundGoalRecord()
	{
		return foundGoal;
	}

	@Override
	public boolean contains(final ITableRecordReference forwardReference)
	{
		return g.containsVertex(forwardReference);
	}

	public List<ITableRecordReference> getPath()
	{
		final List<ITableRecordReference> result = new ArrayList<>();

		final AsUndirectedGraph<ITableRecordReference, DefaultEdge> undirectedGraph = new AsUndirectedGraph<>(g);

		final List<DefaultEdge> path = DijkstraShortestPath.<ITableRecordReference, DefaultEdge> findPathBetween(
				undirectedGraph, start, goal);
		if (path == null || path.isEmpty())
		{
			return ImmutableList.of();
		}
		result.add(start);
		for (final DefaultEdge e : path)
		{
			final ITableRecordReference edgeSource = undirectedGraph.getEdgeSource(e);
			if (!result.contains(edgeSource))
			{
				result.add(edgeSource);
			}
			else
			{
				result.add(undirectedGraph.getEdgeTarget(e));
			}
		}

		return ImmutableList.copyOf(result);
	}

	/* package */ Graph<ITableRecordReference, DefaultEdge> getGraph()
	{
		return g;
	}

	@Override
	public String toString()
	{
		return "FindPathIterateResult [start=" + start + ", goal=" + goal + ", size=" + size + ", foundGoal=" + foundGoal + ", queueItemsToProcess.size()=" + queueItemsToProcess.size() + "]";
	}

}
