/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.dimension;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.sectionCode.SectionCodeId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

@Component
public class InvoiceLineDimensionFactory implements DimensionFactory<I_C_InvoiceLine>
{
	@Override
	public String getHandledTableName()
	{
		return I_C_InvoiceLine.Table_Name;
	}

	@Override
	@NonNull
	public Dimension getFromRecord(@NonNull final I_C_InvoiceLine record)
	{

		return Dimension.builder()
				.projectId(ProjectId.ofRepoIdOrNull(record.getC_Project_ID()))
				.campaignId(record.getC_Campaign_ID())
				.activityId(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID()))
				.salesOrderId(OrderId.ofRepoIdOrNull(record.getC_OrderSO_ID()))
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(record.getM_SectionCode_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.bpartnerId2(BPartnerId.ofRepoIdOrNull(record.getC_BPartner2_ID()))
				.userElementNumber1(InterfaceWrapperHelper.getValueAsBigDecimalOrNull(record, I_C_InvoiceLine.COLUMNNAME_UserElementNumber1))
				.userElementNumber2(InterfaceWrapperHelper.getValueAsBigDecimalOrNull(record, I_C_InvoiceLine.COLUMNNAME_UserElementNumber2))
				.userElementString1(record.getUserElementString1())
				.userElementString2(record.getUserElementString2())
				.userElementString3(record.getUserElementString3())
				.userElementString4(record.getUserElementString4())
				.userElementString5(record.getUserElementString5())
				.userElementString6(record.getUserElementString6())
				.userElementString7(record.getUserElementString7())
				.user1_ID(record.getUser1_ID())
				.user2_ID(record.getUser2_ID())
				.userElementDate1(TimeUtil.asInstant(record.getUserElementDate1()))
				.userElementDate2(TimeUtil.asInstant(record.getUserElementDate2()))
				.harvestingYearAndCalendarId(YearAndCalendarId.ofRepoIdOrNull(record.getC_Harvesting_Calendar_ID(), record.getHarvesting_Year_ID()))
				.build();
	}

	@Override
	public void updateRecord(@NonNull final I_C_InvoiceLine record, @NonNull final Dimension from)
	{
		record.setC_Project_ID(ProjectId.toRepoId(from.getProjectId()));
		record.setC_Campaign_ID(from.getCampaignId());
		record.setC_Activity_ID(ActivityId.toRepoId(from.getActivityId()));
		record.setC_OrderSO_ID(OrderId.toRepoId(from.getSalesOrderId()));
		record.setM_SectionCode_ID(SectionCodeId.toRepoId(from.getSectionCodeId()));
		record.setM_Product_ID(ProductId.toRepoId(from.getProductId()));
		record.setC_BPartner2_ID(BPartnerId.toRepoId(from.getBpartnerId2()));

		updateRecordUserElements(record, from);
	}

	@Override
	public void updateRecordUserElements(@NonNull final I_C_InvoiceLine record, @NonNull final Dimension from)
	{
		record.setUserElementNumber1(from.getUserElementNumber1());
		record.setUserElementNumber2(from.getUserElementNumber2());
		record.setUserElementString1(from.getUserElementString1());
		record.setUserElementString2(from.getUserElementString2());
		record.setUserElementString3(from.getUserElementString3());
		record.setUserElementString4(from.getUserElementString4());
		record.setUserElementString5(from.getUserElementString5());
		record.setUserElementString6(from.getUserElementString6());
		record.setUserElementString7(from.getUserElementString7());
		record.setUser1_ID(from.getUser1_ID());
		record.setUser2_ID(from.getUser2_ID());
		record.setUserElementDate1(TimeUtil.asTimestamp(from.getUserElementDate1()));
		record.setUserElementDate2(TimeUtil.asTimestamp(from.getUserElementDate2()));

		final YearAndCalendarId harvestingYearAndCalendarId = from.getHarvestingYearAndCalendarId();
		record.setC_Harvesting_Calendar_ID(harvestingYearAndCalendarId != null ? harvestingYearAndCalendarId.calendarId().getRepoId() : -1);
		record.setHarvesting_Year_ID(harvestingYearAndCalendarId != null ? harvestingYearAndCalendarId.yearId().getRepoId() : -1);

	}
}
