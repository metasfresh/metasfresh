package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.rest_api.createfromlabel.JsonHUProposal.JsonRejection;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.scannable_code.ScannedCode;
import de.metas.uom.IUOMDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** A rejection is always answered as a proposal that carries the rejection; converting it must never throw. */
class ProposeHUFromScannedCodeCommandTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void rejectionWithoutErrorCode_isAnsweredAsRejectionWithMessage()
	{
		// AdempiereException.getErrorCode() is @Nullable
		final LabelProductResolver resolverRejectingWithoutErrorCode = new LabelProductResolver(HUQRCodesService.newInstanceForUnitTesting(), Services.get(IProductBL.class))
		{
			@Override
			public LabelProduct resolve(@NonNull final ScannedCode scannedCode, @NonNull final ClientId clientId)
			{
				throw new LabelRejectionException(CreateFromLabelMessages.NOT_A_PRODUCT_LABEL, "X")
				{
					@Override
					public String getErrorCode() {return null;}
				};
			}
		};

		final JsonHUProposal proposal = ProposeHUFromScannedCodeCommand.builder()
				.productBL(Services.get(IProductBL.class))
				.attributeDAO(Services.get(IAttributeDAO.class))
				.warehouseDAO(Services.get(IWarehouseDAO.class))
				.labelProductResolver(resolverRejectingWithoutErrorCode)
				.labelTargetLocatorResolver(new LabelTargetLocatorResolver(
						new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository()),
						Services.get(IWarehouseDAO.class),
						Services.get(IOrgDAO.class)))
				.labelQtyAndWeightService(new LabelQtyAndWeightService(Services.get(IProductBL.class), Services.get(IUOMDAO.class)))
				.clientId(ClientId.METASFRESH)
				.userId(UserId.ofRepoId(1_000_001))
				.orgId(OrgId.ofRepoId(1_000_010))
				.scannedCode(ScannedCode.ofString("ANY"))
				.adLanguage("en_US")
				.build()
				.execute();

		final JsonRejection rejection = proposal.getRejection();
		assertThat(rejection).isNotNull();
		assertThat(rejection.getMessage()).isNotBlank();
		assertThat(rejection.getErrorCode()).isEqualTo(CreateFromLabelMessages.NOT_A_PRODUCT_LABEL.toAD_Message());
	}
}
