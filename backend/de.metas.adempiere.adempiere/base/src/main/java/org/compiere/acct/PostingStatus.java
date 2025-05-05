package org.compiere.acct;

import de.metas.i18n.AdMessageKey;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Posting Status.
 * <p>
 * See AD_Reference_ID=234.
 *
 * @author tsa
 */
@AllArgsConstructor
public enum PostingStatus implements ReferenceListAwareEnum
{
	NotPosted("N"),
	NotBalanced("b"),
	NotConvertible("c"),
	PeriodClosed("p"),
	InvalidAccount("i"),
	PostPrepared("y"),
	Posted("Y"),
	Error("E");

	private static final ReferenceListAwareEnums.ValuesIndex<PostingStatus> index = ReferenceListAwareEnums.index(values());

	@Getter @NonNull private final String code;
	@Getter @NonNull private final AdMessageKey adMessage;

	PostingStatus(@NonNull final String code)
	{
		this.code = code;
		this.adMessage = AdMessageKey.of("PostingError-" + code);
	}

	public static PostingStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static PostingStatus ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public boolean isPosted() {return Posted.equals(this);}
}
