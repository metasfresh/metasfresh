package de.metas.ui.web.pporder;

import java.math.BigDecimal;

import de.metas.ui.web.handlingunits.HUDocumentViewType;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.Builder;
import lombok.Data;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Builder
@Data
public class PPOrderLine2
{
	// FIXME: experimental - improve it or delete it!
	
	private String value;
	private String bomType;
	private HUDocumentViewType huType;
	private JSONLookupValue product;
	private String packingInfo;
	private JSONLookupValue uom;
	private BigDecimal qty;
	private BigDecimal qtyPlan;
}
