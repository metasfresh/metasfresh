package de.metas.document.archive.mailrecipient;

import de.metas.i18n.Language;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
public class DocOutBoundRecipient
{
	@NonNull DocOutBoundRecipientId id;
	@Nullable @With String emailAddress;
	boolean invoiceAsEmail;
	@Nullable Language userLanguage;
	@Nullable Language bPartnerLanguage;

	public boolean isEmailAddressSet() {return !Check.isBlank(emailAddress);}

	public DocOutBoundRecipient withEmailAddressIfEmpty(@NonNull final Supplier<String> newEmailAddressSupplier)
	{
		final String emailAddressNorm = StringUtils.trimBlankToNull(emailAddress);
		if (emailAddressNorm != null)
		{
			return this;
		}

		final String newEmailAddressNorm = StringUtils.trimBlankToNull(newEmailAddressSupplier.get());
		return newEmailAddressNorm != null ? withEmailAddress(newEmailAddressNorm) : this;
	}

}
