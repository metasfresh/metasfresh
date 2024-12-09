package org.compiere.acct;

import de.metas.i18n.AdMessageKey;
<<<<<<< HEAD
import de.metas.util.Check;

/**
 * Posting Status.
 * 
 * See AD_Reference_ID=234.
 * 
 * @author tsa
 *
 */
public enum PostingStatus
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
{
	NotPosted("N"),
	NotBalanced("b"),
	NotConvertible("c"),
	PeriodClosed("p"),
	InvalidAccount("i"),
	PostPrepared("y"),
	Posted("Y"),
	Error("E");

<<<<<<< HEAD
	private final String status;

	PostingStatus(final String status)
	{
		Check.assumeNotEmpty(status, "status not empty");
		this.status = status;
	}

	public String getStatusCode()
	{
		return status;
	}

	public AdMessageKey getAD_Message()
	{
		return AdMessageKey.of("PostingError-" + getStatusCode());
	}

	public static PostingStatus forStatusCode(final String statusCode)
	{
		for (final PostingStatus status : values())
		{
			if (status.getStatusCode().equals(statusCode))
			{
				return status;
			}
		}

		throw new IllegalArgumentException("No " + PostingStatus.class + " found for status code: " + statusCode);
	}
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
