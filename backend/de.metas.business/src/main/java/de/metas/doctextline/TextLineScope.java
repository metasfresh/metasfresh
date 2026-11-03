package de.metas.doctextline;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_Doc_TextLine;

/**
 * Whether a {@link DocTextLine} belongs with the article-line run that follows it, or with the whole document.
 * Maps to {@code C_Doc_TextLine.TextLineScope} (AD_Reference {@value X_C_Doc_TextLine#TEXTLINESCOPE_AD_Reference_ID}).
 */
@RequiredArgsConstructor
@Getter
public enum TextLineScope implements ReferenceListAwareEnum
{
	Following(X_C_Doc_TextLine.TEXTLINESCOPE_Following),
	Document(X_C_Doc_TextLine.TEXTLINESCOPE_Document),
	;

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<TextLineScope> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@JsonCreator
	@NonNull
	public static TextLineScope ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isFollowing() {return Following == this;}

	@JsonValue
	public String toJson() {return code;}
}
