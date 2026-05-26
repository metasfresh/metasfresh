package de.metas.handlingunits.qrcodes.mobile;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.i18n.AdMessageKey;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.adempiere.warehouse.qrcode.LocatorQRCodeJsonConverter;

@UtilityClass
public class MobileQRCodeMessages
{
	// QR type / parsing errors
	public static final AdMessageKey WRONG_TYPE_LOCATOR = AdMessageKey.of("de.metas.mobile.qr.WrongType.Locator");
	public static final AdMessageKey WRONG_TYPE         = AdMessageKey.of("de.metas.mobile.qr.WrongType.Generic");
	public static final AdMessageKey NOT_RECOGNIZED     = AdMessageKey.of("de.metas.mobile.qr.NotRecognized");

	// HU lookup errors
	public static final AdMessageKey HU_NOT_FOUND           = AdMessageKey.of("de.metas.mobile.qr.HuNotFound");
	public static final AdMessageKey HU_AMBIGUOUS           = AdMessageKey.of("de.metas.mobile.qr.HuAmbiguous");
	public static final AdMessageKey HU_DESTROYED           = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_HU_DESTROYED_ERROR_MSG");
	public static final AdMessageKey HU_PRODUCT_NOT_MATCHING = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");

	// Distribution business-check errors
	public static final AdMessageKey HU_RESERVED_BY_OTHER  = AdMessageKey.of("de.metas.distribution.HuReservedByOtherDocument");
	public static final AdMessageKey HU_NOT_AT_TARGET      = AdMessageKey.of("de.metas.distribution.HuNotAtTargetWorkplace");
	public static final AdMessageKey HU_ALREADY_AT_TARGET  = AdMessageKey.of("de.metas.distribution.HuAlreadyAtTarget");

	// Inventory business-check errors
	public static final AdMessageKey HU_ALREADY_COUNTED    = AdMessageKey.of("de.metas.inventory.HuAlreadyCounted");
	public static final AdMessageKey HU_NOT_IN_INVENTORY   = AdMessageKey.of("de.metas.inventory.HuNotInInventory");

	// HU consolidation business-check errors
	public static final AdMessageKey LU_EXPECTED_AT_TARGET = AdMessageKey.of("de.metas.hu_consolidation.LuExpectedAtTarget");
	public static final AdMessageKey LU_NOT_AT_SLOT        = AdMessageKey.of("de.metas.hu_consolidation.LuNotAtPickingSlot");

	// Catch-all for unexpected exceptions.
	// TODO: intended for use in the mobile REST controllers' global exception handler to wrap unrecognized server-side exceptions.
	//       The message shown to the user should include a server-generated trace-ID so support staff can
	//       correlate the user's report with the corresponding backend log entry.
	public static final AdMessageKey MOBILE_INTERNAL_ERROR = AdMessageKey.of("de.metas.mobile.InternalError");

	/**
	 * Creates a user-friendly exception for the case where the user scanned a GlobalQRCode that is not an HU QR code.
	 * If the scanned code is a locator (LOC#) QR code, the locator caption is included in the message.
	 */
	@NonNull
	public static AdempiereException newWrongGlobalQRTypeException(@NonNull final GlobalQRCode globalQRCode)
	{
		if (LocatorQRCodeJsonConverter.isTypeMatching(globalQRCode))
		{
			final LocatorQRCode locatorQRCode = LocatorQRCodeJsonConverter.fromGlobalQRCode(globalQRCode);
			return new AdempiereException(WRONG_TYPE_LOCATOR, locatorQRCode.getCaption());
		}
		return new AdempiereException(WRONG_TYPE, globalQRCode.getType().toJson());
	}

	/**
	 * Creates a user-friendly exception for the case where the scanned code does not match any known QR code format.
	 */
	@NonNull
	public static AdempiereException newNotRecognizedException(@NonNull final ScannedCode scannedCode)
	{
		return new AdempiereException(NOT_RECOGNIZED, StringUtils.trunc(scannedCode.getAsString(), 40));
	}
}
