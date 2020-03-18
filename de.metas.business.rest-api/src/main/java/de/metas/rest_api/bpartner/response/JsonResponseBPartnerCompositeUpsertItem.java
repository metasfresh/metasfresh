package de.metas.rest_api.bpartner.response;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
public class JsonResponseBPartnerCompositeUpsertItem
{
	@ApiModelProperty(position = 10)
	@JsonInclude(Include.NON_NULL)
	JsonResponseUpsertItem jsonResponseBPartnerUpsertItem;

	@ApiModelProperty(position = 20)
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseUpsertItem> jsonResponseLocationUpsertItems;

	@ApiModelProperty(position = 30)
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseUpsertItem> jsonResponseContactUpsertItems;

	@ApiModelProperty(position = 40)
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseUpsertItem> jsonResponseBankAccountUpsertItems;

	@Builder
	@JsonCreator
	public JsonResponseBPartnerCompositeUpsertItem(
			@JsonProperty("jsonResponseBPartnerUpsertItem") @Nullable final JsonResponseUpsertItem jsonResponseBPartnerUpsertItem,
			@JsonProperty("jsonResponseLocationUpsertItems") @Singular final List<JsonResponseUpsertItem> jsonResponseLocationUpsertItems,
			@JsonProperty("jsonResponseContactUpsertItems") @Singular final List<JsonResponseUpsertItem> jsonResponseContactUpsertItems,
			@JsonProperty("jsonResponseBankAccountUpsertItems") @Singular final List<JsonResponseUpsertItem> jsonResponseBankAccountUpsertItems)
	{
		this.jsonResponseBPartnerUpsertItem = jsonResponseBPartnerUpsertItem;
		this.jsonResponseLocationUpsertItems = jsonResponseLocationUpsertItems;
		this.jsonResponseContactUpsertItems = jsonResponseContactUpsertItems;
		this.jsonResponseBankAccountUpsertItems = jsonResponseBankAccountUpsertItems;
	}

}
