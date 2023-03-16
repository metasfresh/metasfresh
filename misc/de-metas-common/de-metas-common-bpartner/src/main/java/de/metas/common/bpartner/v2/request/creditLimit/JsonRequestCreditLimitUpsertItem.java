/*
 * #%L
 * de-metas-common-bpartner
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.common.bpartner.v2.request.creditLimit;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
public class JsonRequestCreditLimitUpsertItem
{
	@Schema(description = "Translated to `C_BPartner_CreditLimit.C_BPartner_CreditLimit_ID`, if set, it's assumed that the resource exists in metasfresh.")
	private JsonMetasfreshId creditLimitId;

	@Schema(hidden = true)
	private boolean creditLimitIdSet;

	@Schema(description = "Translated to `C_BPartner_CreditLimit.Amount`")
	private JsonMoney amount;

	@Schema(hidden = true)
	private boolean amountSet;

	@Schema(description = "Translated to `C_CreditLimit_Type.Name`")
	private String type;

	@Schema(hidden = true)
	private boolean typeSet;

	@Schema(description = "Translated to `C_BPartner_CreditLimit.DateFrom`")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateFrom;

	@Schema(hidden = true)
	private boolean dateFromSet;

	@Schema(description = "Translated to `C_BPartner_CreditLimit.IsActive`")
	private Boolean active;

	@Schema(hidden = true)
	private boolean activeSet;

	@Schema(description = "Translated to `C_BPartner_CreditLimit.Processed`")
	private Boolean processed;

	@Schema(hidden = true)
	private boolean processedSet;

	@Schema(description = "Translated to `C_BPartner_CreditLimit.ApprovedBy_ID`")
	private JsonMetasfreshId approvedBy;

	@Schema(hidden = true)
	private boolean approvedBySet;

	public void setAmount(final JsonMoney amount)
	{
		this.amount = amount;
		this.amountSet = true;
	}

	public void setType(final String type)
	{
		this.type = type;
		this.typeSet = true;
	}

	public void setCreditLimitId(final JsonMetasfreshId creditLimitId)
	{
		this.creditLimitId = creditLimitId;
		this.creditLimitIdSet = true;
	}

	public void setDateFrom(final LocalDate dateFrom)
	{
		this.dateFrom = dateFrom;
		this.dateFromSet = true;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setProcessed(final Boolean processed)
	{
		this.processed = processed;
		this.processedSet = true;
	}

	public void setApprovedBy(final JsonMetasfreshId approvedBy)
	{
		this.approvedBy = approvedBy;
		this.approvedBySet = true;
	}
}
