package de.metas.ui.web.window.model.sql.save;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.Document;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.GridTabVO;

import java.util.Set;

public interface SaveHandler
{
	@NonNull
	Set<String> getHandledTableName();

	boolean isReadonly(@NonNull GridTabVO gridTabVO);

	SaveResult save(@NonNull Document document);

	void delete(@NonNull Document document);

	//
	//
	//
	//
	//
	//
	//

	@Value
	@Builder
	class SaveResult
	{
		boolean needsRefresh;
		@NonNull DocumentId idNew;
	}
}
