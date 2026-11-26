package de.metas.frontend_testing.wfnode;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.X_AD_WF_Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SetWFNodeFlagsCommandTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static I_AD_WF_Node createNode(final boolean isActive, final String alwaysAvailableToUser)
	{
		final I_AD_WF_Node node = InterfaceWrapperHelper.newInstance(I_AD_WF_Node.class);
		node.setName("node");
		node.setIsActive(isActive);
		node.setPP_AlwaysAvailableToUser(alwaysAvailableToUser);
		InterfaceWrapperHelper.saveRecord(node);
		return node;
	}

	private static JsonSetWFNodeFlagsResponse execute(final int adWfNodeId, final Boolean isActive, final String alwaysAvailableToUser)
	{
		return SetWFNodeFlagsCommand.builder()
				.request(JsonSetWFNodeFlagsRequest.builder()
						.adWfNodeId(adWfNodeId)
						.isActive(isActive)
						.alwaysAvailableToUser(alwaysAvailableToUser)
						.build())
				.build()
				.execute();
	}

	@Test
	public void setActiveAndAlwaysAvailable_returnsPrevious()
	{
		final I_AD_WF_Node node = createNode(false, null);

		final JsonSetWFNodeFlagsResponse response = execute(node.getAD_WF_Node_ID(), true, X_AD_WF_Node.PP_ALWAYSAVAILABLETOUSER_Yes);

		assertThat(response.getIsActive()).isFalse();
		assertThat(response.getAlwaysAvailableToUser()).isNull();
		InterfaceWrapperHelper.refresh(node);
		assertThat(node.isActive()).isTrue();
		assertThat(node.getPP_AlwaysAvailableToUser()).isEqualTo("Y");
	}

	@Test
	public void emptyAlwaysAvailable_clearsTheFlag_andOmittedFlagsStayUnchanged()
	{
		final I_AD_WF_Node node = createNode(true, "Y");

		final JsonSetWFNodeFlagsResponse response = execute(node.getAD_WF_Node_ID(), null, "");

		assertThat(response.getIsActive()).isTrue();
		assertThat(response.getAlwaysAvailableToUser()).isEqualTo("Y");
		InterfaceWrapperHelper.refresh(node);
		assertThat(node.isActive()).isTrue();
		assertThat(node.getPP_AlwaysAvailableToUser()).isNull();
	}
}
