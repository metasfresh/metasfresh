/*
 * #%L
 * de.metas.business
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

package de.metas.costrevaluation.interceptor;

import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICostingService;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costrevaluation.CostRevaluationDetailType;
import de.metas.costrevaluation.CostRevaluationRepository;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.costrevaluation.RevaluationSource;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.model.I_M_CostRevaluation_Detail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * The line's {@code DeltaAmt} follows its own quantity and prices on every save of a manual revaluation in draft.
 */
class M_CostRevaluationLineTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final CostRevaluationService costRevaluationService = new CostRevaluationService(
				new CostRevaluationRepository(),
				mock(ICurrentCostsRepository.class),
				mock(ICostingService.class),
				mock(ICostDetailRepository.class),
				mock(ICostElementRepository.class));
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new M_CostRevaluationLine(costRevaluationService));
	}

	@Test
	void newLine_deltaAmtEqualsQtyTimesPriceDifference()
	{
		final I_M_CostRevaluation header = createHeader(RevaluationSource.Manual, DocStatus.Drafted);

		final I_M_CostRevaluationLine line = createLine(header, "100", "10", "15");
		assertThat(line.getDeltaAmt()).isEqualByComparingTo("500"); // 100 x (15 - 10)

		final I_M_CostRevaluationLine zeroStockLine = createLine(header, "0", "7", "9");
		assertThat(zeroStockLine.getDeltaAmt()).isEqualByComparingTo("0"); // 0 x (9 - 7)
	}

	@Test
	void newCostPriceEdit_recomputesDeltaAndResetsEvaluation()
	{
		final I_M_CostRevaluation header = createHeader(RevaluationSource.Manual, DocStatus.Drafted);
		final I_M_CostRevaluationLine line = createLine(header, "100", "10", "15");
		markEvaluatedWithDetail(line);

		line.setNewCostPrice(new BigDecimal("16"));
		saveRecord(line);

		refresh(line);
		assertThat(line.getDeltaAmt()).isEqualByComparingTo("600"); // 100 x (16 - 10)
		assertThat(line.isRevaluated()).isFalse();
		assertThat(countDetails(line)).isZero();
	}

	@Test
	void qtyOrPriceRefresh_recomputesDeltaWithoutReset()
	{
		final I_M_CostRevaluation header = createHeader(RevaluationSource.Manual, DocStatus.Drafted);
		final I_M_CostRevaluationLine line = createLine(header, "100", "10", "15");
		createDetail(line);

		line.setCurrentQty(new BigDecimal("80"));
		saveRecord(line);

		refresh(line);
		assertThat(line.getDeltaAmt()).isEqualByComparingTo("400"); // 80 x (15 - 10)
		assertThat(line.isRevaluated()).isFalse();
		assertThat(countDetails(line)).isEqualTo(1);
	}

	@Test
	void evaluatedLine_qtyAndPriceWrittenByTheEvaluation_keepsEvaluationAndDetails()
	{
		final I_M_CostRevaluation header = createHeader(RevaluationSource.Manual, DocStatus.Drafted);
		final I_M_CostRevaluationLine line = createLine(header, "100", "10", "15");
		markEvaluatedWithDetail(line);

		line.setCurrentQty(new BigDecimal("80"));
		line.setCurrentCostPrice(new BigDecimal("12"));
		saveRecord(line);

		refresh(line);
		assertThat(line.getDeltaAmt()).isEqualByComparingTo("240"); // 80 x (15 - 12)
		assertThat(line.isRevaluated()).isTrue();
		assertThat(countDetails(line)).isEqualTo(1);
	}

	@Test
	void copyFromCostElement_lineIsLeftAsWritten()
	{
		final I_M_CostRevaluation header = createHeader(RevaluationSource.CopyFromCostElement, DocStatus.Drafted);
		final I_M_CostRevaluationLine line = createLine(header, "100", "10", "15");
		assertThat(line.getDeltaAmt()).isEqualByComparingTo("0");

		markEvaluatedWithDetail(line);
		line.setNewCostPrice(new BigDecimal("16"));
		saveRecord(line);

		refresh(line);
		assertThat(line.getDeltaAmt()).isEqualByComparingTo("0");
		assertThat(line.isRevaluated()).isTrue();
		assertThat(countDetails(line)).isEqualTo(1);
	}

	@Test
	void inProgressRevaluation_newCostPriceEdit_recomputesDeltaAndResetsEvaluation()
	{
		final I_M_CostRevaluation header = createHeader(RevaluationSource.Manual, DocStatus.InProgress);
		final I_M_CostRevaluationLine line = createLine(header, "100", "10", "15");
		assertThat(line.getDeltaAmt()).isEqualByComparingTo("500"); // 100 x (15 - 10)
		markEvaluatedWithDetail(line);

		line.setNewCostPrice(new BigDecimal("16"));
		saveRecord(line);

		refresh(line);
		assertThat(line.getDeltaAmt()).isEqualByComparingTo("600"); // 100 x (16 - 10)
		assertThat(line.isRevaluated()).isFalse();
		assertThat(countDetails(line)).isZero();
	}

	@Test
	void reversedRevaluation_lineIsLeftAsWritten()
	{
		final I_M_CostRevaluation header = createHeader(RevaluationSource.Manual, DocStatus.Drafted);
		final I_M_CostRevaluationLine line = createLine(header, "100", "10", "15");
		markEvaluatedWithDetail(line);

		header.setDocStatus(DocStatus.Reversed.getCode());
		saveRecord(header);

		line.setNewCostPrice(new BigDecimal("16"));
		saveRecord(line);

		refresh(line);
		assertThat(line.getDeltaAmt()).isEqualByComparingTo("500");
		assertThat(line.isRevaluated()).isTrue();
		assertThat(countDetails(line)).isEqualTo(1);
	}

	@Test
	void completedRevaluation_bookedValuesAreLeftAsWritten()
	{
		final I_M_CostRevaluation header = createHeader(RevaluationSource.Manual, DocStatus.Drafted);
		final I_M_CostRevaluationLine line = createLine(header, "100", "10", "15");
		markEvaluatedWithDetail(line);

		header.setDocStatus(DocStatus.Completed.getCode());
		saveRecord(header);

		// what posting writes back: the stock on hand and the cost price it booked from, and the booked amount
		line.setCurrentQty(new BigDecimal("80"));
		line.setCurrentCostPrice(new BigDecimal("12"));
		line.setDeltaAmt(new BigDecimal("123"));
		saveRecord(line);

		refresh(line);
		assertThat(line.getDeltaAmt()).isEqualByComparingTo("123");
		assertThat(line.isRevaluated()).isTrue();
		assertThat(countDetails(line)).isEqualTo(1);
	}

	private static I_M_CostRevaluation createHeader(@NonNull final RevaluationSource source, @NonNull final DocStatus docStatus)
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		record.setC_AcctSchema_ID(1);
		record.setM_CostElement_ID(1);
		record.setRevaluationSource(source.getCode());
		record.setDocStatus(docStatus.getCode());
		final Timestamp dateAcct = Timestamp.from(Instant.parse("2024-03-04T00:00:00Z"));
		record.setDateAcct(dateAcct);
		record.setEvaluationStartDate(dateAcct);
		saveRecord(record);
		return record;
	}

	private static I_M_CostRevaluationLine createLine(
			@NonNull final I_M_CostRevaluation header,
			@NonNull final String currentQty,
			@NonNull final String currentCostPrice,
			@NonNull final String newCostPrice)
	{
		final I_M_CostRevaluationLine record = newInstance(I_M_CostRevaluationLine.class);
		record.setM_CostRevaluation_ID(header.getM_CostRevaluation_ID());
		record.setCurrentQty(new BigDecimal(currentQty));
		record.setCurrentCostPrice(new BigDecimal(currentCostPrice));
		record.setNewCostPrice(new BigDecimal(newCostPrice));
		saveRecord(record);
		return record;
	}

	private static void markEvaluatedWithDetail(@NonNull final I_M_CostRevaluationLine line)
	{
		line.setIsRevaluated(true);
		saveRecord(line);
		createDetail(line);
	}

	private static void createDetail(@NonNull final I_M_CostRevaluationLine line)
	{
		final I_M_CostRevaluation_Detail detail = newInstance(I_M_CostRevaluation_Detail.class);
		detail.setM_CostRevaluation_ID(line.getM_CostRevaluation_ID());
		detail.setM_CostRevaluationLine_ID(line.getM_CostRevaluationLine_ID());
		detail.setRevaluationType(CostRevaluationDetailType.CurrentCostBeforeRevaluation.getCode());
		detail.setDeltaAmt(line.getDeltaAmt());
		saveRecord(detail);
	}

	private static int countDetails(@NonNull final I_M_CostRevaluationLine line)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostRevaluation_Detail.class)
				.addEqualsFilter(I_M_CostRevaluation_Detail.COLUMNNAME_M_CostRevaluationLine_ID, line.getM_CostRevaluationLine_ID())
				.create()
				.count();
	}
}
