package de.metas.scannable_code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeParseResult;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * A scanned code by the user.
 * At this level we don't know what kind of code it is (Barcode, QRCode etc.).
 */
@EqualsAndHashCode(doNotUseGetters = true)
public class ScannedCode
{
	private final String code;

	private ScannedCode(@NonNull final Object obj)
	{
		this.code = normalizeCode(obj);
		if (this.code == null)
		{
			throw new AdempiereException("code is blank");
		}
	}

	@Nullable
	private static String normalizeCode(@Nullable final Object obj)
	{
		return obj != null ? StringUtils.trimBlankToNull(obj.toString()) : null;
	}

	public static ScannedCode ofString(@NonNull final String code) {return new ScannedCode(code);}

	@Nullable
	public static ScannedCode ofNullableString(@Nullable final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		return codeNorm != null ? new ScannedCode(codeNorm) : null;
	}

	@Nullable
	@JsonCreator(mode = JsonCreator.Mode.DELEGATING) // IMPORTANT: keep it here because we want to handle the case when the JSON value is empty string, number etc. 
	public static ScannedCode ofNullableObject(@Nullable final Object obj)
	{
		final String code = normalizeCode(obj);
		return code != null ? new ScannedCode(code) : null;
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return code;}

	public GlobalQRCode toGlobalQRCode() {return toGlobalQRCodeIfMatching().orThrow();}

	public GlobalQRCodeParseResult toGlobalQRCodeIfMatching() {return GlobalQRCode.parse(code);}

	public PrintableScannedCode toPrintableScannedCode() {return PrintableScannedCode.of(this);}

	public String substring(final int beginIndex, final int endIndex) {return code.substring(beginIndex, endIndex);}

	public int length() {return code.length();}
}
