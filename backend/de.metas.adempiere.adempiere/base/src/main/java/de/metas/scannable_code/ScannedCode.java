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
import java.util.Optional;

/**
 * A scanned code by user.
 * At this level we don't know what kind of code it is (Barcode, QRCode etc.).
 */
@EqualsAndHashCode(doNotUseGetters = true)
public class ScannedCode
{
	private final String code;

	private ScannedCode(@NonNull final String code)
	{
		this.code = StringUtils.trimBlankToNull(code);
		if (this.code == null)
		{
			throw new AdempiereException("code is blank");
		}
	}

	public static ScannedCode ofString(@NonNull final String code) {return new ScannedCode(code);}

	@Nullable
	@JsonCreator // IMPORTANT: keep it here because we want to handle the case when the json value is empty string 
	public static ScannedCode ofNullableString(@Nullable final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		return codeNorm != null ? new ScannedCode(codeNorm) : null;
	}

	public static Optional<ScannedCode> optionalOfString(@Nullable final String code)
	{
		return StringUtils.trimBlankToOptional(code).map(ScannedCode::new);
	}

	@Override
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return code;}

	public GlobalQRCode toGlobalQRCode() {return toGlobalQRCodeIfMatching().orThrow();}

	public GlobalQRCodeParseResult toGlobalQRCodeIfMatching() {return GlobalQRCode.parse(code);}

	public PrintableScannedCode toPrintableScannedCode() {return PrintableScannedCode.of(this);}
}
