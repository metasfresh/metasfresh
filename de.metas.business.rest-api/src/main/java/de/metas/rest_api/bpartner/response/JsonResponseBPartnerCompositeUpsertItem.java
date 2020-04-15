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
	JsonResponseUpsertItem responseBPartnerItem;

	@ApiModelProperty(position = 20)
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseUpsertItem> responseLocationItems;

	@ApiModelProperty(position = 30)
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseUpsertItem> responseContactItems;

	@ApiModelProperty(position = 40)
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseUpsertItem> responseBankAccountItems;

	@Builder
	@JsonCreator
	public JsonResponseBPartnerCompositeUpsertItem(
			@JsonProperty("responseBPartnerItem") @Nullable final JsonResponseUpsertItem responseBPartnerItem,
			@JsonProperty("responseLocationItems") @Singular final List<JsonResponseUpsertItem> responseLocationItems,
			@JsonProperty("responseContactItems") @Singular final List<JsonResponseUpsertItem> responseContactItems,
			@JsonProperty("responseBankAccountItems") @Singular final List<JsonResponseUpsertItem> responseBankAccountItems)
	{
		this.responseBPartnerItem = responseBPartnerItem;
		this.responseLocationItems = responseLocationItems;
		this.responseContactItems = responseContactItems;
		this.responseBankAccountItems = responseBankAccountItems;
	}


}
