package de.metas.ui.web.material.cockpit.v2;

import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecorator;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class MaterialCockpitV2FilterConverterDecorator implements SqlDocumentFilterConverterDecorator
{
	@Override
	public WindowId getWindowId()
	{
		return MaterialCockpitV2Service.WINDOWID_MaterialCockpitV2;
	}

	@Override
	public SqlDocumentFilterConverter decorate(@NonNull final SqlDocumentFilterConverter converter)
	{
		return new MaterialCockpitV2SelectionFilterConverter(converter);
	}
}
