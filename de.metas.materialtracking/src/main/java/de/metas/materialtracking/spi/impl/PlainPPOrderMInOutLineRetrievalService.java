package de.metas.materialtracking.spi.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_Cost_Collector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;

/**
 * Simple map-based implementation that can be used for testing.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class PlainPPOrderMInOutLineRetrievalService implements IPPOrderMInOutLineRetrievalService
{
	private final Multimap<Integer, I_M_InOutLine> costCollectorIdToInOutLines = ArrayListMultimap.create();

	public void addAssociation(I_PP_Cost_Collector costCollector, org.compiere.model.I_M_InOutLine iol)
	{
		costCollectorIdToInOutLines.put(costCollector.getPP_Cost_Collector_ID(), InterfaceWrapperHelper.create(iol, I_M_InOutLine.class));
	}

	@Override
	public List<I_M_InOutLine> provideIssuedInOutLines(I_PP_Cost_Collector issueCostCollector)
	{
		return new ArrayList<>(costCollectorIdToInOutLines.get(issueCostCollector.getPP_Cost_Collector_ID()));
	}
}
