package de.metas.acct.posting.log;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public enum DocumentPostingLogRequestType implements ReferenceListAwareEnum
{
	Enqueued("enqueued"),
	PostingOK("posting_ok"),
	PostingError("posting_error"),
	;

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<DocumentPostingLogRequestType> index = ReferenceListAwareEnums.index(values());

	@NonNull @Getter private final String code;

	@NonNull
	public static DocumentPostingLogRequestType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static DocumentPostingLogRequestType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@JsonValue
	public String toJson() {return getCode();}
}
