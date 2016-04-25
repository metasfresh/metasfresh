package test.integration.commission.helper;

/*
 * #%L
 * de.metas.commission.ait
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


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;

import de.metas.adempiere.ait.helper.GridWindowHelper;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_Cond;
import de.metas.commission.model.I_C_Sponsor_CondLine;
import de.metas.commission.model.X_C_Sponsor_CondLine;

public class SponsorCondHelper
{
	private final Helper helper;

	private int sponsorId;

	private List<SponsorCondLineHelper> lines = new ArrayList<SponsorCondHelper.SponsorCondLineHelper>();

	private I_C_Sponsor_Cond createdCond;

	SponsorCondHelper(final Helper parent)
	{
		this.helper = parent;
	}

	public SponsorCondHelper setC_Sponsor_ID(int c_Sponsor_ID)
	{
		sponsorId = c_Sponsor_ID;
		return this;
	}

	public SponsorCondLineHelper addLine()
	{
		final SponsorCondLineHelper newLine = new SponsorCondLineHelper(this);

		lines.add(newLine);
		return newLine;
	}

	public SponsorCondHelper create()
	{
		final GridWindowHelper gridWindowHelper = helper
				.mkGridWindowHelper()
				.openTabForTable(I_C_Sponsor.Table_Name, true);

		Check.assume(sponsorId > 0, "sponsorId was set to a value > 0");
		gridWindowHelper
				.selectTab(I_C_Sponsor.Table_Name)
				.selectRecordById(sponsorId)
				.selectTab(I_C_Sponsor_Cond.Table_Name)
				.newRecord();

		createdCond = gridWindowHelper.getGridTabInterface(I_C_Sponsor_Cond.class);
		assertThat(createdCond + " should automatically have the correct C_Sponsor_ID",
				createdCond.getC_Sponsor_ID(), equalTo(sponsorId));
		assertThat(createdCond + " should have CondChangeDate=@#Date@",
				createdCond.getCondChangeDate(), equalTo(Env.getContextAsDate(Env.getCtx(), "#Date")));

		createdCond.setDescription("Created by " + getClass().getName() + " at " + SystemTime.asTimestamp());
		gridWindowHelper.validateLookups().save();

		gridWindowHelper.selectTab(I_C_Sponsor_CondLine.Table_Name);

		for (final SponsorCondLineHelper line : lines)
		{
			final I_C_Sponsor_CondLine condLine =
					gridWindowHelper
							.newRecord()
							.getGridTabInterface(I_C_Sponsor_CondLine.class);

			condLine.setSponsorSalesRepType(line.sponsorSalesRepType);
			condLine.setC_AdvComSystem_ID(line.comSystemId);
			condLine.setC_AdvCommissionCondition_ID(line.commissionConditionId);
			condLine.setDateFrom(line.dateFrom);
			condLine.setDateTo(line.dateTo);
			condLine.setC_BPartner_ID(line.bPartnerId);
			condLine.setC_Sponsor_Parent_ID(line.sponsorParentId);

			gridWindowHelper.save();
		}

		return this;
	}

	public void complete()
	{
		final GridWindowHelper gridWindowHelper =
				helper
						.mkGridWindowHelper()
						.openTabForTable(I_C_Sponsor.Table_Name, true);

		Check.assume(sponsorId > 0, "sponsorId was set to a value > 0");
		gridWindowHelper
				.selectTab(I_C_Sponsor.Table_Name)
				.selectRecordById(sponsorId)
				.selectTab(I_C_Sponsor_Cond.Table_Name)
				.selectRecordById(createdCond.getC_Sponsor_Cond_ID())
				.runProcess(I_C_Sponsor_Cond.COLUMNNAME_DocAction);

		gridWindowHelper.refresh();
	}

	public static class SponsorCondLineHelper
	{
		private final SponsorCondHelper condHelper;

		private String sponsorSalesRepType;

		private Timestamp dateFrom;

		private Timestamp dateTo;

		private int comSystemId;

		private int commissionConditionId;

		private int bPartnerId;

		private int sponsorParentId;

		private SponsorCondLineHelper(final SponsorCondHelper condHelper)
		{
			assertThat("Param 'condHelper'", condHelper, notNullValue());

			this.condHelper = condHelper;
		}

		public SponsorCondLineHelper setSponsorSalesRepType(String sponsorSalesRepType)
		{
			this.sponsorSalesRepType = sponsorSalesRepType;
			return this;
		}

		public SponsorCondLineHelper setDateFrom(Timestamp dateFrom)
		{
			this.dateFrom = dateFrom;
			return this;
		}

		public SponsorCondLineHelper setDateTo(Timestamp dateTo)
		{
			this.dateTo = dateTo;
			return this;
		}

		public SponsorCondLineHelper setC_AdvComSystem_ID(int comSystemId)
		{
			this.comSystemId = comSystemId;
			return this;
		}

		public SponsorCondLineHelper setC_AdvCommissionCondition_ID(int commissionConditionId)
		{
			this.commissionConditionId = commissionConditionId;
			return this;
		}

		public SponsorCondLineHelper setC_BPartner_ID(int bPartnerId)
		{
			this.bPartnerId = bPartnerId;
			return this;
		}

		public SponsorCondHelper addLineDone()
		{
			// validate
			assertThat("sponsorSalesRepType", sponsorSalesRepType, notNullValue());

			assertThat("dateFrom", dateFrom, notNullValue());
			assertThat("dateTo", dateTo, notNullValue());
			assertThat("comSystemId", comSystemId, greaterThan(0));

			if (sponsorSalesRepType.equals(X_C_Sponsor_CondLine.SPONSORSALESREPTYPE_Hierarchie))
			{
				assertThat("sponsorParentId", sponsorParentId, greaterThan(0));
			}

			// Note: commissionConditionId and bPartnerId may be 0, even if sponsorSalesRepType = 'S'

			return condHelper;
		}

		public SponsorCondLineHelper setC_Sponsor_Parent_ID(int sponsorParentId)
		{
			this.sponsorParentId = sponsorParentId;
			return this;
		}
	}
}
