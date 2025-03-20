package de.metas.manufacturing.workflows_api.rest_api.json;

import de.metas.global_qrcodes.JsonDisplayableQRCode;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonFinishGoodsReceiveQRCodesGenerateResponse
{
	List<JsonDisplayableQRCode> qrCodes;
}
