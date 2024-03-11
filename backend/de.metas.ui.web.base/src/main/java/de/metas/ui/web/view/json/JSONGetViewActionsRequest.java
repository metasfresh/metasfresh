package de.metas.ui.web.view.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONGetViewActionsRequest
{
	@Nullable String viewProfileId;

	@Nullable Set<String> selectedIds;

	@Nullable String parentViewId;
	@ApiParam("comma separated IDs")
	@Nullable Set<String> parentViewSelectedIds;

	@Nullable String childViewId;
	@ApiParam("comma separated IDs")
	@Nullable Set<String> childViewSelectedIds;

	/** if true then all actions shall be returned, not only those which are enabled or not silent */
	boolean all;
}
