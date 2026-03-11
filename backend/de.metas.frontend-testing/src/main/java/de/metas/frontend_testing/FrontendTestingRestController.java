package de.metas.frontend_testing;

import de.metas.Profiles;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.frontend_testing.expectations.AssertExpectationsCommand;
import de.metas.frontend_testing.expectations.request.JsonExpectations;
import de.metas.frontend_testing.expectations.request.JsonExpectationsResponse;
import de.metas.frontend_testing.masterdata.CreateMasterdataCommand;
import de.metas.frontend_testing.masterdata.CreateMasterdataCommandSupportingServices;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataRequest;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.InOutId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.permissions2.PermissionNotGrantedException;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

@RequestMapping(FrontendTestingRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class FrontendTestingRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/frontendTesting";
	private static final String SYSCONFIG_Enabled = "frontend.testing";

	@NonNull private final Logger logger = LogManager.getLogger(FrontendTestingRestController.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final UserAuthTokenFilterConfiguration userAuthTokenFilterConfiguration;
	@NonNull private final CreateMasterdataCommandSupportingServices services;

	private boolean isEnabled()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_Enabled, false);
	}

	private void assertEnabled()
	{
		if (!isEnabled())
		{
			throw new PermissionNotGrantedException(TranslatableStrings.constant("Frontend testing REST endpoints are disabled"));
		}
	}

	@PostConstruct
	public void postConstruct()
	{
		userAuthTokenFilterConfiguration.doNotAuthenticatePathsContaining(ENDPOINT);

		if (isEnabled())
		{
			logger.warn("\n"
					+ "\n************************************************************************************************************************"
					+ "\n Frontend testing REST endpoints are active and accessible without login!"
					+ "\n************************************************************************************************************************"
			);
		}
	}

	private <T> T callInContext(@NonNull final Supplier<T> callable)
	{
		assertEnabled();

		final Properties ctx = Env.copyCtx(Env.getCtx());
		Env.setLoggedUserId(ctx, UserId.SYSTEM);
		Env.setClientId(ctx, ClientId.METASFRESH);
		Env.setOrgId(ctx, OrgId.MAIN);

		try (final IAutoCloseable ignored = Env.switchContext(ctx))
		{
			return callable.get();
		}
	}

	@PostMapping
	public JsonCreateMasterdataResponse createMasterdata(@RequestBody final JsonCreateMasterdataRequest request)
	{
		return callInContext(
				() -> CreateMasterdataCommand.builder()
						.services(services)
						.request(request)
						.build().execute()
		);
	}

	@PostMapping("expect")
	public ResponseEntity<JsonExpectationsResponse> expect(@RequestBody @NonNull final JsonExpectations jsonExpectations) throws Exception
	{
		assertEnabled();

		return AssertExpectationsCommand.builder()
				.services(services.assertExpectationsCommandServices)
				.expectations(jsonExpectations)
				.build()
				.execute();
	}

	@PostMapping("reverseShipment")
	public Map<String, Object> reverseShipment(@RequestBody @NonNull final JsonReverseShipmentRequest request)
	{
		return callInContext(() -> {
			final IQueryBL queryBL = Services.get(IQueryBL.class);
			final IDocumentBL documentBL = Services.get(IDocumentBL.class);

			final I_M_InOut shipment;
			if (request.getShipmentId() != null)
			{
				shipment = InterfaceWrapperHelper.load(request.getShipmentId(), I_M_InOut.class);
			}
			else if (request.getSalesOrderId() != null)
			{
				// Find shipment via order lines → inout lines → inout
				final InOutId shipmentId = queryBL.createQueryBuilder(I_M_InOutLine.class)
						.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID,
								queryBL.createQueryBuilder(org.compiere.model.I_C_OrderLine.class)
										.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_C_Order_ID, request.getSalesOrderId())
										.create())
						.andCollect(I_M_InOutLine.COLUMN_M_InOut_ID, I_M_InOut.class)
						.addEqualsFilter(I_M_InOut.COLUMNNAME_DocStatus, "CO")
						.addEqualsFilter(I_M_InOut.COLUMNNAME_IsSOTrx, true)
						.create()
						.firstIdOnly(InOutId::ofRepoIdOrNull);

				if (shipmentId == null)
				{
					throw new AdempiereException("No completed shipment found for salesOrderId=" + request.getSalesOrderId());
				}
				shipment = InterfaceWrapperHelper.load(shipmentId, I_M_InOut.class);
			}
			else
			{
				throw new AdempiereException("Either shipmentId or salesOrderId must be provided");
			}

			InterfaceWrapperHelper.refresh(shipment);
			documentBL.processEx(shipment, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);

			final Map<String, Object> result = new HashMap<>();
			result.put("shipmentId", shipment.getM_InOut_ID());
			result.put("documentNo", shipment.getDocumentNo());
			result.put("docStatus", shipment.getDocStatus());
			result.put("reversalId", shipment.getReversal_ID());
			return result;
		});
	}

	@Value
	@lombok.Builder
	@lombok.extern.jackson.Jacksonized
	public static class JsonReverseShipmentRequest
	{
		Integer shipmentId;
		Integer salesOrderId;
	}
}
