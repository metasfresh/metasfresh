package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.request.JsonExpectations;
import de.metas.frontend_testing.expectations.request.JsonExpectationsResponse;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;

public class AssertExpectationsCommand
{
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;

	@NonNull private final JsonExpectations expectations;

	@Builder
	private AssertExpectationsCommand(
			@NonNull final AssertExpectationsCommandServices services,
			@NonNull final JsonExpectations expectations)
	{
		this.services = services;
		this.expectations = expectations;
		this.context = createContext(expectations);
	}

	private static MasterdataContext createContext(@NonNull final JsonExpectations expectations)
	{
		final MasterdataContext context = new MasterdataContext();

		final JsonCreateMasterdataResponse masterdata = expectations.getMasterdata();
		if (masterdata != null)
		{
			masterdata.getBpartners().forEach((identifierStr, bpartner) -> context.putIdentifier(Identifier.ofString(identifierStr), bpartner.getId()));
			masterdata.getProducts().forEach((identifierStr, product) -> context.putIdentifier(Identifier.ofString(identifierStr), product.getId()));
			masterdata.getHandlingUnits().forEach((identifierStr, handlingUnit) -> context.putIdentifier(Identifier.ofString(identifierStr), HuId.ofObject(handlingUnit.getHuId())));
		}

		return context;
	}

	public JsonExpectationsResponse execute() throws Exception
	{
		if (expectations.getPickings() != null)
		{
			AssertPickingExpectationsCommand.builder()
					.services(services)
					.context(context)
					.expectations(expectations.getPickings())
					.build()
					.execute();
		}
		if (expectations.getHus() != null)
		{
			AssertHUExpectationsCommand.builder()
					.services(services)
					.context(context)
					.expectations(expectations.getHus())
					.build()
					.execute();
		}

		return JsonExpectationsResponse.builder().build();
	}

}
