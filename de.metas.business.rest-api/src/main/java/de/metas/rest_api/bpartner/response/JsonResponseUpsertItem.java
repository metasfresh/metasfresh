package de.metas.rest_api.bpartner.response;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.common.MetasfreshId;
import de.pentabyte.springfox.ApiEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@ApiModel("Response to a a single mater data upsert request entity")
@Value
public class JsonResponseUpsertItem
{
	public enum SyncOutcome
	{
		CREATED,

		@ApiEnum("Master data was updated; note that it's possible that nothing really changed due to the update.")
		UPDATED,

		@ApiEnum("E.g. if a location already exists and the sync advise is to not change existing entities.")
		NOTHING_DONE;
	}

	@ApiModelProperty(value = "The identifier that was specified in the repective upsert request",//
			position = 10)
	String identifier;

	@ApiModelProperty(value = "The metasfresh-ID of the upserted record.\n"
			+ "Can be null if the respective resource did not exist and the sync-advise indicated to do nothing.",//
			position = 20, dataType = "java.lang.Long")
	MetasfreshId metasfreshId;

	SyncOutcome syncOutcome;

	@Builder
	@JsonCreator
	public JsonResponseUpsertItem(
			@JsonProperty("identifier") @NonNull final String identifier,
			@JsonProperty("metasfreshId") @Nullable final MetasfreshId metasfreshId,
			@JsonProperty("syncOutcome") @NonNull final SyncOutcome syncOutcome)
	{
		this.identifier = identifier;
		this.metasfreshId = metasfreshId;
		this.syncOutcome = syncOutcome;
	}

}
