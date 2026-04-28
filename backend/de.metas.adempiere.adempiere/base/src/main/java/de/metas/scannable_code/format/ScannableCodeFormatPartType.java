package de.metas.scannable_code.format;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_ScannableCode_Format_Part;

@RequiredArgsConstructor
@Getter
public enum ScannableCodeFormatPartType implements ReferenceListAwareEnum
{
	Ignored(X_C_ScannableCode_Format_Part.DATATYPE_Ignored, ScannableCodeFormatPartDataType.String),
	Constant(X_C_ScannableCode_Format_Part.DATATYPE_Constant, ScannableCodeFormatPartDataType.String),
	ProductCode(X_C_ScannableCode_Format_Part.DATATYPE_ProductCode, ScannableCodeFormatPartDataType.String),
	WeightInKg(X_C_ScannableCode_Format_Part.DATATYPE_WeightInKg, ScannableCodeFormatPartDataType.Number),
	LotNo(X_C_ScannableCode_Format_Part.DATATYPE_LotNo, ScannableCodeFormatPartDataType.String),
	BestBeforeDate(X_C_ScannableCode_Format_Part.DATATYPE_BestBeforeDate, ScannableCodeFormatPartDataType.Date),
	ProductionDate(X_C_ScannableCode_Format_Part.DATATYPE_ProductionDate, ScannableCodeFormatPartDataType.Date),
	;

	@NonNull private static final ValuesIndex<ScannableCodeFormatPartType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;
	@NonNull private final ScannableCodeFormatPartDataType dataType;

	public static ScannableCodeFormatPartType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@JsonCreator
	public static ScannableCodeFormatPartType ofJson(@NonNull final String json) {return ofCode(json);}

	@JsonValue
	public String toJson() {return getCode();}

	public boolean isDate() {return dataType == ScannableCodeFormatPartDataType.Date;}
}
