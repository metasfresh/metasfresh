package de.metas.ui.web.handlingunits;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.model.X_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceEventQuery.EventTimeOperator;
import de.metas.handlingunits.trace.HUTraceEventQuery.RecursionMode;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.handlingunits.trace.HuTraceQueryCreator;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DocumentFilterToHuTraceQueryTest
{

	@Before
	public void init()
	{
		Check.setDefaultExClass(AdempiereException.class);
	}

	@Test
	public void createTraceQueryFromDocumentFilter_empty()
	{
		final DocumentFilter emptyFilter = DocumentFilter.builder().setFilterId("emptyFilter").build();
		final HUTraceEventQuery huTraceQuery = HuTraceQueryCreator.createTraceQueryFromDocumentFilter(emptyFilter);
		assertThat(huTraceQuery).isNotNull();
	}

	@Test
	public void createTraceQueryFromDocumentFilter()
	{
		final DocumentFilter emptyFilter = DocumentFilter.builder()
				.setFilterId("simple-M_InOut_ID-filter")
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_M_InOut_ID, Operator.EQUAL, IntegerLookupValue.of(20, "test-inout-id")))
				.build();

		final HUTraceEventQuery huTraceQuery = HuTraceQueryCreator.createTraceQueryFromDocumentFilter(emptyFilter);

		assertThat(huTraceQuery).isNotNull();
		assertThat(huTraceQuery.getRecursionMode()).isEqualTo(RecursionMode.BOTH);
		assertThat(huTraceQuery.getInOutId()).isEqualTo(20);
	}

	@Test(expected = AdempiereException.class)
	public void createTraceQueryFromDocumentFilter_duplicateFilterParam()
	{
		final DocumentFilter emptyFilter = DocumentFilter.builder()
				.setFilterId("inconsitent-filter")
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_M_InOut_ID, Operator.EQUAL, IntegerLookupValue.of(23, "test-inout-id")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_M_InOut_ID, Operator.EQUAL, IntegerLookupValue.of(24, "inconsistent-other-test-inout-id")))
				.build();
		HuTraceQueryCreator.createTraceQueryFromDocumentFilter(emptyFilter);
	}

	@Test
	public void createTraceQueryFromDocumentFilter_all_equal_params()
	{
		final DocumentFilter filter = DocumentFilter.builder()
				.setFilterId("filter")
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_AD_Org_ID, Operator.EQUAL, IntegerLookupValue.of(20, "test-AD_Org_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_C_DocType_ID, Operator.EQUAL, IntegerLookupValue.of(30, "test-C_DocType_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_DocStatus, Operator.EQUAL, StringLookupValue.of("CO", "test-DocStatus")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_HUTraceType, Operator.EQUAL, StringLookupValue.of(X_M_HU_Trace.HUTRACETYPE_MATERIAL_PICKING, "test-HUTraceType")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_M_HU_ID, Operator.EQUAL, IntegerLookupValue.of(60, "test-M_HU_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_M_HU_Trace_ID, Operator.EQUAL, IntegerLookupValue.of(70, "test-M_HU_Trace_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_M_HU_Trx_Line_ID, Operator.EQUAL, IntegerLookupValue.of(80, "test-M_HU_Trx_Line_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_M_InOut_ID, Operator.EQUAL, IntegerLookupValue.of(90, "test-M_InOut_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_M_Movement_ID, Operator.EQUAL, IntegerLookupValue.of(100, "test-M_Movement_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_M_Product_ID, Operator.EQUAL, IntegerLookupValue.of(110, "test-M_Product_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_M_ShipmentSchedule_ID, Operator.EQUAL, IntegerLookupValue.of(120, "test-M_ShipmentSchedule_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_PP_Cost_Collector_ID, Operator.EQUAL, IntegerLookupValue.of(130, "test-PP_Cost_Collector_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_PP_Order_ID, Operator.EQUAL, IntegerLookupValue.of(140, "test-PP_Order_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_EventTime, Operator.EQUAL, TimeUtil.parseTimestamp("2017-10-13")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_VHU_ID, Operator.EQUAL, IntegerLookupValue.of(160, "test-VHU_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_VHU_Source_ID, Operator.EQUAL, IntegerLookupValue.of(170, "test-VHU_Source_ID")))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_M_HU_Trace.COLUMNNAME_VHUStatus, Operator.EQUAL, StringLookupValue.of(X_M_HU_Trace.VHUSTATUS_Active, "test-VHUStatus")))
				.build();

		final HUTraceEventQuery huTraceQuery = HuTraceQueryCreator.createTraceQueryFromDocumentFilter(filter);

		assertThat(huTraceQuery).isNotNull();
		assertThat(huTraceQuery.getRecursionMode()).isEqualTo(RecursionMode.BOTH);
		assertThat(huTraceQuery.getOrgId()).isEqualTo(20);
		assertThat(huTraceQuery.getDocTypeId().getAsInt()).isEqualTo(30);
		assertThat(huTraceQuery.getDocStatus()).isEqualTo("CO");
		assertThat(huTraceQuery.getType().toString()).isEqualTo(X_M_HU_Trace.HUTRACETYPE_MATERIAL_PICKING);
		assertThat(huTraceQuery.getTopLevelHuId()).isEqualTo(60);
		assertThat(huTraceQuery.getHuTraceEventId().getAsInt()).isEqualTo(70);
		assertThat(huTraceQuery.getHuTrxLineId()).isEqualTo(80);
		assertThat(huTraceQuery.getInOutId()).isEqualTo(90);
		assertThat(huTraceQuery.getMovementId()).isEqualTo(100);
		assertThat(huTraceQuery.getProductId()).isEqualTo(110);
		assertThat(huTraceQuery.getShipmentScheduleId()).isEqualTo(120);
		assertThat(huTraceQuery.getPpCostCollectorId()).isEqualTo(130);
		assertThat(huTraceQuery.getPpOrderId()).isEqualTo(140);
		assertThat(huTraceQuery.getVhuId()).isEqualTo(160);
		assertThat(huTraceQuery.getVhuSourceId()).isEqualTo(170);
		assertThat(huTraceQuery.getVhuStatus()).isEqualTo(X_M_HU_Trace.VHUSTATUS_Active);
		assertThat(huTraceQuery.getEventTime()).isEqualTo(TimeUtil.parseTimestamp("2017-10-13").toInstant());
	}

	@Test
	public void createTraceQueryFromDocumentFilter_eventdate()
	{
		final Date date = TimeUtil.parseTimestamp("2017-10-13");
		final Date dateTo = TimeUtil.parseTimestamp("2017-10-15");
		final DocumentFilter filter = DocumentFilter.builder()
				.setFilterId("filter")
				.addParameter(createEventTimeBetweenParameter(date, dateTo))
				.build();

		final HUTraceEventQuery huTraceQuery = HuTraceQueryCreator.createTraceQueryFromDocumentFilter(filter);

		assertThat(huTraceQuery.getRecursionMode()).isEqualTo(RecursionMode.BOTH);
		assertThat(huTraceQuery.getEventTime()).isEqualTo(date.toInstant());
		assertThat(huTraceQuery.getEventTimeOperator()).isEqualTo(EventTimeOperator.BETWEEN);
		assertThat(huTraceQuery.getEventTimeTo()).isEqualTo(dateTo.toInstant());
	}

	private DocumentFilterParam createEventTimeBetweenParameter(final Date date, final Date dateTo)
	{
		return DocumentFilterParam.builder()
				.setFieldName(I_M_HU_Trace.COLUMNNAME_EventTime)
				.setOperator(Operator.BETWEEN)
				.setValue(date)
				.setValueTo(dateTo).build();
	}
}
