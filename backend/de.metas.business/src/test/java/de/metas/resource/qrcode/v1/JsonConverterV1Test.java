package de.metas.resource.qrcode.v1;

import de.metas.product.ResourceId;
import de.metas.resource.qrcode.ResourceQRCode;
import org.compiere.model.X_S_Resource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonConverterV1Test
{
	@Test
	void toGlobalQRCode_fromGlobalQRCode()
	{
		final ResourceQRCode qrCode = ResourceQRCode.builder()
				.resourceId(ResourceId.ofRepoId(123))
				.resourceType(X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant)
				.caption("Plant 1")
				.build();

		final ResourceQRCode qrCode2 = JsonConverterV1.fromGlobalQRCode(JsonConverterV1.toGlobalQRCode(qrCode));

		assertThat(qrCode2).isEqualTo(qrCode);
	}
}