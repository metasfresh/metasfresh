package de.metas.commission.dashboard;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Properties;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.adempiere.util.Services;
import org.compiere.model.MBPartner;
import org.compiere.model.MDiscountSchema;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.test.POTest;
import de.metas.commission.custom.type.ISalesRefFactCollector;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.MCAdvCommissionSalaryGroup;
import de.metas.commission.model.MCSponsor;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.impl.SponsorBL;
import de.metas.web.component.BeanInfoPanel;

public class DPAgentInfoTests
{

	@Mocked 
	MBPartner bPartner;
	
	@Mocked
	MCSponsor sponsor;
	
	@Mocked
	SponsorBL sponsorBL;
	
	
	/**
	 * If a given sponsor has a fixed rank, this rank needs to be displayed. Also, the valid-dates make no sense and
	 * should be left empty.
	 */
	@Test
	public void fixedRank()
	{
		// specific setup
		sponsorHasRankAndDS("ManualRank", true, "DiscountSchemaOfRank");

		// invocation
		final DPAgentInfo agentInfo = new DPAgentInfo();
		
		// verification of beanInfoPanel
		final BeanInfoPanel beanInfoPanel = agentInfo.getBeanInfoPanel();
		
		assertEquals(beanInfoPanel.getValue(DPAgentInfo.ADV_COM_SALARY_GROUP_VALUE), "ManualRank");
		assertEquals(beanInfoPanel.getValue(DPAgentInfo.DISCOUNT), "DiscountSchemaOfRank");
		
		assertEquals(DPAgentInfo.MSG_DATE_NOT_APPLICABLE,
				beanInfoPanel.getValue(DPAgentInfo.ADV_COM_SALARY_GROUP_DATE_FROM));
		assertEquals(DPAgentInfo.MSG_DATE_NOT_APPLICABLE,
				beanInfoPanel.getValue(DPAgentInfo.ADV_COM_SALARY_GROUP_DATE_TO));
	}
	
	private void sponsorHasRankAndDS(final String rankName, final boolean isManualRank, final String discountSchemaName)
	{
		new NonStrictExpectations()
		{
			MCAdvCommissionSalaryGroup rank;
			ISalesRefFactCollector srfCollector;
			MDiscountSchema ds;
			{
				sponsor.isManualRank(); result = isManualRank;
				sponsor.getC_AdvCommissionSalaryGroup(); result = rank;
				rank.getName(); result = rankName;
				
				sponsorBL.retrieveSalesRepFactCollector(Env.getCtx(), sponsor, POTest.DATE, null);
				result = srfCollector;
				
				srfCollector.retrieveDiscountSchema(rank, sponsor, POTest.DATE); result = ds;
				ds.getName(); result = discountSchemaName;
		}};
	}

	@Before
	public void setup()
	{
		Env.setContext(Env.getCtx(), "#Date", POTest.DATE);
		
		// registering our sponsorBL-mock
		Services.registerService(ISponsorBL.class, sponsorBL);
		
		// setting up the mocks for user, bPartner and sponsor
		new NonStrictExpectations()
		{
			MUser user;
			
			@SuppressWarnings("unused") MSysConfig sysConfigUnused;
			
			@SuppressWarnings("unused") Msg msgUnused;
			
			{
				MUser.get( (Properties)any, anyInt); result = user;
				user.getC_BPartner(); result = bPartner;
								
				MCSponsor.retrieveForSalesRepAt((I_C_BPartner)any, POTest.DATE); result = Collections.singletonList(sponsor);
				
				MSysConfig.getBooleanValue("LOGINDATE_AUTOUPDATE", anyBoolean, POTest.AD_CLIENT_ID); result = false;
				
				Msg.getMsg((Properties)any, DPAgentInfo.MSG_DATE_NOT_APPLICABLE); result = DPAgentInfo.MSG_DATE_NOT_APPLICABLE;
			}
		};
	}

}
