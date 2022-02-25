package de.metas.devices.webui.process;

import com.google.common.collect.ImmutableList;
import de.metas.device.accessor.DeviceAccessor;
import de.metas.device.accessor.DeviceAccessorsHubFactory;
import de.metas.device.accessor.DeviceId;
import de.metas.device.accessor.qrcode.DeviceQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.util.StringUtils;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.stream.Stream;

public class PrintDeviceQRCodes extends JavaProcess
{
	private final DeviceAccessorsHubFactory deviceAccessorsHubFactory = SpringContextHolder.instance.getBean(DeviceAccessorsHubFactory.class);
	private final GlobalQRCodeService globalQRCodeService = SpringContextHolder.instance.getBean(GlobalQRCodeService.class);

	public static final String PARAM_DeviceId = "DeviceId";
	@Param(parameterName = PARAM_DeviceId)
	private String p_deviceIdStr;

	@ProcessParamLookupValuesProvider(parameterName = "DeviceId", numericKey = false)
	public LookupValuesList getDeviceIds()
	{
		return deviceAccessorsHubFactory.getDefaultDeviceAccessorsHub()
				.streamAllDeviceAccessors()
				.map(PrintDeviceQRCodes::toLookupValue)
				.distinct()
				.collect(LookupValuesList.collect());
	}

	private static LookupValue toLookupValue(DeviceAccessor deviceAccessor)
	{
		return LookupValue.StringLookupValue.of(deviceAccessor.getId().getAsString(), deviceAccessor.getDisplayName());
	}

	@Override
	protected String doIt()
	{
		final ImmutableList<PrintableQRCode> qrCodes = streamSelectedDevices()
				.map(PrintDeviceQRCodes::toDeviceQRCode)
				.map(DeviceQRCode::toPrintableQRCode)
				.collect(ImmutableList.toImmutableList());

		final QRCodePDFResource pdf = globalQRCodeService.createPDF(qrCodes);
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}

	private Stream<DeviceAccessor> streamSelectedDevices()
	{
		final DeviceId onlyDeviceId = StringUtils.trimBlankToOptional(p_deviceIdStr)
				.map(DeviceId::ofString)
				.orElse(null);

		if (onlyDeviceId != null)
		{
			final DeviceAccessor deviceAccessor = deviceAccessorsHubFactory.getDefaultDeviceAccessorsHub()
					.getDeviceAccessorById(onlyDeviceId)
					.orElseThrow(() -> new AdempiereException("No device found for id: " + onlyDeviceId));
			return Stream.of(deviceAccessor);
		}
		else
		{
			return deviceAccessorsHubFactory.getDefaultDeviceAccessorsHub()
					.streamAllDeviceAccessors();
		}
	}

	private static DeviceQRCode toDeviceQRCode(final DeviceAccessor deviceAccessor)
	{
		return DeviceQRCode.builder()
				.deviceId(deviceAccessor.getId())
				.caption(deviceAccessor.getDisplayName().getDefaultValue())
				.build();
	}

}
