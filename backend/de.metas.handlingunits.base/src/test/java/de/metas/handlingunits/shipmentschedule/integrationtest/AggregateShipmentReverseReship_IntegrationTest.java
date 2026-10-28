package de.metas.handlingunits.shipmentschedule.integrationtest;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.compiere.model.I_M_InOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Aggregate-HU shipment: ship the whole aggregate VHU, then reverse the M_InOut and re-generate the shipment.
 *
 * <p>Goal: characterise the {@code M_ShipmentSchedule_QtyPicked} rows that survive a shipment-reversal cycle for
 * a shipment whose allocation is an aggregate VHU (one {@code M_HU} representing N transport units). When the
 * aggregate HU's snapshot is replayed during reversal, multiple HU-trx lines route through the same VHU; without
 * the consolidation in {@code ShipmentScheduleHUTrxListener#trxLineProcessed} that produces several identical
 * QtyPicked rows on the same (schedule, VHU) which later collide on the partial unique index when the next
 * shipment is generated.
 *
 * <p>The harness builds + ships a real aggregate HU in-memory (inherited from
 * {@link HUShipmentProcess_2LU_1ShipTrans_1InOut_IntegrationTest}); this test then completes the shipment, drives
 * the document engine reverse, re-generates, and dumps + asserts the QtyPicked rows per (schedule, VHU).
 *
 * <p>NOTE on scope: the in-memory POJO document engine ({@link PlainDocumentBL}) fires the registered
 * {@code @DocValidate} model interceptors for reverse, but does NOT execute the legacy {@code MInOut.reverseCorrectIt()}
 * body that restores the HU snapshot and replays the per-TU HU-trx lines. The full per-TU trx replay (the trigger
 * that emits N identical rows) is therefore only reproducible on the real Postgres + document stack. This test
 * proves the structural invariant the fix enforces on the in-memory reverse and dumps the resulting rows for
 * inspection; it is honest about what the in-memory reverse does and does not replay (see the assertions and the
 * dumped row counts).
 */
public class AggregateShipmentReverseReship_IntegrationTest
		extends HUShipmentProcess_2LU_1ShipTrans_1InOut_IntegrationTest
{
	private static final Logger logger = LoggerFactory.getLogger(AggregateShipmentReverseReship_IntegrationTest.class);

	private PlainDocumentBL docActionBL;

	@Override
	protected void initialize()
	{
		super.initialize();

		// Configure and register a POJO-decoupled document engine so we can drive complete/reverse on the M_InOut.
		this.docActionBL = new PlainDocumentBL();
		PlainDocumentBL.isDocumentTableResponse = true;
		this.docActionBL.setDefaultProcessInterceptor(PlainDocumentBL.PROCESSINTERCEPTOR_CompleteDirectly);
		Services.registerService(IDocumentBL.class, docActionBL);
	}

	@Override
	protected void step50_GenerateShipment()
	{
		// Build + ship the aggregate HU exactly as the parent does (also runs the parent's validations).
		super.step50_GenerateShipment();

		assertThat(generatedShipments).as("expected exactly one generated shipment").hasSize(1);
		final I_M_InOut shipment = generatedShipments.get(0);

		//
		// Snapshot the QtyPicked rows immediately after the first (aggregate) shipment.
		dumpQtyPicked("AFTER FIRST AGGREGATE SHIPMENT");

		//
		// Complete the shipment so that it can be reversed (snapshot is taken at completion on the real stack).
		docActionBL.processEx(shipment, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		dumpQtyPicked("AFTER SHIPMENT COMPLETE");

		//
		// Reverse the shipment. This fires the M_InOut @DocValidate(AFTER_REVERSECORRECT) interceptors (HU assignment
		// removal + picking-job reopen). On the real stack the HU snapshot is also restored, replaying one HU-trx line
		// per aggregated TU through the single aggregate VHU.
		docActionBL.processEx(shipment, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
		dumpQtyPicked("AFTER SHIPMENT REVERSE");

		//
		// Assert the invariant the fix enforces, expressed exactly as the customer's partial unique index
		// (M_ShipmentSchedule_QtyPicked_UI on vhu_id, m_tu_hu_id, m_lu_hu_id, qtylu, qtytu, qtypicked, m_inoutline_id):
		// no two ACTIVE rows may share that full tuple. Without the listener-merge, an aggregate-HU snapshot replay
		// emits several rows that are identical on this tuple -> they would collide on the index on the next shipment.
		// (A +qty / -qty pair on the same VHU is NOT a collision: qtypicked is part of the index key, so they are
		// distinct index entries; only genuinely identical rows collide.)
		assertNoIndexCollidingQtyPicked();
	}

	private void assertNoIndexCollidingQtyPicked()
	{
		final List<I_M_ShipmentSchedule_QtyPicked> rows = queryAllQtyPicked();

		final Map<String, Integer> countByIndexKey = new LinkedHashMap<>();
		for (final I_M_ShipmentSchedule_QtyPicked row : rows)
		{
			if (!row.isActive())
			{
				continue;
			}

			// the full unique-index tuple (plus the schedule, which scopes the rows)
			final String key = "sched=" + row.getM_ShipmentSchedule_ID()
					+ ",vhu=" + row.getVHU_ID()
					+ ",tu=" + row.getM_TU_HU_ID()
					+ ",lu=" + row.getM_LU_HU_ID()
					+ ",qtyLU=" + row.getQtyLU().stripTrailingZeros().toPlainString()
					+ ",qtyTU=" + row.getQtyTU().stripTrailingZeros().toPlainString()
					+ ",qtyPicked=" + row.getQtyPicked().stripTrailingZeros().toPlainString()
					+ ",iol=" + row.getM_InOutLine_ID();
			countByIndexKey.merge(key, 1, Integer::sum);
		}

		logger.info("Active QtyPicked count per unique-index tuple: {}", countByIndexKey);

		for (final Map.Entry<String, Integer> e : countByIndexKey.entrySet())
		{
			assertThat(e.getValue())
					.as("expected at most ONE active QtyPicked row per unique-index tuple for " + e.getKey()
							+ " but found " + e.getValue() + " (identical rows collide on M_ShipmentSchedule_QtyPicked_UI on the next shipment)")
					.isLessThanOrEqualTo(1);
		}
	}

	private List<I_M_ShipmentSchedule_QtyPicked> queryAllQtyPicked()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.create()
				.list(I_M_ShipmentSchedule_QtyPicked.class);
	}

	private void dumpQtyPicked(final String phase)
	{
		final List<I_M_ShipmentSchedule_QtyPicked> rows = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.orderBy().addColumn(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID, Direction.Ascending, Nulls.Last).endOrderBy()
				.create()
				.list(I_M_ShipmentSchedule_QtyPicked.class);

		final StringBuilder sb = new StringBuilder();
		sb.append("\n========== M_ShipmentSchedule_QtyPicked dump [").append(phase).append("] (").append(rows.size()).append(" rows) ==========");
		sb.append("\n id | sched | vhu | tu | lu | qtyTU | qtyPicked | iol | active");
		for (final I_M_ShipmentSchedule_QtyPicked row : rows)
		{
			sb.append(String.format(
					"%n %-7d | %-6d | %-7d | %-7d | %-7d | %-5s | %-9s | %-7d | %s",
					row.getM_ShipmentSchedule_QtyPicked_ID(),
					row.getM_ShipmentSchedule_ID(),
					row.getVHU_ID(),
					row.getM_TU_HU_ID(),
					row.getM_LU_HU_ID(),
					row.getQtyTU(),
					row.getQtyPicked(),
					row.getM_InOutLine_ID(),
					row.isActive()));
		}
		sb.append("\n=========================================================================================");
		logger.info(sb.toString());
		// also to stdout so the evidence is visible in the surefire console output
		System.out.println(sb);
	}
}
