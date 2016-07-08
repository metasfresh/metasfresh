package de.metas.materialtracking.spi.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;

/**
 * Simple map-based implementation that can be used for testing.
 *
 * @author metas-dev <dev@metasfresh.com>
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

	@Override
	public Map<Integer, BigDecimal> retrieveIolAndQty(I_PP_Order ppOrder)
	{
		//TODO : this part shall be tested too
		return Collections.emptyMap();
	}
}
